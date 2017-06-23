package com.javacodegeeks.examples.boot.app;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller
@RequestMapping("/stream")
public class StreamController {

	private SseEmitter sseEmitter;

	@RequestMapping("/no")
	@ResponseBody
	public String home() {
		// System.out.println("Hit me.");
		return "Hello Stream!";
	}

	@RequestMapping("/no/int/list")
	@ResponseBody
	public List<String> listInt() {
		// System.out.println("Hit me.");
		return Arrays.asList("1", "2", "3");
	}

	@RequestMapping("/no/string/list")
	@ResponseBody
	public List<String> listString() {
		// System.out.println("Hit me.");
		return Arrays.asList("test1", "test2", "test3");
	}

	@RequestMapping("/yes")
	public StreamingResponseBody handleRequest() {
		System.out.println("Called stream...");
		return new StreamingResponseBody() {
			@Override
			public void writeTo(OutputStream out) throws IOException {
				for (int i = 0; i < 1000; i++) {
					out.write((Integer.toString(i) + " - ").getBytes());
					out.flush();
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

	@RequestMapping("/event")
	public SseEmitter getRealTimeMessageAction(HttpServletRequest request) {
		SseEmitter sseEmitter = new SseEmitter();
		// You can send message here
		try {
			for (int i = 0; i < 5; i++) {
				sseEmitter.send("Message #" + i);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sseEmitter;
	}

	@RequestMapping("/event2")
	public SseEmitter getRealTimeMessageAction2(HttpServletRequest request) {
		SseEmitter sseEmitter = new SseEmitter();
		runAsync(sseEmitter);
		return sseEmitter;
	}

	private void runAsync(SseEmitter sseEmitter) {
		for (int i = 0; i < 3; i++) {
			AsyncThread at = new AsyncThread();
			at.setEmitter(sseEmitter);
			at.setSleep((6-(i*2))*1000);
			at.setMessageId(i);
			at.start();
		}
	}

	private class AsyncThread extends Thread {
		private SseEmitter sseEmitter;
		private int sleep;
		private int id;
		public void setEmitter(SseEmitter sseEmitter) {
			this.sseEmitter = sseEmitter;
		}

		public void setSleep(int sleep) {
			this.sleep = sleep;
		}
		
		public void setMessageId(int id) {
			this.id = id;
		}
		public void run() {
			try {
				try {
					Thread.sleep(this.sleep);
					System.out.println("Timestamp:" + SimpleDateFormat.getDateTimeInstance().format(new Date().getTime()));
					this.sseEmitter.send("Message " + this.id);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
