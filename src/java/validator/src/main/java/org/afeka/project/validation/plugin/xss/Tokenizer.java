package org.afeka.project.validation.plugin.xss;

import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.ValidatorFlag;
import org.afeka.project.validation.plugin.xss.state.*;
import org.afeka.project.validation.plugin.xss.util.StringReader;
import org.apache.commons.text.StringEscapeUtils;

import java.util.Iterator;

public class Tokenizer implements Iterator<Token> {
  private State state;

  public Tokenizer(ValidatorFlag flag, String data) {
    final StringReader dataReader = new StringReader(StringEscapeUtils.unescapeXml(StringEscapeUtils.unescapeEcmaScript(StringEscapeUtils.unescapeHtml4(data))));
    switch (flag) {
      case DEFAULT:
        {
          state = new DataState(dataReader);
          break;
        }
      case NO_QUOTE:
        {
          state = new BeforeAttributeNameState(dataReader);
          break;
        }
      case SINGLE_QUOTE:
        {
          state = new SingleQuoteAttributeNameState(dataReader);
          break;
        }
      case DOUBLE_QUOTE:
        {
          state = new DoubleQuoteAttributeNameState(dataReader);
          break;
        }
      case BACK_QUOTE:
        {
          state = new BackQuoteAttributeNameState(dataReader);
          break;
        }
    }
  }

  @Override
  public boolean hasNext() {
    return state.hasNext();
  }

  @Override
  public Token next() {
    state.run();
    Token token = state.getToken();

    if (state.hasNext()) {
      state = state.next();
    }

    return token;
  }
}
