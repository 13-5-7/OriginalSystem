package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.InfoBean;
import model.InfoDao;
import model.SecBean;
import util.FileUploadOriginal;
import util.Mail;

/**
 * Servlet implementation class CandidateRegiCon
 */
public class CandidateRegiCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CandidateRegiCon() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードの指定
		request.setCharacterEncoding("UTF-8");

		//日付クラスの書式を設定したSimpleDateFormatクラスのインスタンスを作成する
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();

		// InfoDaoクラスのインスタンスを生成
		InfoDao dao = new InfoDao();
		// 部署情報検索メソッド（一覧表示画面）の呼び出し
		ArrayList<SecBean> allSectionList = dao.allSectionSearchDao();

		// JSPへ渡す
		request.setAttribute("allSectionList", allSectionList);
		request.setAttribute("today",sdf.format(now));

		// JSPファイルを表示（応募者登録画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/CandidateRegi.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードの指定
		request.setCharacterEncoding("UTF-8");

		// InfoDaoのインスタンスを生成
		InfoDao dao = new InfoDao();
		// InfoBeanクラスのインスタンスを生成
		InfoBean infoBean = new InfoBean();
		// FileUploadOriginalクラスのインスタンスを生成
		FileUploadOriginal file = new FileUploadOriginal();

		// ファイルをアップロードするパスの取得
		String path = getServletContext().getRealPath("files");
		// ファイルのアップロードメソッドの呼び出し（その他のデータはInfoBeanに格納）
		infoBean = file.FileUp(request,path);
		// ファイル名をfileName変数へ代入
		String fileName = infoBean.getCandiPdf();
		// 拡張子チェック
		boolean extension = false;
		// ファイル名が存在するか確認
		if(fileName != null) {
			extension = true;		// 存在するので拡張子チェックOK
		}else {
			extension = false;		// 存在しないので拡張子チェックNG
		}

		// 拡張子が対応しているかどうか
		if(extension == false) {
			// JSPへ渡す
			request.setAttribute("extension", extension);

		}else {
			// 応募者名重複チェックメソッドの呼び出し・結果の取得
			//int count = dao.registerCheckDao(infoBean);
			// 同姓同名対策
			int count = 0;
			// 応募者登録の有無を設定する変数
			int num = 0;

			//取得した重複件数によって処理分け
			if( count == 0 ) {
				// 応募者登録メソッドの呼び出し・結果の取得
				num = dao.registerDao(infoBean);
				// メールアドレス取得メソッドの呼び出し
				ArrayList<String> mailaddress = dao.getMailaddressDao(infoBean.getSecName());
				// Mailクラスのインスタンスを生成
				Mail mail = new Mail();
				// 応募者の希望部署に所属するユーザに対するメール送信（複数対応可）
				for(int i = 0; i < mailaddress.size(); i++) {
					mail.sendMail(mailaddress.get(i));
				}
			}

			// JSPへ渡す
			request.setAttribute("result", num);
		}
		// 部署情報検索メソッド（一覧表示画面）の呼び出し
		ArrayList<SecBean> allSectionList = dao.allSectionSearchDao();

		// JSPへ渡す
		request.setAttribute("allSectionList", allSectionList);
		request.setAttribute("infoBean", infoBean);
		request.setAttribute("secName", infoBean.getSecName());

		// JSPファイルを表示（応募者登録画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/CandidateRegi.jsp");
		dispatcher.forward(request,response);
	}

}
