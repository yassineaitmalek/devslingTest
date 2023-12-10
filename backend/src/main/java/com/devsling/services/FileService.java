package com.devsling.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.devsling.config.UploadFolder;
import com.devsling.constants.Constants;
import com.devsling.constants.FileExtension;
import com.devsling.controllers.config.ApiDownloadInput;
import com.devsling.controllers.config.ApiPartialInput;
import com.devsling.exception.config.ApiException;
import com.devsling.exception.file.FileNotFoundException;
import com.devsling.exception.file.FileUnStreamableException;
import com.devsling.models.local.Attachement;
import com.devsling.repositories.local.AttachementRepository;
import com.devsling.utility.FileUtility;
import com.devsling.utility.RangeUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class FileService {

  private final UploadFolder uploadFolder;

  private final AttachementRepository attachementRepository;

  public String uploadFile(@Valid @NotNull MultipartFile file, @Valid @NotNull @NotEmpty String attachmentId,
      String ext) {
    try {
      String realEXT = (ext == null || ext.trim().isEmpty()) ? "" : "." + ext;
      String filePath = Paths.get(uploadFolder.getUploadFolderPath(), attachmentId + realEXT).toString();
      file.transferTo(new File(filePath));
      return filePath;
    } catch (Exception e) {

      throw new ApiException(e.getMessage(), e);
    }

  }

  public Attachement uploadAttachement(@Valid @NotNull MultipartFile fileToUpload,
      @Valid @NotNull Attachement attachment) {

    attachment.setExt(FileUtility.getExtention(fileToUpload.getOriginalFilename()));
    attachment.setExtension(FileExtension.getByValue(attachment.getExt()));
    attachment.setFileType(attachment.getExtension().getType());
    attachment.setOriginalFileName(FileUtility.getFileNameWithoutExtension(fileToUpload.getOriginalFilename()));
    attachment.setPath(uploadFile(fileToUpload, attachment.getId(), attachment.getExt()));
    attachment.setFileSize(fileToUpload.getSize());
    return attachment;

  }

  public String getOriginalFileNameFromFile(@Valid @NotNull Attachement attachement) {
    return Optional.ofNullable(attachement)
        .map(f -> f.getOriginalFileName() + "." + f.getExt())
        .orElse("");

  }

  public Optional<Attachement> getById(@Valid @NotNull @NotEmpty String attachmentId) {
    return attachementRepository.findById(attachmentId);
  }

  public ApiDownloadInput downloadFile(@Valid @NotNull @NotEmpty String attachmentId) {
    log.info("download attachment with id : {}", attachmentId);
    return Optional.of(attachmentId)
        .map(attachementRepository::findById)
        .filter(e -> e.isPresent())
        .map(e -> e.get())
        .map(this::downloadAttachement)
        .orElseThrow(FileNotFoundException::new);

  }

  public ApiDownloadInput downloadAttachement(@Valid @NotNull Attachement attachment) {
    try {
      log.info("download file with id : {}", attachment.getId());
      return new ApiDownloadInput(getFileBytes(attachment), attachment.getOriginalFileName(), attachment.getExt());
    } catch (Exception e) {
      throw new ApiException(e.getMessage(), e);
    }

  }

  public void deleteAttachement(@Valid @NotNull @NotEmpty String attachmentId) {
    log.info("delete file with id : {}", attachmentId);
    Optional.of(attachmentId)
        .map(attachementRepository::findById)
        .filter(e -> e.isPresent())
        .map(e -> e.get())
        .ifPresent(this::deleteAttachement);

  }

  public void deleteAttachement(@Valid @NotNull Attachement attachment) {

    Optional.of(attachment)
        .ifPresent(e -> {
          deleteFile(attachment.getPath());
          attachementRepository.delete(e);
        });

  }

  public void deleteFile(@Valid @NotNull @NotEmpty String filePath) {

    try {
      Files.delete(Paths.get(filePath));
    } catch (IOException e) {
      throw new ApiException(e.getMessage(), e);
    }
  }

  public byte[] getFileBytes(@Valid @NotNull Attachement file) throws IOException {

    log.info("Getting stream of file with id {}", file.getId());
    InputStream inputStream = new FileInputStream(new File(file.getPath()));
    byte[] bytes = IOUtils.toByteArray(inputStream);
    IOUtils.closeQuietly(inputStream);
    return bytes;

  }

  public ApiPartialInput streamFile(@Valid @NotNull @NotEmpty String fileId, String httpRangeList) {

    return attachementRepository.findById(fileId)
        .map(e -> streamFile(e, httpRangeList))
        .orElseThrow(FileNotFoundException::new);

  }

  public ApiPartialInput streamFile(@Valid @NotNull Attachement attachement, String httpRangeList) {
    if (attachement.getFileType().isStreamable()) {
      RangeUtil ru = RangeUtil.getRangeUtil(httpRangeList, attachement.getFileSize(),
          attachement.getFileType().isImage());

      return ApiPartialInput
          .builder()
          .bytes(
              readByteRange(readPartialFileInputStream(attachement.getPath(),
                  ru.getStart(), ru.len()), ru.len()))
          .start(ru.getStart())
          .end(ru.getEnd())
          .lenght(ru.len())
          .size(attachement.getFileSize())
          .content(attachement.getFileType().getValue())
          .ext(attachement.getExt())
          .build();
    }
    throw new FileUnStreamableException();

  }

  public byte[] readPartialFileBytes(String filePath, long startRange, long rangeLength) {
    try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r")) {
      randomAccessFile.seek(startRange);
      byte[] data = new byte[(int) rangeLength];
      int bytesRead = randomAccessFile.read(data);

      if (bytesRead == -1) {
        throw new ApiException("End of file reached before reading the specified range.");
      }

      return data;
    } catch (Exception e) {
      throw new ApiException(e.getMessage(), e);

    }

  }

  public InputStream readPartialFileInputStream(String filePath, long startRange, long rangeLength) {
    try (RandomAccessFile randomAccessFile = new RandomAccessFile(filePath, "r");
        FileChannel fileChannel = randomAccessFile.getChannel()) {

      fileChannel.position(startRange);

      ByteBuffer buffer = ByteBuffer.allocate((int) rangeLength);
      int bytesRead = fileChannel.read(buffer);

      if (bytesRead == -1) {
        throw new IOException("End of file reached before reading the specified range.");
      }

      byte[] data = new byte[bytesRead];
      buffer.flip();
      buffer.get(data);

      return new ByteArrayInputStream(data);
    } catch (Exception e) {
      throw new ApiException(e.getMessage(), e);

    }
  }

  public byte[] readByteRange(InputStream inputStream, long len) {
    try {
      ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream();
      byte[] data = new byte[Constants.BYTE_RANGE];
      int nRead;
      while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
        bufferedOutputStream.write(data, 0, nRead);
      }
      bufferedOutputStream.flush();

      byte[] result = new byte[(int) len];
      System.arraycopy(bufferedOutputStream.toByteArray(), 0, result, 0, result.length);
      return result;
    } catch (Exception e) {
      throw new ApiException(e.getMessage(), e);

    }

  }
}
