package org.afeka.project.model;

import java.util.UUID;

public class AnalysisResult {
    private AnalysisResultState state;
    private UUID uuid;

    public AnalysisResult(AnalysisResultState state, UUID uuid) {
        this.state = state;
        this.uuid = uuid;
    }

    public AnalysisResultState getState() {
        return state;
    }

    public UUID getUuid() {
        return uuid;
    }
}
