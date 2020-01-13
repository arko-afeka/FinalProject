package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import deserializer.DataDeserializer;

@JsonDeserialize(using = DataDeserializer.class)
public class Data {
  private String data;

  public Data(String data) {
    this.data = data;
  }

  public Data() {}

  public String getData() {
    return data;
  }

  @Override
  public String toString() {
    return "Data{" +
            "data='" + data + '\'' +
            '}';
  }
}
