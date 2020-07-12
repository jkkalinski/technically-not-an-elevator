package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Account;

@Service
public class AccountSqlDAO implements AccountDAO{
	
	private JdbcTemplate jdbcTemplate;

    public AccountSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
	@Override
	public List<Account> findAll() {
		String sqlQuery = "select * from accounts";
		List<Account> accounts = new ArrayList<Account>();
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
		while (results.next()) {
			accounts.add(mapRowToAccount(results));
		}
		return accounts;
	}

	@Override
	public Account findByUsername(String username) {
		String sqlQuery = "select * from accounts where user_id = (select user_id from users where username = ?)";
		Account account = null;
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, username);
		while (results.next()) {
			account = mapRowToAccount(results);
		}
		return account;

	}
	
	private Account mapRowToAccount(SqlRowSet s) {
		Account newAccount = new Account();
		newAccount.setAccountId(s.getLong("account_id"));
		newAccount.setBalance(s.getDouble("balance"));
		newAccount.setUserId(s.getInt("user_id"));
		return newAccount;
	}

	@Override
	public void updateAccount(Account account) {
		String sqlQuery = "update accounts set balance = ? where account_id = ?";
		jdbcTemplate.update(sqlQuery, account.getBalance(), account.getAccountId());
		return;
	}

}
