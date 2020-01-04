package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class DoctypeState extends State {
  public DoctypeState(StringReader data) {
    super(data);
  }

  protected DoctypeState(State state) {
    super(state);
  }

  @Override
  public void run() {
    TokenType tokenType = TokenType.DOCTYPE;
    shouldContinue = true;
    int idx = data.indexOf(SpecialChar.GT);

    if (idx == -1) {
      token = new Token(data.subString(data.size()), tokenType);
      nextState = new EOFState(this);
    } else {
      nextState = new DataState(this);
      token = new Token(data.subString(idx), tokenType);
      data.move(1);
    }
  }
}
