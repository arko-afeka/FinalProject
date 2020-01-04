package org.afeka.project.validation.plugin;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.afeka.project.model.AnalysisResultState;
import org.afeka.project.model.http.HTTPMessage;
import org.afeka.project.model.http.HTTPMessageType;
import org.afeka.project.validation.ValidationModule;
import org.afeka.project.validation.plugin.xss.Tokenizer;
import org.afeka.project.validation.plugin.xss.model.Token;
import org.afeka.project.validation.plugin.xss.model.ValidatorFlag;

import java.util.List;
import java.util.Set;

public class XSSModule implements ValidationModule {
  @Override
  public AnalysisResultState analyse(HTTPMessage message) {
    Tokenizer tokenizer = new Tokenizer(ValidatorFlag.DEFAULT, message.getBody());
    List<Token> tokens = Lists.newArrayList();

    while (tokenizer.hasNext()) {
      tokens.add(tokenizer.next());
    }

    System.out.println(tokens);

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
