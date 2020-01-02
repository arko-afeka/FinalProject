package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.util.StringReader;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class EOFState extends State {
    public EOFState(StringReader buffer) {
        super(buffer);
    }

    @Override
    void run() {
        shouldContinue = false;
    }
}
