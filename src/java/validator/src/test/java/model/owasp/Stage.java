package model.owasp;

public class Stage {
    private Input input;
    private Output output;

    public Stage() {

    }

    public Stage(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public Input getInput() {
        return input;
    }

    public Output getOutput() {
        return output;
    }
}
