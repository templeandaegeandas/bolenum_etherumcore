package com.bolenum.coreservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolenum.coreservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmailIdIgnoreCase(String emailId);
	
	public User findByUserId(Long id);
	
	public User findByEthWalletaddress(String address);
}
