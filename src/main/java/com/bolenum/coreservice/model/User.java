package com.bolenum.coreservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;

	@NotNull
	private String firstName;

	private String middleName;

	private String lastName;

	@NotNull
	@Column(unique = true)
	private String emailId;

	@NotNull
	private String password;

	private String address;

	private String city;

	private String state;

	private String country;

	private String mobileNumber;
	
	private Boolean isMobileVerified = false;

	private String gender;

	private Date dob;

	private String profileImage;

	private Boolean termsConditions = false;

	private Boolean isEnabled = false;

	private Boolean isDeleted = false;

	private Boolean isLocked = false;

	@CreationTimestamp
	private Date createdOn;

	@UpdateTimestamp
	private Date updatedOn;

	private Date deletedOn;

	private String btcWalletUuid;
	
	private String ethWalletaddress;
	
	private String ethWalletPwd;
	
	private String ethWalletPwdKey;
	
	private String ethWalletJsonFileName;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return this.firstName + (this.middleName == null ? "" : this.middleName)
				+ (this.lastName == null ? "" : this.lastName);
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Boolean getIsMobileVerified() {
		return isMobileVerified;
	}

	public void setIsMobileVerified(Boolean isMobileVerified) {
		this.isMobileVerified = isMobileVerified;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Boolean getTermsConditions() {
		return termsConditions;
	}

	public void setTermsConditions(Boolean termsConditions) {
		this.termsConditions = termsConditions;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn
	 *  the createdOn to set
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
	 * @param updatedOn
	 * the updatedOn to set
	 */
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * @return the deletedOn
	 */
	public Date getDeletedOn() {
		return deletedOn;
	}

	/**
	 * @param deletedOn
	 * the deletedOn to set
	 */
	public void setDeletedOn(Date deletedOn) {
		this.deletedOn = deletedOn;
	}


	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	/**
	 * @return the btcWalletUuid
	 */
	public String getBtcWalletUuid() {
		return btcWalletUuid;
	}

	/**
	 * @param btcWalletUuid 
	 * the btcWalletUuid to set
	 */
	public void setBtcWalletUuid(String btcWalletUuid) {
		this.btcWalletUuid = btcWalletUuid;
	}

	/**
	 * @return the ethWalletaddress
	 */
	public String getEthWalletaddress() {
		return ethWalletaddress;
	}

	/**
	 * @param ethWalletaddress 
	 * the ethWalletaddress to set
	 */
	public void setEthWalletaddress(String ethWalletaddress) {
		this.ethWalletaddress = ethWalletaddress;
	}

	/**
	 * @return the ethWalletPwd
	 */
	public String getEthWalletPwd() {
		return ethWalletPwd;
	}

	/**
	 * @param ethWalletPwd 
	 * the ethWalletPwd to set
	 */
	public void setEthWalletPwd(String ethWalletPwd) {
		this.ethWalletPwd = ethWalletPwd;
	}

	/**
	 * @return the ethWalletPwdKey
	 */
	public String getEthWalletPwdKey() {
		return ethWalletPwdKey;
	}

	/**
	 * @param ethWalletPwdKey 
	 * the ethWalletPwdKey to set
	 */
	public void setEthWalletPwdKey(String ethWalletPwdKey) {
		this.ethWalletPwdKey = ethWalletPwdKey;
	}

	/**
	 * @return the ethWalletJsonFileName
	 */
	public String getEthWalletJsonFileName() {
		return ethWalletJsonFileName;
	}

	/**
	 * @param ethWalletJsonFileName 
	 * the ethWalletJsonFileName to set
	 */
	public void setEthWalletJsonFileName(String ethWalletJsonFileName) {
		this.ethWalletJsonFileName = ethWalletJsonFileName;
	}
	
}
