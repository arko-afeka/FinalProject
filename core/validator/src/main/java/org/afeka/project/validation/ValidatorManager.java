package org.afeka.project.validation;

import org.afeka.project.model.AnalysisResult;
import org.afeka.project.model.AnalysisResultState;
import org.afeka.project.model.http.HTTPMessage;

import java.util.UUID;

public interface ValidatorManager {
  AnalysisResult validate(HTTPMessage message);

  AnalysisResult validateWithContext(HTTPMessage message, UUID uuid);
}
