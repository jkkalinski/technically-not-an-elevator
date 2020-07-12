package com.techelevator.tenmo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.model.Account;

@PreAuthorize("isAuthenticated()")
@RestController
public class AccountController {

	private AccountDAO accountDAO;
	
	public AccountController(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}
	
//  @RequestMapping(path = "/accounts", method = RequestMethod.GET)
//  public List<Account> listAllAccounts() {
//  	return accountDAO.findAll();
//  }
  
  @RequestMapping(path = "/accounts", method = RequestMethod.GET)
  public Account findAccountByUsername(@RequestParam (value = "username", required = true) String username) {
  	return accountDAO.findByUsername(username);
  }
  
  @RequestMapping(path = "/accounts/{id}", method = RequestMethod.PUT)
  public void updateAccount(@PathVariable ("id")int id, @RequestBody Account account) {
	  accountDAO.updateAccount(account);
  }
}
