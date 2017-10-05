/**
 * 
 */
package com.bolenum.coreservice.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolenum.coreservice.model.Transaction;

/**
 * @author chandan kumar singh
 * @date 29-Sep-2017
 */
public interface TransactionRepo extends JpaRepository<Transaction, Serializable> {
	public Transaction findByTxHash(String txHash);
}
