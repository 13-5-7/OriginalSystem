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
 * Servlet implementation class UserListCon
 */
public class UserListCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserListCon() {
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
		// 部署一覧を取得
		ArrayList<SecBean> allSectionList = dao.allSectionSearchDao();
		// ユーザ一覧を取得
		ArrayList<UserBean> allUserList = dao.allUserSearchDao();
		// 応募者の希望部署と結びついている部署名を調べる
		ArrayList<Integer> deleteSecName = new ArrayList<>();
		// 応募者の希望部署と紐づいている件数を調べる
		for(int i = 0; i < allUserList.size(); i++) {
			UserBean userCheckBean = allUserList.get(i);
			int count = dao.secNameCheckDao(userCheckBean.getSecName());
			deleteSecName.add(count);		// 紐づいている件数をリストに追加
		}
		// JSPに渡す
		request.setAttribute("allSectionList", allSectionList);
		request.setAttribute("allUserList", allUserList);
		request.setAttribute("count", deleteSecName);

		// JSPファイルを表示（ログイン画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/UserList.jsp");
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
