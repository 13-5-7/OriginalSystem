package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class SecBean implements Serializable{

	private static final long serialVersionUID = 1L;

	//フィールドの宣言
	private int secNo;
	private String secName;
	private Timestamp dateTime;

	/*
	 * 部署NO
	 */
	public int getSecNo() {
		return secNo;
	}
	public void setSecNo(int secNo) {
		this.secNo = secNo;
	}
	/*
	 * 部署名
	 */
	public String getSecName() {
		return secName;
	}
	public void setSecName(String secName) {
		this.secName = secName;
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