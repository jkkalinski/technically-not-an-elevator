package com.techelevator;



import java.io.FileNotFoundException;

import java.math.BigDecimal;



public class Transaction {
	static final BigDecimal QUARTER = new BigDecimal("0.25");
	static final BigDecimal DIME = new BigDecimal("0.10");
	static final BigDecimal NICKEL = new BigDecimal("0.05");

	private BigDecimal amtOwed; 				// Created BigDecimals to store dollar amounts
	private BigDecimal amtTendered;
	private BigDecimal changeDue;
	

	
	
	

	public Transaction() throws FileNotFoundException {
		amtTendered = new BigDecimal(0);
		amtOwed = new BigDecimal(0);
		changeDue = new BigDecimal(0);
		
		
	}


	public BigDecimal getAmtOwed() {	
		return amtOwed;
	}
	
	public BigDecimal getAmtTendered() {
		return amtTendered;
	}
	
	public BigDecimal getChangeDue() {
		return changeDue;
	}

	public void setAmtOwed(BigDecimal price) {			// Stores amount owed, equivalent to price of the slot that is selected
		amtOwed = amtOwed.add(price);
	}

	public void deposit(int cash) {				// Stores the whole dollar amount that the user put in at start of transaction
		BigDecimal preciseCash = new BigDecimal(cash);
		amtTendered = amtTendered.add(preciseCash);
		changeDue = amtTendered;
		
		
		
	}

	

	public String applyDeposit() {															//Reduces deposited money available by the price of purchased item
		if (amtTendered.compareTo(amtOwed)==1 || amtTendered.compareTo(amtOwed)== 0) {		//Stores remaining deposit as change due
			amtTendered = amtTendered.subtract(amtOwed);										//Prevents transaction if amount tendered is less than price of item
			amtOwed = new BigDecimal(0);
			changeDue = amtTendered;
			
			return "Remaining funds: " + amtTendered;
		} else {
			return "Insufficient funds.";
		}																				//NEED TO ADD ABILITY TO KEEP MONEY IN AND BUY ANOTHER THING
	}

	public String changeCoinifier() {		
		int numQuarter;
		int numDime;
		int numNickel;
		BigDecimal whatsLeft = new BigDecimal(0);

		numQuarter = changeDue.divideAndRemainder(QUARTER)[0].intValue(); 			// Expected result: 1.10 / .25 gives {4, 0.10}
		whatsLeft = whatsLeft.add(changeDue.divideAndRemainder(QUARTER)[1]);

		numDime = whatsLeft.divideAndRemainder(DIME)[0].intValue();					// Expected result: 0.10 / 0.10 give {1, 0.0}
		whatsLeft = whatsLeft.divideAndRemainder(DIME)[1];
		

		numNickel = whatsLeft.divideAndRemainder(NICKEL)[0].intValue();

		return  numQuarter + " quarters, " + numDime + " dimes, and " + numNickel + " nickels.";

		
	}
	
	
	

}
