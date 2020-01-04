package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class CdataState extends State {
  public CdataState(StringReader data) {
    super(data);
  }

  protected CdataState(State state) {
    super(state);
  }

  @Override
  public void run() {
    int pos = data.position();
    while (true) {
      int idx = data.indexOf(SpecialChar.RIGHTB, pos);

      if (idx == -1 || (data.size() - idx < 3)) {
        nextState = new EOFState(this);
        token = new Token(data.subString(data.size()), TokenType.DATA_TEXT);
        shouldContinue = true;
        return;
      } else if (data.peek(idx + 1) == SpecialChar.RIGHTB && data.peek(idx + 2) == SpecialChar.GT) {
        nextState = new DataState(this);
        token = new Token(data.subString(idx), TokenType.DATA_TEXT);
        data.move(3);
        shouldContinue = true;
        return;
      } else {
        pos = idx + 1;
      }
    }
  }
}
