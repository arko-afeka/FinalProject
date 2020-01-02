package org.afeka.project.validation.plugin.xss.model;

public enum TokenType {
    DATA_TEXT,
    TAG_NAME_OPEN,
    TAG_NAME_CLOSE,
    TAG_NAME_SELF_CLOSE,
    TAG_DATA,
    TAG_ATTR_NAME,
    TAG_ATTR_VALUE,
    TAG_COMMENT,
    TAG_DOCTYPE;
}
