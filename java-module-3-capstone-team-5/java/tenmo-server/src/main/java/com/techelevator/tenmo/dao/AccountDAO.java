package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Account;

public interface AccountDAO {
	
	List<Account> findAll();
	
	Account findByUsername(String username);

	void updateAccount(Account account);
}
