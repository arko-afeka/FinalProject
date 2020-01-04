package model;

import java.util.List;

public class TestFile {
    private Meta meta;
    private List<Test> tests;

    public TestFile() {
    }

    public TestFile(Meta meta, List<Test> tests) {
        this.meta = meta;
        this.tests = tests;
    }

    public Meta getMeta() {
        return meta;
    }

    public List<Test> getTests() {
        return tests;
    }
}
