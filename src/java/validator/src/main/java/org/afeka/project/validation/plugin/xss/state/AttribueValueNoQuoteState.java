package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.util.StringReader;

public class AttribueValueNoQuoteState extends State {
  public AttribueValueNoQuoteState(StringReader data) {
    super(data);
  }

  protected AttribueValueNoQuoteState(State previousState) {
    super(previousState);
  }

  @Override
  public void run() {}
}
