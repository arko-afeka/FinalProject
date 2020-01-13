package org.afeka.project.validation.plugin.xss.model;

public enum BlackTag {
  APPLET("APPLET"),
  BASE("BASE"),
  COMMENT("COMMENT"),
  EMBED("EMBED"),
  FRAME("FRAME"),
  FRAMESET("FRAMESET"),
  HANDLER("HANDLER"),
  IFRAME("IFRAME"),
  IMPORT("IMPORT"),
  ISINDEX("ISINDEX"),
  LINK("LINK"),
  LISTENER("LISTENER"),
  META("META"),
  NOSCRIPT("NOSCRIPT"),
  OBJECT("OBJECT"),
  SCRIPT("SCRIPT"),
  STYLE("STYLE"),
  VMLFRAME("VMLFRAME"),
  XML("XML"),
  XSS("XSS"),
  FORM("FORM");

  private String name;

  BlackTag(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
