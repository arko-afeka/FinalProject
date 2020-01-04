package org.afeka.project.validation.plugin.xss.model;

public enum TokenType {
  DATA_TEXT,
  TAG_NAME_OPEN,
  TAG_NAME_CLOSE,
  TAG_NAME_SELFCLOSE,
  TAG_DATA,
  ATTR_NAME,
  ATTR_VALUE,
  TAG_COMMENT,
  DOCTYPE,
  TAG_CLOSE;
}
