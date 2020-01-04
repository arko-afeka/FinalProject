package org.afeka.project.validation.plugin;

import com.google.common.collect.ImmutableSet;
import org.afeka.project.libinjection.Libinjection;
import org.afeka.project.model.AnalysisResultState;
import org.afeka.project.model.http.HTTPMessage;
import org.afeka.project.model.http.HTTPMessageType;
import org.afeka.project.model.http.HTTPRequestLine;
import org.afeka.project.validation.ValidationModule;

import java.util.Set;

public class SQLiModule implements ValidationModule {
  @Override
  public AnalysisResultState analyse(HTTPMessage message) {
    final HTTPRequestLine requestData = (HTTPRequestLine) message.getHeaderLine();

    return requestData
        .getQueryParams()
        .values()
        .parallelStream()
        .filter(data -> new Libinjection().libinjection_sqli(data))
        .findAny()
        .map(x -> AnalysisResultState.BLOCK)
        .orElse(AnalysisResultState.ALLOW);
  }

  @Override
  public AnalysisResultState analyseWithContext(HTTPMessage message, HTTPMessage fromStorage) {
    return AnalysisResultState.ALLOW;
  }

  @Override
  public Set<HTTPMessageType> messsageTypes() {
    return ImmutableSet.of(HTTPMessageType.Request);
  }
}
