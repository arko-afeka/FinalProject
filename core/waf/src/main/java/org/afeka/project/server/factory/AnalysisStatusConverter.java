package org.afeka.project.server.factory;

import com.google.protobuf.ByteString;
import org.afeka.project.model.AnalysisResult;
import org.afeka.project.waf.api.AnalysisStatus;
import org.afeka.project.waf.api.MessageStatus;
import org.afeka.project.waf.api.UUID;

public class AnalysisStatusConverter {
  private AnalysisResult result;

  public AnalysisStatusConverter(AnalysisResult result) {
    this.result = result;
  }

  private MessageStatus getMessageStatus() {
    switch (result.getState()) {
      case ALLOW:
        return MessageStatus.Allow;
      case BLOCK:
        return MessageStatus.Block;
    }

    return null;
  }

  public AnalysisStatus getResult() {
    return AnalysisStatus.newBuilder()
        .setStatus(getMessageStatus())
        .setUuid(
            UUID.newBuilder()
                .setData(ByteString.copyFrom(result.getUuid().toString().getBytes()))
                .build())
        .build();
  }
}
