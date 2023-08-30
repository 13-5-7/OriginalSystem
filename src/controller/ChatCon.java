package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.InfoBean;
import model.InfoDao;
import model.MessageBean;
import model.UserBean;

/**
 * Servlet implementation class ChatCon
 */
public class ChatCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatCon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードの指定
		request.setCharacterEncoding("UTF-8");

		// InfoDaoクラスのインスタンスを生成
		InfoDao dao = new InfoDao();

		// パラメータの取得
		int infoNo = Integer.valueOf(request.getParameter("infoNo"));

		// セッションを取得する
		HttpSession session = request.getSession(true);
		// セッションに保持している値を取得
		UserBean userBean = (UserBean)session.getAttribute("userBean");
		// セッションで保持しているユーザIDを変数に代入
		String userId = userBean.getUserId();
		// メッセージ検索メソッド（一覧表示画面）の呼び出し
		ArrayList<MessageBean> messageList = dao.messageSearchDao(infoNo);
		// 応募者検索メソッド（応募者NOから応募者を検索）の呼び出し
		InfoBean infoBean = dao.candiSearchDao(infoNo);
		// 応募者名を変数に代入
		String candiName = infoBean.getCandiName();

		// 新規登録変更メソッドの呼び出し
		dao.newRegiUpdateDao(infoNo, 2);
		// ラストメッセージ送信者メソッドの呼び出し
		//dao.lastMessageUserIdDao(infoNo,infoBean.getUserId());

		// JSPへ渡す
		request.setAttribute("messageList", messageList);
		request.setAttribute("infoNo", infoNo);
		request.setAttribute("userIdSession", userId);
		request.setAttribute("candiName", candiName);

		// JSPファイルを表示（チャットroom画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/Chat.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードの指定
		request.setCharacterEncoding("UTF-8");

		// InfoBeanクラスのインスタンスを生成
		MessageBean messageBean = new MessageBean();
		// InfoDaoクラスのインスタンスを生成
		InfoDao dao = new InfoDao();

		// パラメータの取得
		int infoNo = Integer.valueOf(request.getParameter("infoNo"));
		String message = request.getParameter("message");
		String userId = request.getParameter("userId");

		// infoBeanへ値を設定
		messageBean.setRoomNo(infoNo);											// チャットルームNO（応募者NO）
		messageBean.setMessage(message);										// メッセージ
		messageBean.setUserId(userId);											// ユーザID
		messageBean.setDateTime(new Timestamp(System.currentTimeMillis())); 	// 現在日時取得

		// メッセージ登録メソッドの呼び出し
		int num = dao.messageRegiDao(messageBean);
		// ラストメッセージ送信者メソッドの呼び出し
		dao.lastMessageUserIdDao(infoNo,userId);
		// メッセージ検索メソッド（一覧表示画面）の呼び出し
		ArrayList<MessageBean> messageList = dao.messageSearchDao(infoNo);
		// セッションを取得する
		HttpSession session = request.getSession(true);
		// セッションに保持させている値の取得
		UserBean userBean = (UserBean)session.getAttribute("userBean");
		// セッションに保持させているuserIdの取得（ログイン中のuserId）
		String userIdSession = userBean.getUserId();
		// 応募者検索メソッド（応募者NOから応募者を検索）の呼び出し
		InfoBean candiBean = dao.candiSearchDao(infoNo);
		// 応募者名を変数に代入
		String candiName = candiBean.getCandiName();

		// JSPへ渡す
		request.setAttribute("messageList", messageList);
		request.setAttribute("infoNo", infoNo);
		request.setAttribute("userId", userId);
		request.setAttribute("userIdSession", userIdSession);
		request.setAttribute("candiName", candiName);

		// JSPファイルを表示（チャットroom画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/Chat.jsp");
		dispatcher.forward(request,response);
	}

}
