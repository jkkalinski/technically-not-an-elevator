package com.techelevator;

import java.math.BigDecimal;

public class Slot {
	
	public final static int STARTING_QUANTITY = 5;
	
	/**
	 * Declared attributes of a single slot, which includes the Product object the slot might contain
	 */
	Product snack;
	BigDecimal price;
	int quantity;
	boolean isSoldOut;
	
	public Slot(Product snack, BigDecimal price) {			//Initializes a slot with a product and price, defaults to 5 quantity and "not sold out"
		
		this.snack = snack;
		this.price = price;
		this.quantity = STARTING_QUANTITY;
		this.isSoldOut = isSoldOut();
		
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public boolean isSoldOut() {							// Checks quantity to determine if sold out
		if (this.quantity == 0) {
			return isSoldOut = true;
		} else {
			return false;
		}
	}
	
	public String dispense() {								// Decrements the quantity as long as the slot item isn't sold out
		if (!isSoldOut()) {									// Slot does not know if you paid enough to dispense, VM determines this and calls dispense
			quantity--;
			return "Take your " + this.snack + " for $" + this.price;
		}  else {
			return "This item is sold out";
		}
	}
	
	public BigDecimal getPrice() {							// Returns price of slot item as a dollar amount
		return this.price;
	}
	
	public Product getSnack() {
		return this.snack;
	}
	
	@Override
	public String toString() {								// Returns a Price and product string to be displayed in console
		return "$" + this.price + " " + this.snack;
	}
	
}

