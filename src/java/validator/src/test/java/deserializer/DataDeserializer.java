package deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.common.collect.Lists;
import model.owasp.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

public class DataDeserializer extends StdDeserializer<Data> {

  protected DataDeserializer(Class<?> vc) {
    super(vc);
  }

  protected DataDeserializer() {
    this(null);
  }

  @Override
  public Data deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    final JsonNode treeNode = p.getCodec().readTree(p);

    if (treeNode.isArray()) {
      List<String> data = Lists.newArrayList();
      for (JsonNode jsonNode : treeNode) {
        data.add(jsonNode.asText());
      }

      return new Data(StringUtils.join(data, "\r\n"));
    } else {
      return new Data(treeNode.asText());
    }
  }
}
