package org.afeka.project.model;

import java.util.UUID;

public class AnalysisResult {
  private AnalysisResultState state;

  public AnalysisResult(AnalysisResultState state) {
    this.state = state;
  }

  public AnalysisResultState getState() {
    return state;
  }
}
