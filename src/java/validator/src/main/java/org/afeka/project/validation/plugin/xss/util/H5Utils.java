package org.afeka.project.validation.plugin.xss.util;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;

public class H5Utils {
  public static boolean isWhite(char c) {
    switch (c) {
      case '\t':
      case '\n':
      case 0x0B:
      case '\f':
      case '\r':
      case ' ':
        {
          return true;
        }
      default:
        {
          return false;
        }
    }
  }

  public static char skipWhiteSpace(StringReader reader) {
    while (reader.size() > 0) {
      char cur = reader.peek();
      switch (cur) {
        case (0x00):
        case (0x20):
        case (0x09):
        case (0x0A):
        case (0x0B):
        case (0x0C):
        case (0x0D):
          {
            reader.move(1);
            break;
          }
        default:
          {
            return cur;
          }
      }
    }

    return SpecialChar.EOF;
  }
}
