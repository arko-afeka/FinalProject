package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.SpecialChar;
import org.afeka.project.validation.plugin.xss.util.StringReader;
import org.apache.commons.lang3.CharUtils;

public class TagEndState extends State {
  public TagEndState(StringReader data) {
    super(data);
  }

  protected TagEndState(State state) {
    super(state);
  }

  @Override
  public void run() {
    if (data.size() == 0) {
      shouldContinue = false;
      token = null;
    }

    char cur = data.peek();

    if (cur == SpecialChar.GT) {
      setFromState(new DataState(this));
    } else if (CharUtils.isAsciiAlpha(cur)) {
      setFromState(new TagNameState(this));
    } else {
      isClose = false;
      setFromState(new BogusCommentState(this));
    }
  }
}
