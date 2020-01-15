package org.afeka.project.validation.plugin;

import com.google.common.base.Charsets;
import org.afeka.project.validation.plugin.sqli.libinjection.Libinjection;
import org.afeka.project.model.AnalysisResultState;
import org.afeka.project.model.http.HTTPMessage;
import org.afeka.project.model.http.HTTPRequestLine;
import org.afeka.project.validation.ValidationModule;

import java.net.URLDecoder;

public class SQLiModule implements ValidationModule {
  @Override
  public AnalysisResultState analyse(HTTPMessage message) {
    final HTTPRequestLine requestData = (HTTPRequestLine) message.getHeaderLine();

    if (requestData
        .getQueryParams()
        .values()
        .parallelStream()
        .filter(
            data -> {
              try {
                data = URLDecoder.decode(data, Charsets.UTF_8);
              } catch (Exception ignored) {
              }

              return new Libinjection().libinjection_sqli(data);
            })
        .findAny()
        .map(x -> AnalysisResultState.BLOCK)
        .orElse(AnalysisResultState.ALLOW)
        .equals(AnalysisResultState.BLOCK)) {
      return AnalysisResultState.BLOCK;
    }

    return AnalysisResultState.ALLOW;
  }

}
