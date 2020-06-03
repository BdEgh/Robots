package window;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Position {
	@JsonProperty("isIcon")
	private Boolean isIcon;

	@JsonProperty("w")
	private Integer width;

	@JsonProperty("h")
	private Integer height;

	@JsonProperty("x")
	private Integer x;

	@JsonProperty("y")
	private Integer y;

	@JsonProperty("isIcon")
	public Boolean getIconState() {
		return isIcon;
	}

	@JsonProperty("isIcon")
	public void setIconState(Boolean iconState) {
		this.isIcon = iconState;
	}

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
}
