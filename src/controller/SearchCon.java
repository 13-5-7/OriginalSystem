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
 * Servlet implementation class SearchCon
 */
public class SearchCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchCon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		String searchSec = request.getParameter("searchSec");
		String searchName = request.getParameter("searchName");

		// 応募者検索メソッドの呼び出し（応募者リスト下部）
		ArrayList<InfoBean> searchList = dao.selectSearchDao(searchSec,searchName);
		// セッションを取得する
		HttpSession session = request.getSession(true);
		// セッションに保持させている値の取得
		UserBean userBean = (UserBean)session.getAttribute("userBean");
		// 部署情報検索メソッド（一覧表示画面）の呼び出し
		ArrayList<SecBean> allSectionList = dao.allSectionSearchDao();

		// JSPへ渡す
		request.setAttribute("userBean", userBean);
		request.setAttribute("allCandidateList", searchList);
		request.setAttribute("allSectionList", allSectionList);
		request.setAttribute("searchSec", searchSec);

		// JSPファイルを表示（応募者一覧画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/CandidateList.jsp");
		dispatcher.forward(request,response);

	}

}
