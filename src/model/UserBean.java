package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserBean implements Serializable{

	private static final long serialVersionUID = 1L;

	//フィールドの宣言
	private int userNo;
	private String userId;
	private String password;
	private String secName;
	private String mailaddress;
	private Timestamp dateTime;
	/*
	 * ユーザNO
	 */
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	/*
	 * ユーザID
	 */
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/*
	 * パスワード
	 */
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/*
	 * 所属部署
	 */
	public String getSecName() {
		return secName;
	}
	public void setSecName(String secName) {
		this.secName = secName;
	}
	/*
	 * メールアドレス
	 */
	public String getMailaddress() {
		return mailaddress;
	}
	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}
	/*
	 * 更新日時
	 */
	public Timestamp getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}
}