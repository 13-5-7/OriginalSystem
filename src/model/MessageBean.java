package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class MessageBean implements Serializable{

	private static final long serialVersionUID = 1L;

	//フィールドの宣言
	private int roomNo;
	private String userId;
	private String Message;
	private Timestamp dateTime;

	/*
	 * ルームNO
	 */
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
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
	 * メッセージ
	 */
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
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
