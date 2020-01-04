package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class BogusCommentState extends State {
  public BogusCommentState(StringReader data) {
    super(data);
  }

  protected BogusCommentState(State state) {
    super(state);
  }

  @Override
  public void run() {
    int idx = data.indexOf(SpecialChar.GT);
    TokenType tokenType = TokenType.TAG_COMMENT;
    shouldContinue = true;

    if (idx == -1) {
      token = new Token(data.subString(data.size()), tokenType);
      nextState = new EOFState(this);
    } else {
      token = new Token(data.subString(idx), tokenType);
      data.move(1);
      nextState = new DataState(this);
    }
  }
}
