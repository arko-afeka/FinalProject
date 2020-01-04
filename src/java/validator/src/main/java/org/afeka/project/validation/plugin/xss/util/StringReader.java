package org.afeka.project.validation.plugin.xss.util;

import org.apache.commons.lang3.StringUtils;

public class StringReader {
  private String data;
  private int pos = 0;

  public StringReader(String data) {
    this.data = data;
  }

  public boolean isBeginning() {
    return pos == 0;
  }

  public int indexOf(char c) {
    int result = this.data.indexOf(c, pos);

    if (result == -1) {
      return result;
    }

    return result - pos;
  }

  public int indexOf(char c, int begin) {
    int result = this.data.indexOf(c, pos + begin);

    if (result == -1) {
      return result;
    }

    return result - pos;
  }

  public String subString(int len) {
    String data = this.data.substring(pos, pos + len);
    pos += len;
    return data;
  }

  public void move(int len) {
    pos += len;
  }

  public boolean startsWithIgnoreCase(String data) {
    if (size() - data.length() < 0) {
      return false;
    }

    return StringUtils.startsWithIgnoreCase(this.data.substring(pos), data);
  }

  public boolean startsWith(String data) {
    if (size() - data.length() < 0) {
      return false;
    }

    return StringUtils.startsWith(this.data.substring(pos), data);
  }

  public char peek() {
    return data.charAt(pos);
  }

  public char peek(int ind) {
    return data.charAt(pos + ind);
  }

  public int position() {
    return 0;
  }

  public int size() {
    return data.length() - pos;
  }
}
