package model.owasp;

public class ParentStage {
    private Stage stage;

    public ParentStage() {
    }

    public ParentStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
