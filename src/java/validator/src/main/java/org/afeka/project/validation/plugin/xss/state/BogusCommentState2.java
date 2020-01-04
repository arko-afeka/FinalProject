package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class BogusCommentState2 extends State {
  public BogusCommentState2(StringReader data) {
    super(data);
  }

  protected BogusCommentState2(State state) {
    super(state);
  }

  @Override
  public void run() {
    int pos = data.position();

    while (true) {
      int idx = data.indexOf(SpecialChar.PERCENT, pos);

      if (idx == -1 || (data.size() - idx < 2)) {
        token = new Token(data.subString(data.size()), TokenType.TAG_COMMENT);
        nextState = new EOFState(this);
        shouldContinue = true;
        return;
      }

      if (data.peek(idx + 1) != SpecialChar.GT) {
        pos = idx + 1;
        continue;
      }

      token = new Token(data.subString(idx), TokenType.TAG_COMMENT);
      data.move(2);
      nextState = new DataState(this);
      shouldContinue = true;
      return;
    }
  }
}
