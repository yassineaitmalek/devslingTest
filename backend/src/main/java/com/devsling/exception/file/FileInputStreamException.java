package com.devsling.exception.file;

import com.devsling.exception.config.ApiException;

public class FileInputStreamException extends ApiException {

  /**
   * @param message
   */
  public FileInputStreamException() {
    super("error while getting the file inputstream");

  }

}
