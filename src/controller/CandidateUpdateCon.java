package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
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
 * Servlet implementation class CandidateUpdateCon
 */
public class CandidateUpdateCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CandidateUpdateCon() {
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
		// InfoBeanクラスのインスタンスを生成
		InfoBean infoBean = new InfoBean();

		// パラメータの取得
		int infoNo = Integer.valueOf(request.getParameter("infoNo"));
		// 応募者検索メソッド（応募者NOから応募者を検索）の呼び出し
		infoBean = dao.candiSearchDao(infoNo);
		// 部署情報検索メソッド（一覧表示画面）の呼び出し
		ArrayList<SecBean> allSectionList = dao.allSectionSearchDao();

		// 新規登録変更メソッドの呼び出し
		dao.newRegiUpdateDao(infoNo, 2);

		// JSPへ渡す
		request.setAttribute("infoBean", infoBean);
		request.setAttribute("allSectionList", allSectionList);

		// JSPファイルを表示（応募者更新画面）
		ServletContext app = this.getServletContext();
		RequestDispatcher dispatcher = app.getRequestDispatcher("/CandidateUpdate.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コードの指定
		request.setCharacterEncoding("UTF-8");

		// InfoBeanクラスのインスタンスを生成
		InfoBean updateBean = new InfoBean();
		// InfoDaoクラスのインスタンスを生成
		InfoDao dao = new InfoDao();
		// FileUploadOriginalクラスのインスタンスを生成
		FileUploadOriginal file = new FileUploadOriginal();

		try {
			// ファイルをアップロードするパスの取得
			String path = getServletContext().getRealPath("files");
			// ファイルのアップロードメソッドの呼び出し（その他のデータはInfoBeanに格納）
			updateBean = file.FileUpUpdate(request,path);
			// 応募者の応募書類検索メソッドの呼び出し（DBに登録されている基のファイル名を取得）
			String oldImageName = dao.candiImageDao(updateBean.getInfoNo());
			// 新しくDBに登録しようとしている応募書類
			String newImageName = updateBean.getCandiPdf();
			// DBに登録されている基のファイル名をfileImageに設定
			File fileImage = new File(path + "\\" + oldImageName);

			// 拡張子チェック
			boolean extension = false;
			// ファイル名が存在するか確認
			if(newImageName.equals("")) {
				extension = true;		// 存在するので拡張子チェックOK
			}else {
				extension = false;		// 存在しないので拡張子チェックNG
			}

			// 拡張子が対応しているかどうか、新し応募書類のファイル名が取得できるか
			if( (extension == true) | (newImageName == null) ){
				// 更新画面で画像の設定がない場合の処理
				if(newImageName.equals("")) {
					updateBean.setCandiPdf(oldImageName);  	//基のファイル名に上書き

					//新しい画像を登録した場合の処理
				}else if(fileImage.exists()) {
					fileImage.delete();						//基のファイルの存在を確認し、あれば削除
				}

				// 応募者名重複チェックメソッドの呼び出し・結果の取得
				//int count = dao.registerCheckDao(updateBean);
				int count = 0;  // 同姓同名対策（一時的に応募者名重複メソッドを省く）
				// 応募者登録の有無を設定する変数
				int num = 0;

				// 更新リンクを押下した時の時間を変数beforeTimeに設定
				Timestamp beforeTime = updateBean.getDateTime();
				// updateBean内の時間を現在の時刻に上書き
				updateBean.setDateTime(new Timestamp(System.currentTimeMillis()));

				// 取得した重複件数によって処理分け
				if( count == 0 ) {
					// もう一度DBから応募者情報を取ってきて現在の値と比較する
					// 応募者情報検索メソッドの呼び出し・結果をItemBeanにつめて取得
					InfoBean afterInfoBean = dao.candiSearchDao(updateBean.getInfoNo());
					System.out.println(beforeTime);
					System.out.println(afterInfoBean.getDateTime());
					// もし時間が同じであれば更新成功
					if(beforeTime.equals(afterInfoBean.getDateTime())) {
						dao = new InfoDao();
						num = dao.candiUpdateDao(updateBean);	// 応募者情報更新メソッドの呼び出し
					}else {
						num = 2;								// 時間が違う場合はnumを2に設定
					}

					// 削除されていないかチェックする為に応募者情報を取得
					InfoBean lastCheck = dao.candiSearchDao(updateBean.getInfoNo());
					// 応募者情報がが削除されていないかチェック
					if(lastCheck.getCandiName() == null) {
						num = 3;	//削除済なら商品名nullが返ってくるのでnumを3に設定
					}
				}

				// JSPへ渡す
				request.setAttribute("result", num);

				// 拡張子が対応していない場合
			}else {
				// JSPへ渡す
				request.setAttribute("extension", extension);
			}

			// 全応募者検索メソッドの呼び出し
			dao = new InfoDao();
			// 応募者情報検索メソッド（一覧表示画面）の呼び出し
			ArrayList<InfoBean> allCandidateList = dao.allCandidateSearchDao();
			// セッションを取得する
			HttpSession session = request.getSession(true);
			// セッションに保持させている値の取得
			UserBean userBean = (UserBean)session.getAttribute("userBean");
			// 部署情報検索メソッド（一覧表示画面）の呼び出し
			ArrayList<SecBean> allSectionList = dao.allSectionSearchDao();

			// 新規登録変更メソッドの呼び出し
			dao.newRegiUpdateDao(updateBean.getInfoNo(), 2);

			// JSPへ渡す
			request.setAttribute("allCandidateList", allCandidateList);
			request.setAttribute("userBean", userBean);
			request.setAttribute("allSectionList", allSectionList);

			// JSPファイルを表示（応募者一覧画面）
			ServletContext app = this.getServletContext();
			RequestDispatcher dispatcher = app.getRequestDispatcher("/CandidateList.jsp");
			dispatcher.forward(request,response);


		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}

	}

}
