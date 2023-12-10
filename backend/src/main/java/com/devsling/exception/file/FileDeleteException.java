package com.devsling.exception.file;

import com.devsling.exception.config.ApiException;

public class FileDeleteException extends ApiException {

  /**
   * @param message
   */
  public FileDeleteException() {
    super("error while deleting a file");

  }

}
