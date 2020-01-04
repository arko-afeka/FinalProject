package org.afeka.project.exception;

public class HTTPStructureException extends Exception {
  public HTTPStructureException() {}

  public HTTPStructureException(String message) {
    super(message);
  }

  public HTTPStructureException(String message, Throwable cause) {
    super(message, cause);
  }

  public HTTPStructureException(Throwable cause) {
    super(cause);
  }

  public HTTPStructureException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
