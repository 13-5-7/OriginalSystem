package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.InfoDao;
import model.SecBean;

/**
 * Servlet implementation class SectionRegiCon
 */
public class SectionRegiCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SectionRegiCon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// JSPファイルを表示（設備登録面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/SecntionRegi.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードの指定
		request.setCharacterEncoding("UTF-8");
		// パラメータの取得
		String secName = request.getParameter("secName");
		// InfoDaoクラスのインスタンスを生成
		InfoDao dao = new InfoDao();
		// SecBeanクラスのインスタンスを生成
		SecBean secBean = new SecBean();
		// userBeanに値を設定
		secBean.setSecName(secName);
		// 部署情報重複メソッドの呼び出し
		int count = dao.registerSectionCheckDao(secBean);
		// 登録の有無を判断する変数宣言
		int num = 0;

		// 部署名が重複していなければする処理
		if(count == 0) {
			// 部署情報登録メソッドの呼び出し
			num = dao.registerSectionDao(secBean);
		}

		// JSPへ渡す
		request.setAttribute("num", num);

		// JSPファイルを表示（設備登録面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/SecntionRegi.jsp");
		dispatcher.forward(request,response);
	}

}
