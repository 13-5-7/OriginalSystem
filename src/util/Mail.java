package util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	/**
	 * 応募者登録お知らせメール送信
	 *
	 * @param userBean  UserBeanクラスオブジェクト
	 * @throws MessagingException,UnsupportedEncodingException
	 */
	public void sendMail(String mailaddress) {
		// SMTP認証
		Properties props = new Properties();
		// GoogleのSMTPサーバーを設定
		props.setProperty("mail.smtp.host","smtp.gmail.com");
		// SSL用にポート番号を変更
		props.setProperty("mail.smtp.port", "465");
		// タイムアウト設定
		props.setProperty("mail.smtp.connectiontimeout", "60000");
		props.setProperty("mail.smtp.timeout", "60000");

		// 認証
		props.setProperty("mail.smtp.auth", "true");
		// SSLを使用するとこはこの設定が必要
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.socketFactory.port","465");

		// sessionの作成
		final Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("momonobu1048dong@gmail.com", "ixqublpbgauhdvvc");
			}
		});
		// メール本文に記入するパスワードをuserPassword変数に設定
		String loginUrl = "http://localhost:8080/OriginalSystem/LoginCon.jsp";

		MimeMessage contentMessage = new MimeMessage(session);
		// メール内容
		String mailContents = "<html><body>"
				+ "<p>応募者新規登録が済みました。</p>"
				+ "<p>以下のURLからログインしてご確認ください。</p>"
				+ "<a href='" + loginUrl + "'>ログインはこちら</a>"
				+ "</body></html>";
		try {
			// 送信元の設定
			Address addFrom = new InternetAddress("momonobu1048dong@gmail.com", "応募者新規登録", "UTF-8");
			contentMessage.setFrom(addFrom);
			// 送信先の設定
			Address addTo = new InternetAddress(mailaddress, "ご担当者様", "UTF-8");
			contentMessage.addRecipient(Message.RecipientType.TO, addTo);
			// 件名の設定
			contentMessage.setSubject("応募者新規登録が済みました。");
			// メールのコンテンツタイプを設定
			contentMessage.setHeader("Content-Type", "text/html; charset=UTF-8");
			// メールのコンテンツタイプを設定
			contentMessage.setContent(mailContents, "text/html; charset=UTF-8");
			// 日付の設定
			contentMessage.setSentDate(new Date());
		}catch(MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// メール送信
		try {
			Transport.send(contentMessage);
			System.out.println("送信完了");
		}catch(AuthenticationFailedException e) {
			System.out.println("認証失敗");
		}catch(MessagingException e) {
			System.out.println("サーバ接続失敗");
		}
	}
}