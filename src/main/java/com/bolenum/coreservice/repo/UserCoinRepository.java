package com.bolenum.coreservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolenum.coreservice.model.User;
import com.bolenum.coreservice.model.UserCoin;

public interface UserCoinRepository extends JpaRepository<UserCoin, Long> {

	UserCoin findByTokenNameAndUser(String tokenName, User user);

	UserCoin findByWalletAddress(String walletAddress);
}
