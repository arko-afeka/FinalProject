package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Output {
    @JsonProperty("log_contains")
    private String logContains;

    @JsonProperty("no_log_contains")
    private String noLogContains;

    public Output() {

    }

    public Output(String logContains, String noLogContains) {
        this.logContains = logContains;
        this.noLogContains = noLogContains;
    }

    public String getLogContains() {
        return logContains;
    }

    public String getNoLogContains() {
        return noLogContains;
    }
}
