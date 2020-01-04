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
  @Inject private ContextStorage storage;

  @Inject
  public ValidatorManagerImpl(Set<ValidationModule> validations) {
    this.validations = validations;
  }

  @Override
  public AnalysisResult validateRequest(HTTPMessage message) {
    AnalysisResultState result =
        validations
            .parallelStream()
            .filter(
                validationModule -> validationModule.messsageTypes().contains(message.getType()))
            .map(validationModule -> validationModule.analyse(message))
            .filter(AnalysisResultState.BLOCK::equals)
            .findFirst()
            .orElse(AnalysisResultState.ALLOW);

    UUID storageId = null;
    if (result.equals(AnalysisResultState.ALLOW)) {
      storageId = storage.addToStorage(message);
    }

    return new AnalysisResult(result, storageId);
  }

  @Override
  public AnalysisResult validateResponse(HTTPMessage message, UUID uuid) {
    HTTPMessage fromStorage = storage.removeFromStorage(uuid);

    AnalysisResultState analysisResultState =
        validations
            .parallelStream()
            .filter(
                validationModule -> validationModule.messsageTypes().contains(message.getType()))
            .map(
                validationModule ->
                    Objects.nonNull(fromStorage)
                        ? validationModule.analyseWithContext(message, fromStorage)
                        : validationModule.analyse(message))
            .filter(AnalysisResultState.BLOCK::equals)
            .findFirst()
            .orElse(AnalysisResultState.ALLOW);

    return new AnalysisResult(analysisResultState, uuid);
  }
}
