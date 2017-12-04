/**
 * 
 */
package com.bolenum.coreservice.services;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.bolenum.coreservice.enums.TransactionStatus;
import com.bolenum.coreservice.enums.TransactionType;
import com.bolenum.coreservice.model.Transaction;
import com.bolenum.coreservice.model.User;
import com.bolenum.coreservice.repo.TransactionRepo;
import com.bolenum.coreservice.repo.UserRepository;

/**
 * @author chandan kumar singh
 * @date 02-Oct-2017
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	private Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionRepo transactionRepo;

	private Web3j web3j;

	@PostConstruct
	void init() {
		web3j = Web3j.build(new HttpService("http://165.227.86.165:8000"));
	}

	@Override
	public void saveEthereumIncomingTx() {
		logger.debug("method for transaction event called");
		web3j.transactionObservable().subscribe(tx -> {
			logger.debug("transaction to address: {}", tx.getTo());
			if (tx.getTo() != null) {
				User toUser = userRepository.findByEthWalletaddress(tx.getTo());
				User fromUser = userRepository.findByEthWalletaddress(tx.getFrom());
				if (toUser != null) {
					logger.debug("new Incoming ethereum transaction for user : {}", toUser.getEmailId());
					saveTx(toUser, fromUser, tx);
				}
			}

		}, error -> {
			logger.error("error in transaction listen: {}", error.getMessage());
			error.printStackTrace();
		});

	}

	private void saveTx(User toUser, User fromUser, org.web3j.protocol.core.methods.response.Transaction transaction) {
		Transaction tx = transactionRepo.findByTxHash(transaction.getHash());
		BigInteger feeInWei = transaction.getGas().multiply(transaction.getGasPrice());
		if (tx == null) {
			tx = new Transaction();
			tx.setTxFee(convertWeiToEther(feeInWei));
			tx.setTxHash(transaction.getHash());
			tx.setFromAddress(transaction.getFrom());
			tx.setToAddress(transaction.getTo());
			tx.setGas(convertWeiToEther(transaction.getGas()));
			tx.setGasPrice(convertWeiToEther(transaction.getGasPrice()));
			tx.setTxAmount(convertWeiToEther(transaction.getValue()));
			tx.setTransactionType(TransactionType.INCOMING);
			tx.setTransactionStatus(TransactionStatus.DEPOSIT);
			tx.setCurrencyName("ETH");
			tx.setFromUser(fromUser);
			tx.setToUser(toUser);
			Transaction saved = transactionRepo.saveAndFlush(tx);
			if (saved != null) {
				logger.debug("new incoming transaction saved of user: {}", toUser.getEmailId());
			}
		} else {
			if (tx.getTransactionStatus().equals(TransactionStatus.WITHDRAW)) {
				tx.setTransactionType(TransactionType.INCOMING);
			}
			logger.debug("tx exists: {}", transaction.getHash());
			tx.setGas(convertWeiToEther(transaction.getGas()));
			tx.setGasPrice(convertWeiToEther(transaction.getGasPrice()));
			tx.setTxFee(convertWeiToEther(feeInWei));
			transactionRepo.saveAndFlush(tx);
		}
	}

	@Override
	public void getBlockNumber() {
		web3j.blockObservable(true).subscribe(block -> {
			logger.debug("block number: {}", block.getBlock().getNumber() + " has just been created");
		}, error -> {
			logger.error("error in block number listen: {}", error.getMessage());
		});
	}

	/**
	 * @description to convert value from wei to either
	 * @param amount
	 *            in wei
	 * @return either
	 */
	private Double convertWeiToEther(BigInteger amount) {
		logger.debug("amount in Wei: {}", amount);
		BigDecimal balance = new BigDecimal(amount);
		BigDecimal conversionRate = new BigDecimal(new BigInteger("1000000000000000000"));
		BigDecimal amountInEther = balance.divide(conversionRate);
		logger.debug("amount in eth: {}", amountInEther.doubleValue());
		return amountInEther.doubleValue();
	}
}
