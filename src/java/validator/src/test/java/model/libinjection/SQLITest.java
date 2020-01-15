package model.libinjection;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class SQLITest {
    private String name;
    private String input;
    private List<Pair<Character, String>> expected;

    public SQLITest(String name, String input, List<Pair<Character, String>> expected) {
        this.name = name;
        this.input = input;
        this.expected = expected;
    }

    public String getName() {
        return name;
    }

    public String getInput() {
        return input;
    }

    public List<Pair<Character, String>> getExpected() {
        return expected;
    }

    @Override
    public String toString() {
        return "SQLITest{" +
                "name='" + name + '\'' +
                '}';
    }
}
