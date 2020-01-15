package org.afeka.project.validation.plugin;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.afeka.project.model.AnalysisResultState;
import org.afeka.project.model.http.HTTPHeader;
import org.afeka.project.model.http.HTTPMessage;
import org.afeka.project.model.http.HTTPMessageType;
import org.afeka.project.validation.ValidationModule;
import org.afeka.project.validation.ValidationModuleConfiguration;
import org.afeka.project.validation.plugin.csrf.AntiCSRFTokenType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CSRFModule implements ValidationModule {
  private static final Logger logger = LoggerFactory.getLogger(CSRFModule.class);

  private List<String> protectedURI;
  private AntiCSRFTokenType tokenType;
  private String tokenName;
  private String domain;

  @Inject
  public CSRFModule(@Named("csrf") ValidationModuleConfiguration configuration) {
    protectedURI = configuration.getValue("protected");
    tokenType = configuration.getValue("token.type");
    tokenName = configuration.getValue("token.name");
    domain = configuration.getValue("domain");
  }

  @Override
  public AnalysisResultState analyse(HTTPMessage message) {
    if (protectedURI
        .parallelStream()
        .noneMatch(
            protectedUri ->
                StringUtils.endsWithIgnoreCase(message.getHeaderLine().getUri(), protectedUri))) {
      return AnalysisResultState.ALLOW;
    }

    if (!message.getHeaders().getOrDefault(HTTPHeader.REFERER.getName(), "").startsWith(domain)) {
      logger.warn("Blocked request due to bad referer");
      return AnalysisResultState.BLOCK;
    }

    switch (tokenType) {
      case COOKIE:
        if (Objects.isNull(message.getCookies().get(tokenName))) {
          return AnalysisResultState.BLOCK;
        }
        break;
      case PARAM:
        if (Objects.isNull(message.getHeaderLine().getQueryParams().get(tokenName))) {
          return AnalysisResultState.BLOCK;
        }
        break;
    }

    return AnalysisResultState.ALLOW;
  }

}
