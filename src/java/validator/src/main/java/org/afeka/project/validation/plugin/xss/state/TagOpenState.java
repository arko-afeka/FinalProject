package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.TokenType;
import org.afeka.project.validation.plugin.xss.util.StringReader;
import org.apache.commons.lang3.CharUtils;

public class TagOpenState extends State {
  public TagOpenState(StringReader data) {
    super(data);
  }

  protected TagOpenState(State state) {
    super(state);
  }

  @Override
  public void run() {
    if (data.size() <= 0) {
      shouldContinue = false;
      token = null;
      return;
    }

    char cur = data.peek();
    switch (cur) {
      case (SpecialChar.BANG):
        {
          data.move(1);
          setFromState(new MarkupDeclarationOpen(this));
          break;
        }
      case (SpecialChar.SLASH):
        {
          data.move(1);
          isClose = true;
          setFromState(new TagEndState(this));
          break;
        }
      case (SpecialChar.QUESTION):
        {
          data.move(1);
          setFromState(new BogusCommentState(this));
          break;
        }
      case (SpecialChar.PERCENT):
        {
          data.move(1);
          setFromState(new BogusCommentState2(this));
          break;
        }
      case (SpecialChar.NULL):
        {
          setFromState(new TagNameState(this));
          break;
        }
      default:
        {
          if (CharUtils.isAsciiAlpha(cur)) {
            setFromState(new TagNameState(this));
          } else {
            if (data.isBeginning()) {
              setFromState(new DataState(this));
              return;
            }

            nextState = new DataState(this);
            data.move(-1);
            token = new Token(data.subString(1), TokenType.DATA_TEXT);
            shouldContinue = true;
          }
          break;
        }
    }
  }
}
