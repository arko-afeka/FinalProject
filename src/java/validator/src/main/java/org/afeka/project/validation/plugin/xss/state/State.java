package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.util.StringReader;

import java.util.Iterator;

public abstract class State implements Iterator<State> {
  protected StringReader data;
  protected Token token = null;
  protected boolean shouldContinue = true;
  protected State nextState = null;
  protected boolean isClose = false;

  protected State(StringReader data) {
    this.data = data;
  }

  protected State(State previousState) {
    data = previousState.data;
    token = previousState.token;
    shouldContinue = previousState.shouldContinue;
    nextState = previousState.nextState;
    isClose = previousState.isClose;
  }

  public Token getToken() {
    return token;
  }

  @Override
  public boolean hasNext() {
    return shouldContinue;
  }

  @Override
  public State next() {
    return nextState;
  }

  protected void setFromState(State state) {
    state.run();
    token = state.token;
    shouldContinue = state.shouldContinue;
    nextState = state.nextState;
    isClose = state.isClose;
  }

  public abstract void run();
}
