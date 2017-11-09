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

import com.bolenum.coreservice.enums.CurrencyName;
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
		
			if (tx.getTo() != null) {
				logger.debug("tx.getTo() {}",tx.getTo());
				User user = userRepository.findByEthWalletaddress(tx.getTo());
				if (user != null) {
					logger.debug("new Incoming ethereum transaction for user : {}", user.getEmailId());
					saveTx(user, tx);
				}
			}

		}, Throwable::printStackTrace);

	}

	private void saveTx(User user, org.web3j.protocol.core.methods.response.Transaction transaction) {
		Transaction tx = transactionRepo.findByTxHash(transaction.getHash());
		if (tx == null) {
			tx = new Transaction();
			tx.setTxHash(transaction.getHash());
			tx.setFromAddress(transaction.getFrom());
			tx.setToAddress(transaction.getTo());
			tx.setGas(convertWeiToEther(transaction.getGas()));
			tx.setTxAmount(convertWeiToEther(transaction.getValue()));
			tx.setTransactionType(TransactionType.INCOMING);
			tx.setTransactionStatus(TransactionStatus.DEPOSIT);
			tx.setCurrencyName(CurrencyName.ETHEREUM);
			tx.setUser(user);
			Transaction saved = transactionRepo.saveAndFlush(tx);
			if (saved != null) {
				logger.debug("new incoming transaction saved of user: {}", user.getEmailId());
			}
		} else {
			if (tx.getTransactionStatus().equals(TransactionStatus.WITHDRAW)) {
				tx.setTransactionType(TransactionType.INCOMING);
			}
			logger.debug("tx exists: {}", transaction.getHash());
			tx.setGas(convertWeiToEther(transaction.getGas()));
			tx.setGasPrice(convertWeiToEther(transaction.getGasPrice()));
			transactionRepo.saveAndFlush(tx);
		}
	}

	@Override
	public void getBlockNumber() {
		web3j.blockObservable(true).subscribe(block -> {
			logger.debug("block number: {}", block.getBlock().getNumber() + " has just been created");
		}, Throwable::printStackTrace);
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
