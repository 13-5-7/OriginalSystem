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

import model.InfoBean;
import model.InfoDao;
import model.SecBean;
import model.UserBean;

/**
 * Servlet implementation class CandidateListCon
 */
public class CandidateListCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CandidateListCon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードの指定
		request.setCharacterEncoding("UTF-8");

		// セッションを取得する
		HttpSession session = request.getSession(true);
		// セッションに保持させている値の取得
		UserBean userBean = (UserBean)session.getAttribute("userBean");
		// InfoDaoクラスのインスタンスを生成
		InfoDao dao = new InfoDao();
		// 応募者情報検索メソッド（一覧表示画面）の呼び出し
		ArrayList<InfoBean> allCandidateList = dao.allCandidateSearchDao();
		// 部署情報検索メソッド（一覧表示画面）の呼び出し
		ArrayList<SecBean> allSectionList = dao.allSectionSearchDao();

		// JSPへ渡す
		request.setAttribute("allCandidateList", allCandidateList);
		request.setAttribute("allSectionList", allSectionList);
		request.setAttribute("userBean", userBean);

		// JSPファイルを表示（応募者一覧画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/CandidateList.jsp");
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
		// パラメータの取得
		int infoNo = Integer.valueOf(request.getParameter("infoNo"));
		// ファイルをアップロードするパスの取得
		String path = getServletContext().getRealPath("files");
		// 履歴書閲覧用のファイル名検索メソッドの呼び出し
		String fileName = dao.fileSearchDao(infoNo);
		// 新規登録変更メソッドの呼び出し
		dao.newRegiUpdateDao(infoNo, 2);

		// JSPへ渡す
		request.setAttribute("fileName", fileName);
		request.setAttribute("path", path);

		// JSPファイルを表示（履歴書閲覧画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/CandidateView.jsp");
		dispatcher.forward(request,response);
	}

}
