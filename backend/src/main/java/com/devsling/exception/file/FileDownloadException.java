package com.devsling.exception.file;

import com.devsling.exception.config.ApiException;

public class FileDownloadException extends ApiException {

  /**
   * @param message
   */
  public FileDownloadException() {
    super("error could not Download File");

  }

}
