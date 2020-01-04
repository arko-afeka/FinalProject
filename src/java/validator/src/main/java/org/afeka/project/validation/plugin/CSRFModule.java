package org.afeka.project.validation.plugin;

import com.google.common.collect.ImmutableSet;
import org.afeka.project.model.AnalysisResultState;
import org.afeka.project.model.http.HTTPMessage;
import org.afeka.project.model.http.HTTPMessageType;
import org.afeka.project.validation.ValidationModule;

import java.util.Set;

public class CSRFModule implements ValidationModule {
  @Override
  public AnalysisResultState analyse(HTTPMessage message) {
    return AnalysisResultState.ALLOW;
  }

  @Override
  public AnalysisResultState analyseWithContext(HTTPMessage message, HTTPMessage fromStorage) {
    return AnalysisResultState.ALLOW;
  }

  @Override
  public Set<HTTPMessageType> messsageTypes() {
    return ImmutableSet.of(HTTPMessageType.Request, HTTPMessageType.Response);
  }
}
