package org.afeka.project.server;

import com.google.inject.Inject;
import io.grpc.stub.StreamObserver;
import org.afeka.project.model.AnalysisResult;
import org.afeka.project.model.AnalysisResultState;
import org.afeka.project.model.http.HTTPMessage;
import org.afeka.project.server.factory.AnalysisStatusConverter;
import org.afeka.project.util.http.HTTPMessageParser;
import org.afeka.project.validation.ValidatorManager;
import org.afeka.project.waf.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class WAFServiceImpl extends WAFService {
  private static final Logger log = LoggerFactory.getLogger(WAFService.class);

  @Inject HTTPMessageParser parser;
  @Inject ValidatorManager validatorManager;

  @Override
  public void isValidRequest(
      HTTPRequest httpRequest, StreamObserver<AnalysisStatus> responseObserver) {
    try {
      validatorManager.validate(parser.getMessage(httpRequest.getData()));
    } catch (Exception ex) {
      log.error("Couldn't process the message blocking", ex);
      responseObserver.onNext(
          new AnalysisStatusConverter(new AnalysisResult(AnalysisResultState.BLOCK, null))
              .getResult());
    }

    responseObserver.onCompleted();
  }

  @Override
  public void isValidResponse(
      HTTPResponse httpResponse, StreamObserver<AnalysisStatus> responseObserver) {
    try {
      validatorManager.validateWithContext(
          parser.getMessage(httpResponse.getData()),
          UUID.nameUUIDFromBytes(httpResponse.getUuid().getData().toByteArray()));
    } catch (Exception ex) {
      log.error("Couldn't process the message blocking", ex);
      responseObserver.onNext(
          new AnalysisStatusConverter(new AnalysisResult(AnalysisResultState.BLOCK, null))
              .getResult());
    }

    responseObserver.onCompleted();
  }

  @Override
  public void ping(Ping request, StreamObserver<Pong> responseObserver) {
    responseObserver.onNext(Pong.newBuilder().build());
    responseObserver.onCompleted();
  }
}
