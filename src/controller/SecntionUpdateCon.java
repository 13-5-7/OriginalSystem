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

/**
 * Servlet implementation class SecntionUpdateCon
 */
public class SecntionUpdateCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SecntionUpdateCon() {
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
		int secNo = Integer.valueOf(request.getParameter("secNo"));
		// 部署名検索メソッドの呼び出し
		SecBean secBean = dao.secSearchDao(secNo);

		// JSPへ渡す
		request.setAttribute("infoBean", secBean);

		// JSPファイルを表示（部署更新画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/SecntionUpdate.jsp");
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
		int secNo = Integer.valueOf(request.getParameter("secNo"));
		String secName = request.getParameter("secName");
		String dateTime = request.getParameter("dateTime");

		// SecBeanクラスのインスタンスを生成
		SecBean secBean = new SecBean();
		// secBeanに値を設定
		secBean.setSecNo(secNo);											// 部署NO
		secBean.setSecName(secName);										// 部署名
		secBean.setDateTime(new Timestamp(System.currentTimeMillis())); 	// 現在日時取得

		// 更新リンクを押下した時の時間
		Timestamp beforeTime = Timestamp.valueOf(dateTime);
		// 部署名重複確認メソッド
		int countSec = dao.registerSectionCheckDao(secBean);

		// 部署更新の有無を設定する変数
		int num = 0;

		// 部署名重複の有無で処理をわける
		if(countSec == 0) {
			// もう一度DBから部署情報を取ってきて現在の値と比較する
			// 部署情報検索メソッドの呼び出し・結果をafterSecBeanにつめて取得
			SecBean afterSecBean = dao.secSearchDao(secNo);
			// もし時間が同じであれば更新成功
			if(beforeTime.equals(afterSecBean.getDateTime())) {
				// InfoDaoクラスを新規作成
				dao = new InfoDao();
				// ユーザ更新メソッドの呼び出し
				num = dao.updateSectionInfoDao(secBean);
			}else {
				num = 2;
			}
			// 削除されていないかチェックする為に部署情報を取得
			SecBean lastCheck = dao.secSearchDao(secNo);
			// ユーザが削除されていないかチェック
			if(lastCheck.getSecName() == null) {
				//削除済なら部署名nullが返ってくるのでnumを3に設定
				num = 3;
			}

		}
		// 部署情報を取得
		ArrayList<SecBean> allSectionList = dao.allSectionSearchDao();
		// 部署の削除の可否を判断する変数宣言
		ArrayList<Integer> secInfoList = new ArrayList<>();

		// 外部キー制約で削除できない部署名があるか確認する
		for(SecBean sb : allSectionList) {
			String secNameCheck = sb.getSecName();
			// 該当部署名があるかどうか確認メソッドの呼び出し
			int count = dao.countSectionDao(secNameCheck);
			// secInfoListへ該当する件数を追加
			secInfoList.add(count);
		}

		// JSPに渡す
		request.setAttribute("allSectionList", allSectionList);
		request.setAttribute("result", num);
		request.setAttribute("secInfoList",secInfoList);

		// JSPファイルを表示（部署一覧画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/UserList.jsp");
		dispatcher.forward(request, response);
	}

}
