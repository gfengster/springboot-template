package net.gfeng.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import net.gfeng.model.LineWordCount;

@RestController
@RequestMapping(path="/service")
public class CalculatorControl {
	//private static ExecutorService POOL = Executors.newFixedThreadPool(100);
	private static ExecutorService POOL = Executors.newCachedThreadPool();
	
	@RequestMapping(path="calculate/{start}/{end}", method=RequestMethod.GET)
	public ResponseEntity<Object> calculateControll(
			@PathVariable int start, @PathVariable final int end) {
		
		if (start == 0)
			start = 1;
		
		final File file = new File("./story.txt");
		if (!file.canRead())
			return new ResponseEntity<>("story.txt not found", HttpStatus.NOT_FOUND);

		final Collection<LineWordCount> result = new ArrayList<>(end -start + 1);
		final Collection<Future<LineWordCount>> futures = new ArrayList<>(end -start + 1);
		
		int count = 1;
		try (final Scanner scanner = new Scanner(file)){
			while(scanner.hasNextLine() && count <= end) {
				String line = scanner.nextLine();
				
				if (count >= start) {
					futures.add(POOL.submit(new CalculateCall(count, line)));
				}
				
				count++;
			}
			
			for (Future<LineWordCount> future : futures) {
				result.add(future.get());
			}
			
		} catch (FileNotFoundException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
			
			return new ResponseEntity<>("story.txt not found", HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@RequestMapping(path="calculate/{end}", method=RequestMethod.GET)
	public ResponseEntity<Object> calculateControll1(@PathVariable int end) {
		
		return calculateControll(1,  end);
	}
	
	
	private static class CalculateCall implements Callable<LineWordCount> {
		int lineNumber;
		String line;
		
		CalculateCall(int lineNumber, String line) {
			this.lineNumber = lineNumber;
			this.line = line;
		}

		@Override
		public LineWordCount call() throws Exception {
			
			String url = "http://localhost:8080/service/line/" + lineNumber;
			
			Request req = Request.Post(url);
			
			req.bodyByteArray(line.getBytes());
			
			Response resp = req.execute();
			
			//return Calculator.calculate(lineNumber, line);
		
			Gson gson = new Gson();
			LineWordCount result = gson.fromJson(resp.returnContent().asString(), LineWordCount.class);
			
			return result;
		}
	}
}
