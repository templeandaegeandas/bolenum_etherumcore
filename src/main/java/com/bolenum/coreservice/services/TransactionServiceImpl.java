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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.bolenum.coreservice.enums.TransactionStatus;
import com.bolenum.coreservice.enums.TransactionType;
import com.bolenum.coreservice.enums.TransferStatus;
import com.bolenum.coreservice.model.Transaction;
import com.bolenum.coreservice.model.User;
import com.bolenum.coreservice.model.UserCoin;
import com.bolenum.coreservice.repo.TransactionRepo;
import com.bolenum.coreservice.repo.UserCoinRepository;

/**
 * @author chandan kumar singh
 * @date 02-Oct-2017
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	private Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	private UserCoinRepository userCoinRepository;

	@Autowired
	private TransactionRepo transactionRepo;

	private Web3j web3j;

	@Value("${service.url}")
	private String url;

	@PostConstruct
	void init() {
		web3j = Web3j.build(new HttpService(url));
	}

	/**
	 *  
	 */
	@Override
	public void saveEthereumIncomingTx() {
		logger.debug("method for transaction event called");
		web3j.transactionObservable().subscribe(tx -> {
			logger.debug("transaction to address: {} and condition: {}", tx.getTo(), tx.getTo() != null);
			if (tx.getTo() != null) {
				UserCoin userCoin = userCoinRepository.findByWalletAddress(tx.getTo());
				if (userCoin != null) {
					logger.debug("new Incoming ethereum transaction for user : {}", userCoin.getUser().getEmailId());
					saveTx(userCoin.getUser(), tx);
				}
			}
		}, error -> {
			logger.error("error in transaction listen: {}", error.getMessage());
			error.printStackTrace();
		});
	}

	private void saveTx(User toUser, org.web3j.protocol.core.methods.response.Transaction transaction) {
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
			tx.setTransferStatus(TransferStatus.INITIATED);
			if (toUser.getUserId() == 1) {
				tx.setTransferStatus(TransferStatus.COMPLETED);
			}
			tx.setCurrencyName("ETH");
			tx.setToUser(toUser);
			Transaction saved = transactionRepo.saveAndFlush(tx);
			if (saved != null) {
				logger.debug("new incoming transaction saved of user: {}", toUser.getEmailId());
			}
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
