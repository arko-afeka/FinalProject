package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.util.StringReader;

public class EOFState extends State {
  public EOFState(StringReader buffer) {
    super(buffer);
  }

  protected EOFState(State state) {
    super(state);
    shouldContinue = false;
  }

  @Override
  public void run() {
    shouldContinue = false;
    token = null;
  }
}
