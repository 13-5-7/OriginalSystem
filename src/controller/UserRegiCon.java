package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.InfoDao;
import model.SecBean;
import model.UserBean;

/**
 * Servlet implementation class UserRegiCon
 */
public class UserRegiCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegiCon() {
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
		// 部署情報を取得
		ArrayList<SecBean> allSectionList = dao.allSectionSearchDao();

		// JSPへ渡す
		request.setAttribute("allSectionList", allSectionList);

		// JSPファイルを表示（ログイン画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/UserRegi.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードの指定
		request.setCharacterEncoding("UTF-8");

		// InfoDaoクラスのインスタンスを生成
		InfoDao dao = new InfoDao();
		// UserBeanクラスのインスタンスを生成
		UserBean userBean = new UserBean();
		// パラメータの取得
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String secName = request.getParameter("secName");
		String mailaddress= request.getParameter("mailaddress");

		// userBeanに値を設定
		userBean.setUserId(userId);				// ユーザID
		userBean.setPassword(password);			// パスワード
		userBean.setSecName(secName);			// 部署名
		userBean.setMailaddress(mailaddress);	// メールアドレス

		// ユーザID重複メソッドの呼び出し
		int count = dao.registerUserCheckDao(userId);
		// 登録の可否を判断する変数宣言
		int num = 0;

		// 重複の有無で処理を分ける
		if(count == 0) {
			// ユーザ登録メソッドの呼び出し
			num = dao.userRegisterDao(userBean);
		}
		// 部署情報を取得
		ArrayList<SecBean> allSectionList = dao.allSectionSearchDao();

		// JSPへ渡す
		request.setAttribute("allSectionList", allSectionList);
		request.setAttribute("userId", userId);
		request.setAttribute("mailaddress", mailaddress);
		request.setAttribute("secName", secName);
		request.setAttribute("num", num);

		// JSPファイルを表示（ログイン画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/UserRegi.jsp");
		dispatcher.forward(request,response);
	}

}
