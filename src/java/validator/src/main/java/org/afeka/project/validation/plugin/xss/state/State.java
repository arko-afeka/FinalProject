package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.util.StringReader;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Iterator;

public abstract class State implements Iterator<State> {
    protected StringReader data;
    protected Token token;
    protected boolean shouldContinue;
    protected State nextState;

    protected State(StringReader data) {
        this.data = data;
    }

    Token getToken() {
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
        token = state.token;
        shouldContinue = state.shouldContinue;
        nextState = state.nextState;
    }

    abstract void run();
}
