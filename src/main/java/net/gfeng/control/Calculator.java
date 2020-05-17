package net.gfeng.control;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.gfeng.model.LineWordCount;

@RestController
@RequestMapping(path="/service")
public class Calculator {

	@Value("${net.gfeng.greatest}")
	public String myValue;
	
	@GetMapping("/myvalue")
	public String getConstant() {
		return myValue;
	}
	
	@RequestMapping(path="line/{line}", method=RequestMethod.POST)
	public ResponseEntity<LineWordCount> calculateLine(@PathVariable int line,
			@RequestBody String text){
		
		LineWordCount count = calculate(line, text);
		
		return new ResponseEntity<>(count, HttpStatus.OK);
	}
	
	public static LineWordCount calculate(int lineNumber, final String line) {
		LineWordCount count = new LineWordCount(lineNumber, line.length(), line);

		return count;
	}
}
