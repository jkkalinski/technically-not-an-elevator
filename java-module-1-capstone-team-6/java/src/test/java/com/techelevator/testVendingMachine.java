package com.techelevator;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Set;

import org.junit.Test;

import junit.framework.Assert;



public class testVendingMachine {
	
	VendingMachine testVM = new VendingMachine();   // Just pulled this out so we could reuse a vending machine for all the tests
	
	
	
	@Test
	public void does_the_inventory_exist() {
		
		assertNotEquals(null, testVM.getSlotMap());
	}
	

	
	@Test
	
	public void does_inventory_contain_key() {					
		
		VendingMachine testVM = new VendingMachine();
		Set<String> keyExists = testVM.getSlotKeys();
		
		assertTrue(keyExists.contains("A1"));
	}
	
	
	
	@Test
	public void apply_deposit() throws FileNotFoundException {					
		Transaction test = new Transaction();
		
		BigDecimal amtTendered = new BigDecimal("1.00");
		BigDecimal amtOwed = new BigDecimal("1.00");
		
		String result = test.applyDeposit();
		
		assertEquals("Remaining funds: 0", result);
	}
	
	
	@Test
	public void apply_deposit_amtTendered_less_than_Owed() throws FileNotFoundException {					
		Transaction testOwed = new Transaction();
		
		testOwed.setAmtOwed(new BigDecimal(2.00));
		testOwed.deposit(1);
		
		String result = testOwed.applyDeposit();
		
		assertEquals("Insufficient funds.", result);
	}
	
	
}
