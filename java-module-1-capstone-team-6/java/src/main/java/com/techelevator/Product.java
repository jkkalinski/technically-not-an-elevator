package com.techelevator;

public class Product {
	
	String name;					//Product only knows its name and type, other data is determined by whoever sets up the vending machine
	String type;
	
	
	public Product(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
	
	public String returnMessage() {							//Provides a string based on what type of product it is, to be used following a successful transaction
		if (type.equals("Chip")) {
			return "Crunch Crunch, Yum!";
		} else if (type.equals("Candy")) {
			return "Munch Munch, Yum!";
		} else if (type.equals("Drink")) {
			return "Glug Glug, Yum!";
		} else {
			return "Chew Chew, Yum!";
		}
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
