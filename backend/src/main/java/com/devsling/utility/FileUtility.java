package com.devsling.utility;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class FileUtility {

  public String getKey() {
    LocalDate now = DateUtility.nowDate();
    return String.join("/", "file",
        String.valueOf(now.getYear()),
        String.valueOf(now.getMonthValue()),
        String.valueOf(now.getDayOfMonth())) + "/";
  }

  public String getExtention(String fileName) {

    return Optional.ofNullable(fileName)
        .filter(f -> f.contains("."))
        .map(f -> f.substring(fileName.lastIndexOf(".") + 1))
        .map(String::toLowerCase)
        .orElse("");

  }

  public String generateFileName(MultipartFile multipartFile, String fileId) {
    return fileId.concat(".").concat(getExtention(multipartFile.getOriginalFilename()));
  }

  public String join(String path1, String path2) {
    return Paths.get(path1, path2).toString();
  }

  public String getFileNameWithoutExtension(String fileName) {
    return fileName.replaceFirst("[.][^.]+$", "");
  }

  public boolean moveFile(String from, String to) {

    try {
      Files.move(Paths.get(from), Paths.get(to), StandardCopyOption.ATOMIC_MOVE);
      log.info("File {} moved to {}  successfully", from, to);
      return true;
    } catch (Exception e) {
      log.info("File {} failed to be moved to {} ", from, to);
      return false;
    }

  }

  public boolean createFolder(String folderPath) {

    try {
      Files.createDirectories(Paths.get(folderPath));
      log.info("Folder {} created successfully", folderPath);
      return true;
    } catch (Exception e) {
      log.info("folder {} failed to be created", folderPath);
      return false;
    }

  }

  public boolean createFolder(Path folderPath) throws Exception {

    try {
      Files.createDirectories(folderPath);
      log.info("Folder {} created successfully", folderPath.toString());
      return true;
    } catch (Exception e) {
      log.info("folder {} failed to be created", folderPath.toString());
      throw e;
    }

  }

  public void closeInputStram(InputStream stream) {
    IOUtils.closeQuietly(stream);
  }

  public void closeOutPutStream(OutputStream stream) {
    IOUtils.closeQuietly(stream);
  }

}
