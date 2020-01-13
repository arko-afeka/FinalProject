package org.afeka.project.validation.plugin;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;
import org.afeka.project.libinjection.Libinjection;
import org.afeka.project.model.AnalysisResultState;
import org.afeka.project.model.http.HTTPMessage;
import org.afeka.project.model.http.HTTPMessageType;
import org.afeka.project.model.http.HTTPRequestLine;
import org.afeka.project.validation.ValidationModule;

import java.net.URLDecoder;
import java.util.Set;

public class SQLiModule implements ValidationModule {
  @Override
  public AnalysisResultState analyse(HTTPMessage message) {
    final HTTPRequestLine requestData = (HTTPRequestLine) message.getHeaderLine();

    if (requestData
        .getQueryParams()
        .values()
        .parallelStream()
        .filter(
            data -> new Libinjection().libinjection_sqli(URLDecoder.decode(data, Charsets.UTF_8)))
        .findAny()
        .map(x -> AnalysisResultState.BLOCK)
        .orElse(AnalysisResultState.ALLOW)
        .equals(AnalysisResultState.BLOCK)) {
      return AnalysisResultState.BLOCK;
    }

    return AnalysisResultState.ALLOW;
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
