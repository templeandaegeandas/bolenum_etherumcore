/**
 * 
 */
package com.bolenum.coreservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bolenum.coreservice.enums.TransactionStatus;
import com.bolenum.coreservice.enums.TransactionType;

/**
 * @author chandan kumar singh
 * @date 28-Sep-2017
 */

@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@CreationTimestamp
	private Date createdOn;

	@UpdateTimestamp
	private Date updatedOn;

	@Column(unique = true)
	private String txHash;

	private String fromAddress;
	private String toAddress;
	private Double txFee;
	private Double txAmount;
	private String txDescription;

	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	@Enumerated(EnumType.STRING)
	private TransactionStatus transactionStatus;
	private Double gas;
	private Double gasPrice;

	private String currencyName;

	@ManyToOne
	private User fromUser;

	@ManyToOne
	private User toUser;

	private String currenctType;

	public Transaction() {

	}

	public Transaction(String txHash, String fromAddress, String toAddress, Double txFee, Double txAmount,
			String txDescription, TransactionType transactionType, Double gas, Double gasPrice) {
		this.txHash = txHash;
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.txFee = txFee;
		this.txAmount = txAmount;
		this.txDescription = txDescription;
		this.transactionType = transactionType;
		this.gas = gas;
		this.gasPrice = gasPrice;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * the id to set
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * the createdOn to set
	 * 
	 * @param createdOn
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the updatedOn
	 */
	public Date getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * the updatedOn to set
	 * 
	 * @param updatedOn
	 */
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * @return the txHash
	 */
	public String getTxHash() {
		return txHash;
	}

	/**
	 * the txHash to set
	 * 
	 * @param txHash
	 */
	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}

	/**
	 * @return the fromAddress
	 */
	public String getFromAddress() {
		return fromAddress;
	}

	/**
	 * the fromAddress to set
	 * 
	 * @param fromAddress
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	/**
	 * @return the toAddress
	 */
	public String getToAddress() {
		return toAddress;
	}

	/**
	 * @param toAddress
	 *            the toAddress to set
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	/**
	 * @return the txFee
	 */
	public Double getTxFee() {
		return txFee;
	}

	/**
	 * @param txFee
	 *            the txFee to set
	 */
	public void setTxFee(Double txFee) {
		this.txFee = txFee;
	}

	/**
	 * @return the txAmount
	 */
	public Double getTxAmount() {
		return txAmount;
	}

	/**
	 * @param txAmount
	 *            the txAmount to set
	 */
	public void setTxAmount(Double txAmount) {
		this.txAmount = txAmount;
	}

	/**
	 * @return the txDescription
	 */
	public String getTxDescription() {
		return txDescription;
	}

	/**
	 * @param txDescription
	 *            the txDescription to set
	 */
	public void setTxDescription(String txDescription) {
		this.txDescription = txDescription;
	}

	/**
	 * @return the transactionType
	 */
	public TransactionType getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType
	 *            the transactionType to set
	 */
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the gas which is ethereum tx fee
	 */
	public Double getGas() {
		return gas;
	}

	/**
	 * @param gas
	 *            the gas to set
	 */
	public void setGas(Double gas) {
		this.gas = gas;
	}

	/**
	 * @return the gasPrice
	 */
	public Double getGasPrice() {
		return gasPrice;
	}

	/**
	 * @param gasPrice
	 *            the gasPrice to set
	 */
	public void setGasPrice(Double gasPrice) {
		this.gasPrice = gasPrice;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getCurrenctType() {
		return currenctType;
	}

	public void setCurrenctType(String currenctType) {
		this.currenctType = currenctType;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
}
