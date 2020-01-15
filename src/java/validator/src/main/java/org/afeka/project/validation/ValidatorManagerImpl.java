package org.afeka.project.validation;

import com.google.inject.Inject;
import org.afeka.project.model.AnalysisResult;
import org.afeka.project.model.AnalysisResultState;
import org.afeka.project.model.http.HTTPMessage;
import org.afeka.project.storage.ContextStorage;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class ValidatorManagerImpl implements ValidatorManager {
  private final Set<ValidationModule> validations;

  @Inject
  public ValidatorManagerImpl(Set<ValidationModule> validations) {
    this.validations = validations;
  }

  @Override
  public AnalysisResult validateRequest(HTTPMessage message) {
    AnalysisResultState result =
        validations
            .parallelStream()
            .map(validationModule -> validationModule.analyse(message))
            .filter(AnalysisResultState.BLOCK::equals)
            .findFirst()
            .orElse(AnalysisResultState.ALLOW);

    return new AnalysisResult(result);
  }
}
