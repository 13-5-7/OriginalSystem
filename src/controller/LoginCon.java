package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.InfoDao;
import model.SecBean;
import model.UserBean;

/**
 * Servlet implementation class LoginCon
 */
public class LoginCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginCon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// JSPファイルを表示（ログイン画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/Login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードの指定
		request.setCharacterEncoding("UTF-8");
		// InfoDaoクラスのインスタンス生成
		InfoDao dao = new InfoDao();
		// パラメータの取得
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

		// ログイン確認メソッドの呼び出し
		UserBean loginBean = dao.loginCheck(userId);
		// ログインできなかった場合に渡すメッセージ
		String message = "";

		// ユーザIDが正しくない場合、DBからnullが返ってくる
		if( (loginBean.getUserId() == null) ) {
			// エラーメッセージの設定
			message = "ユーザIDが存在しません。";
			// JSPに渡す
			request.setAttribute("userId",userId);
			request.setAttribute("message",message);

			// JSPファイルを表示（ログイン画面）
			ServletContext app = this.getServletContext();
			RequestDispatcher dispatcher = app.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request,response);

			// ユーザIDが正しい場合、パスワードが一致するか確認する（一致しない場合）
		}else if( (!password.equals(loginBean.getPassword())) ) {
			// エラーメッセージの設定
			message = "パスワードが一致しません。";
			// JSPに渡す
			request.setAttribute("userId",userId);
			request.setAttribute("message",message);

			// JSPファイルを表示（ログイン画面）
			ServletContext app = this.getServletContext();
			RequestDispatcher dispatcher = app.getRequestDispatcher("/Login.jsp");
			dispatcher.forward(request,response);

			// ユーザIDとパスワード両方が正しい場合
		}else if( (userId.equals(loginBean.getUserId())) &&  (password.equals(loginBean.getPassword())) ) {
			// セッションの取得
			HttpSession session = request.getSession(false);

			// 既にセッションが存在する場合は一度破棄する
			if (session != null) {
				session.invalidate();		// セッションを破棄
			}

			try {
				// セッションを新規で作成する
				session = request.getSession(true);

				// セッションにuserBeanを登録（userId,userName,userPassword,userPrivilege,firstLogin,secId含む）
				session.setAttribute("userBean", loginBean);

			} catch (Exception e) {
				// 例外によりセッションの作成に失敗
				e.printStackTrace();
			}

			// 人事部がログインした場合（ユーザ登録画面へ遷移）
			if(loginBean.getSecName().equals("人事部")) {
				// 部署情報を取得
				ArrayList<SecBean> allSectionList = dao.allSectionSearchDao();

				// JSPへ渡す
				request.setAttribute("allSectionList", allSectionList);

				// JSPファイルを表示（ログイン画面）
				ServletContext app = this.getServletContext();
				RequestDispatcher dispatcher = app.getRequestDispatcher("/UserRegi.jsp");
				dispatcher.forward(request,response);

				// その他の部署の人がログインした場合（応募者一覧へ遷移）
			}else{
				// CandidateListConへリダイレクト
				response.sendRedirect("CandidateListCon");
			}
		}
	}

}
