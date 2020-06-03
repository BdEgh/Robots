package window;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"window"})

public class WindowsPosition {
	@JsonProperty("window")
	private List<Position> window = null;

	@JsonProperty("window")
	public List<Position> getWindows() {
		return window;
	}

	@JsonProperty("window")
	public void setWindows(List<Position> window) {
		this.window = window;
	}
}