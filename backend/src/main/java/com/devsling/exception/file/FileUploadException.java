package com.devsling.exception.file;

import com.devsling.exception.config.ApiException;

public class FileUploadException extends ApiException {

  /**
   * @param message
   */
  public FileUploadException() {
    super("error could not upload File");

  }

}
