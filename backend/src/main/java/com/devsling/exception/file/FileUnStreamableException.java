package com.devsling.exception.file;

import com.devsling.exception.config.ApiException;

public class FileUnStreamableException extends ApiException {

  /**
   * @param message
   */
  public FileUnStreamableException() {
    super("error the file is unstreamable");

  }

}
