package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.util.StringReader;

public class CommentState extends State {
  public CommentState(StringReader data) {
    super(data);
  }

  protected CommentState(State state) {
    super(state);
  }

  @Override
  public void run() {
    int pos = data.position();

    while (true) {
      int idx = data.indexOf(SpecialChar.DASH, pos);

      if (idx == -1 || (data.size() - idx) < 3) {
        shouldContinue = true;
        nextState = new EOFState(this);
        token = new Token(data.subString(data.size()), TokenType.TAG_COMMENT);
        return;
      }

      int offset = 1;

      while ((idx + offset) < data.size() && data.peek(pos + offset) == 0) {
        offset++;
      }

      if (idx + offset == data.size()) {
        shouldContinue = true;
        nextState = new EOFState(this);
        token = new Token(data.subString(data.size()), TokenType.TAG_COMMENT);
        return;
      }

      if (data.peek(idx + offset) != SpecialChar.DASH && data.peek(idx + offset) != SpecialChar.BANG) {
        pos = idx + 1;
        continue;
      }

      offset++;
      if (idx + offset == data.size()) {
        shouldContinue = true;
        nextState = new EOFState(this);
        token = new Token(data.subString(data.size()), TokenType.TAG_COMMENT);
        return;
      }

      if (data.peek(idx + offset) != SpecialChar.GT) {
        pos = idx + 1;
        continue;
      }

      offset++;

      shouldContinue = true;
      nextState = new DataState(this);
      token = new Token(data.subString(idx), TokenType.TAG_COMMENT);
      data.move(offset);
      return;


    }
  }
}
