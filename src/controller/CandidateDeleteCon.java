package controller;

import java.io.File;
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
import util.FileUploadOriginal;

/**
 * Servlet implementation class CandidateDeleteCon
 */
public class CandidateDeleteCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CandidateDeleteCon() {
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
		// FileUploadOriginalクラスのインスタンスを生成
		FileUploadOriginal file = new FileUploadOriginal();
		// パラメータの取得
		int infoNo = Integer.valueOf(request.getParameter("infoNo"));

		// ファイルをアップロードするパスの取得
		String path = getServletContext().getRealPath("files");
		// 応募者の応募書類検索メソッドの呼び出し（DBに登録されている基のファイル名を取得）
		String oldImageName = dao.candiImageDao(infoNo);
		// DBに登録されている基のファイル名をfileImageに設定
		File fileImage = new File(path + "\\" + oldImageName);

		if(fileImage.exists()) {
			fileImage.delete();			//基のファイルの存在を確認し、あれば削除
		}

		// 応募者削除メソッドの呼び出し
		int num = dao.candiDeleteDao(infoNo);
		// メッセージ削除メソッドの呼び出し
		int count = dao.messageDeleteDao(infoNo);
		// 応募者削除、メッセージ削除の合計件数
		int total = num + count;

		// セッションを取得する
		HttpSession session = request.getSession(true);
		// セッションに保持させている値の取得
		UserBean userBean = (UserBean)session.getAttribute("userBean");
		// 応募者情報検索メソッド（一覧表示画面）の呼び出し
		ArrayList<InfoBean> allCandidateList = dao.allCandidateSearchDao();
		// 部署情報検索メソッド（一覧表示画面）の呼び出し
		ArrayList<SecBean> allSectionList = dao.allSectionSearchDao();

		// JSPへ渡す
		request.setAttribute("allCandidateList", allCandidateList);
		request.setAttribute("allSectionList", allSectionList);
		request.setAttribute("userBean", userBean);
		request.setAttribute("resultDelete", total);

		// JSPファイルを表示（応募者一覧画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/CandidateList.jsp");
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
