package net.gfeng.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class LineWordCount {
	@Setter
	@Getter
	public int lineNumber;
	
	@Setter
	@Getter
	public int length;
	
	@Setter
	@Getter
	public String text;
	
	public LineWordCount(int lineNumber, int length, String text) {
		this.lineNumber = lineNumber;
		this.length = length;
		this.text = text;
	}

	@Override
	public String toString() {
		return "LineWordCount [lineNumber=" + lineNumber + ", length=" + length + ", text=" + text + "]";
	}
}
