package com.qa.opencart.exceptions;

public class FrameWorkException extends RuntimeException {

	
	
	public FrameWorkException(String msg)			// will be called when we creatae obj of this class
	{
		super(msg);									// constructor of RuntimeException will get called.
		printStackTrace();
	}
	
	
}
