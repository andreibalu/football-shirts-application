package org.loose.fis.fssa.exceptions;

public class WrongFieldsException extends Exception {
	  public WrongFieldsException()
	  {
		  super(String.format("You entered a shirt that does not exist, enter again"));
	  }
	}