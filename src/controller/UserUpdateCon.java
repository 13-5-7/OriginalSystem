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

import model.InfoDao;
import model.SecBean;
import model.UserBean;

/**
 * Servlet implementation class UserUpdateCon
 */
public class UserUpdateCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserUpdateCon() {
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
		int userNo = Integer.valueOf(request.getParameter("userNo"));
		// ユーザ情報検索メソッドの呼び出し
		UserBean userBean = dao.userSearchDao(userNo);
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
		request.setAttribute("infoBean", userBean);

		// JSPファイルを表示（部署一覧画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/UserUpdate.jsp");
		dispatcher.forward(request, response);
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
		int userNo = Integer.valueOf(request.getParameter("userNo"));
		String curUserId = request.getParameter("curUserId");
		String userId = request.getParameter("userId");
		String password = request.getParameter("passoword");
		String secName = request.getParameter("secName");
		String dateTime = request.getParameter("dateTime");

		// UserBeanクラスのインスタンスを生成
		UserBean userBean = new UserBean();
		// userBeanに値を設定
		userBean.setUserNo(userNo);											// ユーザNO
		userBean.setUserId(userId);											// ユーザID
		userBean.setPassword(password);										// パスワード
		userBean.setSecName(secName);										// 部署名
		userBean.setDateTime(new Timestamp(System.currentTimeMillis())); 	// 現在日時取得

		// 更新リンクを押下した時の時間
		Timestamp beforeTime = Timestamp.valueOf(dateTime);
		// ユーザID重複確認メソッド
		int countUser = 0;

		// ユーザIDを変更する場合の処理
		if(!curUserId.equals(userId)) {
			countUser = dao.registerUserCheckDao(userId);		// ユーザID重複メソッドの呼び出し
		}

		// ユーザID更新の有無を設定する変数
		int num = 0;

		// ユーザID重複の有無で処理をわける
		if(countUser == 0) {
			// もう一度DBからユーザ情報を取ってきて現在の値と比較する
			// ユーザ情報検索メソッドの呼び出し・結果をafterSecBeanにつめて取得
			UserBean afterUserBean = dao.userSearchDao(userNo);
			// もし時間が同じであれば更新成功
			if(beforeTime.equals(afterUserBean.getDateTime())) {
				// InfoDaoクラスを新規作成
				dao = new InfoDao();
				// ユーザ更新メソッドの呼び出し
				num = dao.updateUserInfoDao(userBean);
			}else {
				num = 2;
			}
			// 削除されていないかチェックする為にユーザ情報を取得
			UserBean lastCheck = dao.userSearchDao(userNo);
			// ユーザが削除されていないかチェック
			if(lastCheck.getUserId() == null) {
				//削除済ならユーザIDnullが返ってくるのでnumを3に設定
				num = 3;
			}

		}
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
		// 部署一覧を取得
		ArrayList<SecBean> allSectionList = dao.allSectionSearchDao();

		// JSPに渡す
		request.setAttribute("allSectionList", allSectionList);
		request.setAttribute("result", num);
		request.setAttribute("infoBean", userBean);
		request.setAttribute("allUserList",allUserList);
		request.setAttribute("count", deleteSecName);

		// JSPファイルを表示（部署一覧画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/UserList.jsp");
		dispatcher.forward(request, response);

	}

}
