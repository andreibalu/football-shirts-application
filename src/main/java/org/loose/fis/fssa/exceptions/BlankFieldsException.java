package org.loose.fis.fssa.exceptions;

public class BlankFieldsException extends Exception {
	  public BlankFieldsException()
	  {
		  super(String.format("You forgot to complete a field, try again"));
	  }
	}
