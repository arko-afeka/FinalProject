package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Test {
  @JsonProperty("test_title")
  private String testTitle;

  private String desc;

  private List<ParentStage> stages;

  public Test() {

  }

  public Test(String testTitle, String desc, List<ParentStage> stages) {
    this.testTitle = testTitle;
    this.desc = desc;
    this.stages = stages;
  }

  public String getTestTitle() {
    return testTitle;
  }

  public String getDesc() {
    return desc;
  }

  public List<ParentStage> getStages() {
    return stages;
  }

    @Override
    public String toString() {
      return testTitle;
    }
}
