package org.loose.fis.fssa.exceptions;

public class NotEnoughStockException extends Exception {

	public NotEnoughStockException(int quantity)
	{
		super(String.format("Not enough shirts on stock, please select a quantity lower or equal to "+String.valueOf(quantity)));
	}
}
