package window;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {
    @JsonProperty("w")
    private Integer width;

    @JsonProperty("h")
    private Integer height;

    @JsonProperty("x")
    private Integer x;

    @JsonProperty("y")
    private Integer y;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("w")
    public Integer getWidth() {
        return width;
    }

    @JsonProperty("w")
    public void setWidth(Integer width) {
        this.width = width;
    }

    @JsonProperty("h")
    public Integer getHeight() {
        return height;
    }

    @JsonProperty("h")
    public void setHeight(Integer height) {
        this.height = height;
    }

    @JsonProperty("x")
    public Integer getX() {
        return x;
    }

    @JsonProperty("x")
    public void setX(Integer x) {
        this.x = x;
    }

    @JsonProperty("y")
    public Integer getY() {
        return y;
    }

    @JsonProperty("y")
    public void setY(Integer y) {
        this.y = y;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
