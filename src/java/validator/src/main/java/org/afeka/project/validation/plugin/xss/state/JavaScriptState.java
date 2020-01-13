package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.util.StringReader;

public class JavaScriptState extends State {
    public JavaScriptState(StringReader data) {
        super(data);
    }

    public JavaScriptState(State previousState) {
        super(previousState);
    }

    @Override
    public void run() {

    }
}
