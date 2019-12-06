package org.afeka.project.server;

import com.google.inject.Inject;
import io.grpc.stub.StreamObserver;
import org.afeka.project.waf.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WAFServiceImpl extends WAFService {
  @Override
  public void isValidRequest(
      HTTPRequest httpRequest, StreamObserver<AnalysisStatus> responseObserver) {
    // TODO: Implement logic for validating request
    super.isValidRequest(httpRequest, responseObserver);
  }

  @Override
  public void isValidResponse(
      HTTPResponse httpResponse, StreamObserver<AnalysisStatus> responseObserver) {
    // TODO: Implement logic for validating response
    super.isValidResponse(httpResponse, responseObserver);
  }

  @Override
  public void ping(Ping request, StreamObserver<Pong> responseObserver) {

    responseObserver.onCompleted();
  }
}
