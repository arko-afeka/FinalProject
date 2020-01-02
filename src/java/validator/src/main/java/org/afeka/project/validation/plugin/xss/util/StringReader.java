package org.afeka.project.validation.plugin.xss.util;

public class StringReader {
    private String data;
    private int pos = 0;

    private StringReader(String data) {
        this.data = data;
    }

    public boolean isBeginning() {
        return pos == 0;
    }

    public int indexOf(char c) {
        return this.data.indexOf(c, pos);
    }

    public String subString(int len) {
        String data = this.data.substring(pos, len);
        pos += len;
        return data;
    }

    public void advance(int len) {
        pos += len;
    }

    public int size() {
        return data.length() - pos;
    }
}
