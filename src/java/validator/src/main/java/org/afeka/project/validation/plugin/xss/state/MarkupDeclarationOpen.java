package org.afeka.project.validation.plugin.xss.state;

import org.afeka.project.validation.plugin.xss.util.StringReader;

public class MarkupDeclarationOpen extends State {

  public MarkupDeclarationOpen(StringReader data) {
    super(data);
  }

  protected MarkupDeclarationOpen(State state) {
    super(state);
  }

  @Override
  public void run() {
    if (data.startsWithIgnoreCase("DOCTYPE")) {
      setFromState(new DoctypeState(this));
    } else if (data.startsWith("[CDATA[")) {
      data.move(7);
      setFromState(new CdataState(this));
    } else if (data.startsWith("--")) {
      data.move(2);
      setFromState(new CommentState(this));
    } else {
      setFromState(new BogusCommentState(this));
    }
  }
}
