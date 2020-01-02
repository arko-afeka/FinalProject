package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.util.StringReader;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class DataState extends State {
  public DataState(StringReader data) {
    super(data);
  }

  @Override
  void run() {
    int idx = data.indexOf(SpecialChar.LT);

    if (idx == -1) {
      token = new Token(data.toString(), TokenType.DATA_TEXT);

      if (token.getToken().length() == 0) {
        shouldContinue = false;
        return;
      }

      nextState = new EOFState(data);
    } else {
      token = new Token(data.subString(0, idx), TokenType.DATA_TEXT);
      data.delete(0, idx);

      if (token.getToken().length() == 0) {
        setFromState(new TagOpenState(data));
        return;
      }

      nextState = new TagOpenState(data);
    }

    shouldContinue = true;
  }
}
