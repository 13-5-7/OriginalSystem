package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class InfoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	//フィールドの宣言
	private int infoNo;
	private String secName;
	private String candiName;
	private int candiType;
	private String candiStage;
	private String regiDate;
	private int candiResult;
	private String interDate;
	private String interTime;
	private String candiPdf;
	private int newRegi;
	private String messageId;
	private Timestamp dateTime;

	/*
	 * 応募者NO
	 */
	public int getInfoNo() {
		return infoNo;
	}
	public void setInfoNo(int infoNo) {
		this.infoNo = infoNo;
	}
	/*
	 * 希望部署
	 */
	public String getSecName() {
		return secName;
	}
	public void setSecName(String secName) {
		this.secName = secName;
	}
	/*
	 * 応募社名
	 */
	public String getCandiName() {
		return candiName;
	}
	public void setCandiName(String candiName) {
		this.candiName = candiName;
	}
	/*
	 * 応募種類
	 */
	public int getCandiType() {
		return candiType;
	}
	public void setCandiType(int candiType) {
		this.candiType = candiType;
	}
	/*
	 * 選考段階
	 */
	public String getCandiStage() {
		return candiStage;
	}
	public void setCandiStage(String candiStage) {
		this.candiStage = candiStage;
	}
	/*
	 * 登録日付
	 */
	public String getRegiDate() {
		return regiDate;
	}
	public void setRegiDate(String regiDate) {
		this.regiDate = regiDate;
	}
	/*
	 * 結果
	 */
	public int getCandiResult() {
		return candiResult;
	}
	public void setCandiResult(int candiResult) {
		this.candiResult = candiResult;
	}
	/*
	 * 面接日付
	 */
	public String getInterDate() {
		return interDate;
	}
	public void setInterDate(String interDate) {
		this.interDate = interDate;
	}
	/*
	 * 面接時間
	 */
	public String getInterTime() {
		return interTime;
	}
	public void setInterTime(String interTime) {
		this.interTime = interTime;
	}
	/*
	 * 履歴書（PDF）
	 */
	public String getCandiPdf() {
		return candiPdf;
	}
	public void setCandiPdf(String candiPdf) {
		this.candiPdf = candiPdf;
	}
	/*
	 * 新規登録の判定
	 */
	public void setNewRegi(int newRegi) {
		this.newRegi = newRegi;
	}
	public int getNewRegi() {
		return newRegi;
	}

	/*
	 * 最終メッセージ送信者のユーザID
	 */
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
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