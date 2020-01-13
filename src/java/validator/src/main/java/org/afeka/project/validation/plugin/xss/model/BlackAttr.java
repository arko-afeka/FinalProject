package org.afeka.project.validation.plugin.xss.model;

public enum BlackAttr {
  ACTION("ACTION", AttributeType.TYPE_ATTR_URL) /* form */,
  ATTRIBUTENAME(
      "ATTRIBUTENAME",
      AttributeType.TYPE_ATTR_INDIRECT) /* SVG allow indirection of attribute names */,
  BY("BY", AttributeType.TYPE_ATTR_URL) /* SVG */,
  BACKGROUND("BACKGROUND", AttributeType.TYPE_ATTR_URL) /* IE6, O11 */,
  DATAFORMATAS("DATAFORMATAS", AttributeType.TYPE_BLACK) /* IE */,
  DATASRC("DATASRC", AttributeType.TYPE_BLACK) /* IE */,
  DYNSRC("DYNSRC", AttributeType.TYPE_ATTR_URL) /* Obsolete img attribute */,
  FILTER("FILTER", AttributeType.TYPE_STYLE) /* Opera, SVG inline style */,
  FORMACTION("FORMACTION", AttributeType.TYPE_ATTR_URL) /* HTML 5 */,
  FOLDER("FOLDER", AttributeType.TYPE_ATTR_URL) /* Only on A tags, IE-only */,
  FROM("FROM", AttributeType.TYPE_ATTR_URL) /* SVG */,
  HANDLER("HANDLER", AttributeType.TYPE_ATTR_URL) /* SVG Tiny, Opera */,
  HREF("HREF", AttributeType.TYPE_ATTR_URL),
  LOWSRC("LOWSRC", AttributeType.TYPE_ATTR_URL) /* Obsolete img attribute */,
  POSTER("POSTER", AttributeType.TYPE_ATTR_URL) /* Opera 10,11 */,
  SRC("SRC", AttributeType.TYPE_ATTR_URL),
  STYLE("STYLE", AttributeType.TYPE_STYLE),
  TO("TO", AttributeType.TYPE_ATTR_URL) /* SVG */,
  VALUES("VALUES", AttributeType.TYPE_ATTR_URL) /* SVG */,
  XLINK("XLINK:HREF", AttributeType.TYPE_ATTR_URL);

  private String name;
  private AttributeType type;

  BlackAttr(String name, AttributeType type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public AttributeType getType() {
    return type;
  }
}
