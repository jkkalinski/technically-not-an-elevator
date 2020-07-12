package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Transfer;

@Service
public class TransferSqlDAO implements TransferDAO{

	private JdbcTemplate jdbcTemplate;

    public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
	
	@Override
	public List<Transfer> listAllTransfers() {
		List<Transfer> allTransfers = new ArrayList<>();
		String sqlQuery = "select * from transfers";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery);
		
		while(results.next()) {
			allTransfers.add(mapRowToTransfer(results));
		}
		
		return allTransfers;
	}
	
	@Override
	public List<Transfer> getAllTransfersByUserId(Long userId){
		List<Transfer> allTransfersByUser = new ArrayList<>();
		String sqlQuery = "select * from transfers where (account_from = ? or account_to = ?) and transfer_status_id <> 1";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, userId, userId);
		
		while(results.next()) {
			allTransfersByUser.add(mapRowToTransfer(results));
		}
		
		return allTransfersByUser;
	}
	
	@Override
	public List<Transfer> getAllPendingTransfersByUserId(Long userId) {
		List<Transfer> showMeMyTransfers = new ArrayList<>();
		String sqlQuery = "select * from transfers where (account_from = ? or account_to = ?) and transfer_status_id = 1";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlQuery, userId, userId);
		while(results.next()) {
			showMeMyTransfers.add(mapRowToTransfer(results));
		}
		return showMeMyTransfers;
	}

	@Override
	public Transfer getTransferById(Long id) {
		Transfer transfer = null;
		String sqlQuery = "select * from transfers where transfer_id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sqlQuery, id);
		
		while (result.next()) {
			transfer = mapRowToTransfer(result);
		}
		
		return transfer;
	}

	@Override
	public Transfer create(Transfer transfer) {
		
		transfer.setTransferId(getNextId());
		String sqlInsert = "insert into transfers (transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) "
						 + "VALUES (?,?,?,?,?,?)";
		jdbcTemplate.update(sqlInsert, transfer.getTransferId(), transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
		return transfer;
	}
	
	@Override
	public void updateTransfer(Long id, Transfer transfer) {
		String sqlUpdate = "update transfers set transfer_status_id =  ? where transfer_id = ?";
		jdbcTemplate.update(sqlUpdate, transfer.getTransferStatusId(), id);
	}
	
	
	
	private Long getNextId() {
		SqlRowSet nextId = jdbcTemplate.queryForRowSet("SELECT nextval('seq_transfer_id')");

		if (nextId.next()) {
			return nextId.getLong(1);
		} else {
			System.out.println("Something went wrong when gettng transfer_id id :(");
		}
		return 1L;
	}
	
	

	
	private Transfer mapRowToTransfer(SqlRowSet results) {
		Transfer transfer = new Transfer();
		transfer.setTransferStatusId(results.getInt("transfer_status_id"));
		transfer.setTransferTypeId(results.getInt("transfer_type_id"));
		transfer.setAccountFrom(results.getInt("account_from"));
		transfer.setTransferId(results.getLong("transfer_id"));
		transfer.setAccountTo(results.getInt("account_to"));
		transfer.setAmount(results.getDouble("amount"));
		
		return transfer;
	}
	
}
