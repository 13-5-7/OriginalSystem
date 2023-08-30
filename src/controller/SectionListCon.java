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

/**
 * Servlet implementation class SectionListCon
 */
public class SectionListCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SectionListCon() {
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
		// 部署の削除の可否を判断する変数宣言
		ArrayList<Integer> secInfoList = new ArrayList<>();

		// 外部キー制約で削除できない部署名があるか確認する
		for(SecBean sb : allSectionList) {
			String secName = sb.getSecName();
			// 該当部署名があるかどうか確認メソッドの呼び出し
			int count = dao.countSectionDao(secName);
			// secInfoListへ該当する件数を追加
			secInfoList.add(count);
		}

		// JSPへ渡す
		request.setAttribute("allSectionList", allSectionList);
		request.setAttribute("secInfoList", secInfoList);

		// JSPファイルを表示（部署一覧画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/SecntionList.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
