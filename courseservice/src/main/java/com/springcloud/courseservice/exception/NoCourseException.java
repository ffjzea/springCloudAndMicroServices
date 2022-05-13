package com.springcloud.courseservice.exception;

import java.io.PrintWriter;

public class NoCourseException extends Exception {

	public NoCourseException(String message) {
		super(message);
	}

}
