package com.devsling.exception.file;

import com.devsling.exception.config.ApiException;

public class FileNotFoundException extends ApiException {

  /**
   * @param message
   */
  public FileNotFoundException() {
    super("error File does not exist");

  }

}
