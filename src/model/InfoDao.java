package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DAOクラス
 * DBアクセス処理を書き込むメソッド
 */
public class InfoDao {
	//メンバ変数の用意
	Connection conn = null;
	PreparedStatement stmt = null;

	/**
	 * 応募者情報登録メソッド
	 * DBに応募者情報を登録
	 *
	 * @param infoBean Bean型に商品情報
	 * @return num INSERTした結果件数
	 * @throws SQLException
	 */
	public int registerDao(InfoBean infoBean) {
		//returnする変数宣言
		int num = 0;
		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//自動コミットを無効にする
			conn.setAutoCommit(false);

			//商品登録画面で登録された内容をDBのitem_tableに追加する（プレースホルダ）
			String sql = "INSERT INTO CANDI_INFO (INFO_NO, SEC_NAME, CANDI_NAME, CANDI_TYPE, CANDI_PDF, CANDI_STAGE, REGI_DATE) "
					+ "VALUES(INFO_NO.nextval, ?, ?, ?, ?, ?, ?)";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメーターセット
			stmt.setString(1, infoBean.getSecName());
			stmt.setString(2, infoBean.getCandiName());
			stmt.setInt(3, infoBean.getCandiType());
			stmt.setString(4, infoBean.getCandiPdf());
			stmt.setString(5, infoBean.getCandiStage());
			stmt.setString(6, infoBean.getRegiDate());
			//SQLの実行
			num = stmt.executeUpdate();

			//ステートメントをクローズ
			stmt.close();
			//コミット
			conn.commit();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			num = 10;
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					//ロールバックしてからクローズ
					conn.rollback();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//試行結果のreturn
		return num;
	}

	/**
	 * 応募者情報重複チェックメソッド（重複チェック処理）
	 * DBに応募者情報を登録する前に応募者名が重複していないかを確認します
	 *
	 * @param infoBean Bean型の商品情報
	 * @return num 重複している商品名をSELECTした結果件数
	 * @throws SQLException
	 */
	public int registerCheckDao(InfoBean infoBean) {
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;
		//return用変数
		int num = 0;
		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成
			String sql = "SELECT COUNT(*) FROM CANDI_INFO WHERE CANDI_NAME = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメーターセット
			stmt.setString(1, infoBean.getCandiName());
			//SQLの実行
			rs = stmt.executeQuery();

			//結果を取得
			rs.next();
			num = rs.getInt(1);
			//リザルトセットをクローズ
			rs.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//試行結果をreturn（返ってきた件数）
		return num;
	}

	/**
	 * 応募者情報検索メソッド（一覧表示画面）
	 * DBに登録されている応募者情報を全て表示します
	 *
	 * @return allList DBに登録されている応募者情報を全て格納し渡す
	 * @throws SQLException
	 */
	public ArrayList<InfoBean> allCandidateSearchDao() {
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;
		//検索した各商品を格納したItemBeanクラスのオブジェクトを格納する
		ArrayList<InfoBean> allCandidateList = new ArrayList<>();

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成
			String sql = "SELECT * FROM CANDI_INFO ORDER BY INFO_NO ASC";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//SQLの実行
			rs = stmt.executeQuery();

			//結果を取得
			while (rs.next()) {
				InfoBean infoBean = new InfoBean();
				infoBean.setInfoNo(rs.getInt("INFO_NO"));
				infoBean.setSecName(rs.getString("SEC_NAME"));
				infoBean.setCandiName(rs.getString("CANDI_NAME"));
				infoBean.setCandiType(rs.getInt("CANDI_TYPE"));
				infoBean.setCandiPdf(rs.getString("CANDI_PDF"));
				infoBean.setCandiStage(rs.getString("CANDI_STAGE"));
				infoBean.setRegiDate(rs.getString("REGI_DATE"));
				infoBean.setCandiResult(rs.getInt("CANDI_RESULT"));
				infoBean.setInterDate(rs.getString("INTER_DATE"));
				infoBean.setInterTime(rs.getString("INTER_TIME"));
				infoBean.setDateTime(rs.getTimestamp("UPDATE_TIME"));
				infoBean.setNewRegi(rs.getInt("NEW_REGI"));
				infoBean.setMessageId(rs.getString("MESSAGE_ID"));
				allCandidateList.add(infoBean);
			}

			//リザルトセットをクローズ
			rs.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//オブジェクトを格納したリストを返す
		return allCandidateList;
	}

	/**
	 * 履歴書閲覧用のファイル名検索メソッド
	 * 応募者情報のNOを基に応募者情報を検索し添付履歴書のファイル名を渡す
	 *
	 * @param infoNo int型の応募者登録NO
	 * @return fileName 該当するファイル名を渡す
	 * @throws SQLException
	 */
	public String fileSearchDao(int infoNo) {
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;
		//検索したファイル名を代入する変数宣言
		String fileName = "";

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成（全商品を選択する）
			String sql = "SELECT CANDI_PDF FROM CANDI_INFO WHERE INFO_NO = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメーターセット
			stmt.setInt(1, infoNo);
			//SQLの実行
			rs = stmt.executeQuery();

			//結果を取得
			rs.next();
			fileName = rs.getString(1);
			//リザルトセットをクローズ
			rs.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//ファイル名を代入した変数を返す
		return fileName;
	}

	/**
	 * ユーザ登録メソッド
	 * 受け取ったユーザID、パスワード、部署名を登録する
	 *
	 * @param userId,password,secNam String型 ユーザID、パスワード、部署名
	 * @return num 登録が済んだ件数を返す
	 * @throws SQLException, ClassNotFoundException, SQLIntegrityConstraintViolationException
	 */
	public int userRegisterDao(UserBean userBean) {
		//登録できた件数を代入する変数宣言
		int num = 0;

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成（更新するITEM_TABLEを選択し更新箇所を設定）
			String sql = "INSERT INTO USER_INFO(USER_NO, USER_ID, PASSWORD, SEC_NAME, MAILADDRESS) VALUES(USER_NO.nextval, ?, ?, ?, ?)";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメータセット
			stmt.setString(1, userBean.getUserId());
			stmt.setString(2, userBean.getPassword());
			stmt.setString(3, userBean.getSecName());
			stmt.setString(4, userBean.getMailaddress());
			//SQLの実行
			num = stmt.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			num = 10;
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					//ロールバックしてからクローズ
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//ユーザ登録ができた件数を渡す
		return num;

	}

	/**
	 * ユーザID重複メソッド（重複チェック処理）
	 * DBにユーザ情報を登録する前にユーザ名が重複していないかを確認します
	 *
	 * @param infoBean Bean型の商品情報
	 * @return num 重複している商品名をSELECTした結果件数
	 * @throws SQLException
	 */
	public int registerUserCheckDao(String userId) {
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;
		//return用変数
		int num = 0;
		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成
			String sql = "SELECT COUNT(*) FROM USER_INFO WHERE USER_ID = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメーターセット
			stmt.setString(1, userId);
			//SQLの実行
			rs = stmt.executeQuery();

			//結果を取得
			rs.next();
			num = rs.getInt(1);
			//リザルトセットをクローズ
			rs.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//試行結果をreturn（返ってきた件数）
		return num;
	}

	/**
	 * 部署情報検索メソッド（一覧表示画面）
	 * DBに登録されている部署情報を全て表示します
	 *
	 * @return allSectionList DBに登録されている部署情報を全て格納し渡す
	 * @throws SQLException, ClassNotFoundException
	 */
	public ArrayList<SecBean> allSectionSearchDao() {
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;
		//検索した各商品を格納したItemBeanクラスのオブジェクトを格納する
		ArrayList<SecBean> allSectionList = new ArrayList<>();

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成
			String sql = "SELECT * FROM SECTION_TABLE ORDER BY SEC_NO ASC";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//SQLの実行
			rs = stmt.executeQuery();

			//結果を取得
			while (rs.next()) {
				SecBean secBean = new SecBean();
				secBean.setSecNo(rs.getInt("SEC_NO"));
				secBean.setSecName(rs.getString("SEC_NAME"));
				secBean.setDateTime(rs.getTimestamp("UPDATE_TIME"));
				allSectionList.add(secBean);
			}

			//リザルトセットをクローズ
			rs.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//オブジェクトを格納したリストを返す
		return allSectionList;
	}

	/**
	 * ログイン確認メソッド
	 * ログイン画面で入力されたログインIDを受け取り、
	 * 実際に登録されている内容に該当あればその内容を返す
	 *
	 * @param loginId String型のログインID
	 * @return infoBean 値をセットしたInfoBeanオブジェクト
	 * @throws SQLException
	 */
	public UserBean loginCheck(String userId) {
		//戻り値として返すUserBeanクラスのインスタンスを生成
		UserBean userBean = new UserBean();
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成（全商品を選択する）
			String sql = "SELECT * FROM USER_INFO WHERE USER_ID = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメータセット
			stmt.setString(1, userId);
			//SQLの実行
			rs = stmt.executeQuery();

			//結果の取得
			while (rs.next()) {
				userBean.setUserId(rs.getString("USER_ID"));
				userBean.setPassword(rs.getString("PASSWORD"));
				userBean.setSecName(rs.getString("SEC_NAME"));
				userBean.setUserNo(rs.getInt("USER_NO"));
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//値を設定したUserBeanオブジェクトを戻り値として返す
		return userBean;
	}

	/**
	 * ユーザ情報検索メソッド（一覧表示画面）
	 * DBに登録されているユーザ情報を全て表示します
	 *
	 * @return allUserList DBに登録されているユーザ情報を全て格納し渡す
	 * @throws SQLException
	 */
	public ArrayList<UserBean> allUserSearchDao() {
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;
		//検索した各商品を格納したItemBeanクラスのオブジェクトを格納する
		ArrayList<UserBean> allUserList = new ArrayList<>();

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成
			String sql = "SELECT * FROM USER_INFO ORDER BY USER_NO ASC";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//SQLの実行
			rs = stmt.executeQuery();

			//結果を取得
			while (rs.next()) {
				UserBean userBean = new UserBean();
				userBean.setUserNo(rs.getInt("USER_NO"));
				userBean.setUserId(rs.getString("USER_ID"));
				userBean.setPassword(rs.getString("PASSWORD"));
				userBean.setSecName(rs.getString("SEC_NAME"));
				userBean.setDateTime(rs.getTimestamp("UPDATE_TIME"));
				allUserList.add(userBean);
			}

			//リザルトセットをクローズ
			rs.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//オブジェクトを格納したリストを返す
		return allUserList;
	}

	/**
	 * 部署情報重複チェックメソッド（重複チェック処理）
	 * DBに部署情報を登録する前に部署名が重複していないかを確認します
	 *
	 * @param infoBean InfoBean型のクラスオブジェクト
	 * @return num 重複している部署名をSELECTした結果件数
	 * @throws SQLException
	 */
	public int registerSectionCheckDao(SecBean secBean) {
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;
		//return用変数
		int num = 0;
		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成
			String sql = "SELECT COUNT(*) FROM SECTION_TABLE WHERE SEC_NAME = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメーターセット
			stmt.setString(1, secBean.getSecName());
			//SQLの実行
			rs = stmt.executeQuery();

			//結果を取得
			rs.next();
			num = rs.getInt(1);
			//リザルトセットをクローズ
			rs.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//試行結果をreturn（返ってきた件数）
		return num;
	}

	/**
	 * 部署情報登録メソッド
	 * DBに部署情報を登録
	 *
	 * @param infoBean InfoBean型のクラスオブジェクト
	 * @return num INSERTした結果件数
	 * @throws SQLException
	 */
	public int registerSectionDao(SecBean secBean) {
		//returnする変数宣言
		int num = 0;
		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//自動コミットを無効にする
			conn.setAutoCommit(false);

			//商品登録画面で登録された内容をDBのitem_tableに追加する（プレースホルダ）
			String sql = "INSERT INTO SECTION_TABLE (SEC_NO, SEC_NAME) VALUES(SEC_NO.nextval, ?)";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメーターセット
			stmt.setString(1, secBean.getSecName());
			//SQLの実行
			num = stmt.executeUpdate();

			//ステートメントをクローズ
			stmt.close();
			//コミット
			conn.commit();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					//ロールバックしてからクローズ
					conn.rollback();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//試行結果のreturn
		return num;
	}

	/**
	 * 部署名検索メソッド（部署コードから部署名を検索）
	 *
	 * @param secNo int型の部署NO
	 * @return infoBean 値をセットしたInfoBeanオブジェクト
	 * @throws SQLException
	 */
	public SecBean secSearchDao(int secNo) {
		//戻り値として返すLoninBeanクラスのインスタンスを生成
		SecBean secBean = new SecBean();
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成（全商品を選択する）
			String sql = "SELECT * FROM SECTION_TABLE WHERE SEC_NO = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメータセット
			stmt.setInt(1, secNo);
			//SQLの実行
			rs = stmt.executeQuery();

			//結果の取得
			while (rs.next()) {
				secBean.setSecNo(rs.getInt("SEC_NO"));
				secBean.setSecName(rs.getString("SEC_NAME"));
				secBean.setDateTime(rs.getTimestamp("UPDATE_TIME"));
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//値を設定したSecBeanオブジェクトを戻り値として返す
		return secBean;
	}

	/**
	 * ユーザ情報検索メソッド（ユーザNOからユーザ情報を検索）
	 *
	 * @param userNo int型のユーザNO
	 * @return infoBean 値をセットしたInfoBeanオブジェクト
	 * @throws SQLException
	 */
	public UserBean userSearchDao(int userNo) {
		//戻り値として返すUserBeanクラスのインスタンスを生成
		UserBean userBean = new UserBean();
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成（全商品を選択する）
			String sql = "SELECT * FROM USER_INFO WHERE USER_NO = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメータセット
			stmt.setInt(1, userNo);
			//SQLの実行
			rs = stmt.executeQuery();

			//結果の取得
			while (rs.next()) {
				userBean.setUserNo(rs.getInt("USER_NO"));
				userBean.setUserId(rs.getString("USER_ID"));
				userBean.setPassword(rs.getString("PASSWORD"));
				userBean.setSecName(rs.getString("SEC_NAME"));
				userBean.setDateTime(rs.getTimestamp("UPDATE_TIME"));
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//値を設定したUserBeanオブジェクトを戻り値として返す
		return userBean;
	}

	/**
	 * ユーザ情報更新メソッド
	 *
	 * @param userBean UserBean型のクラスオブジェクト
	 * @return num UPDATEした件数を返す
	 * @throws SQLException
	 */
	public int updateUserInfoDao(UserBean userBean) {
		//戻り値として返す変数宣言
		int num = 0;

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成（全商品を選択する）
			String sql = "UPDATE USER_INFO SET USER_ID = ?, PASSWORD = ?, SEC_NAME = ?, UPDATE_TIME = ? WHERE USER_NO = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメータセット
			stmt.setString(1, userBean.getUserId());
			stmt.setString(2, userBean.getPassword());
			stmt.setString(3, userBean.getSecName());
			stmt.setTimestamp(4, userBean.getDateTime());
			stmt.setInt(5, userBean.getUserNo());
			//SQLの実行
			num = stmt.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//値を設定したInfoBeanオブジェクトを戻り値として返す
		return num;
	}

	/**
	 * 部署情報更新メソッド
	 *
	 * @param secBean SecBean型のクラスオブジェクト
	 * @return num UPDATEした件数を返す
	 * @throws SQLException
	 */
	public int updateSectionInfoDao(SecBean secBean) {
		//戻り値として返す変数宣言
		int num = 0;

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成（全商品を選択する）
			String sql = "UPDATE SECTION_TABLE SET SEC_NAME = ?, UPDATE_TIME = ? WHERE SEC_NO = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメータセット
			stmt.setString(1, secBean.getSecName());
			stmt.setTimestamp(2, secBean.getDateTime());
			stmt.setInt(3, secBean.getSecNo());
			//SQLの実行
			num = stmt.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			num = 10;
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//値を設定したInfoBeanオブジェクトを戻り値として返す
		return num;
	}

	/**
	 * ユーザ削除メソッド
	 * 受け取ったユーザNoのユーザ情報を削除する
	 *
	 * @param userNo int型のユーザNo
	 * @return num  削除した結果件数
	 * @throws SQLException
	 */
	public int userDeleteDao(int userNo) {
		//returnする変数宣言
		int num = 0;

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成（商品Noを元に商品情報をSTOCK_TABLEから削除する設定）
			String sql = "DELETE FROM USER_INFO WHERE USER_NO = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメータセット
			stmt.setInt(1, userNo);
			//SQLの実行
			num = stmt.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					//ロールバックしてからクローズ
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//削除した結果件数を返す
		return num;
	}

	/**
	 * 部署削除メソッド
	 * 受け取った部署Noの部署情報を削除する
	 *
	 * @param secNo int型の部署No
	 * @return num  削除した結果件数
	 * @throws SQLException
	 */
	public int sectionDeleteDao(int secNo) {
		//returnする変数宣言
		int num = 0;

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成（商品Noを元に商品情報をSTOCK_TABLEから削除する設定）
			String sql = "DELETE FROM SECTION_TABLE WHERE SEC_NO = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメータセット
			stmt.setInt(1, secNo);
			//SQLの実行
			num = stmt.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			num = 10;
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					//ロールバックしてからクローズ
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//削除した結果件数を返す
		return num;
	}

	/**
	 * 応募者検索メソッド（応募者NOから応募者を検索）
	 *
	 * @param infoNo int型の応募者NO
	 * @return infoBean 値をセットしたInfoBeanオブジェクト
	 * @throws SQLException
	 */
	public InfoBean candiSearchDao(int infoNo) {
		//戻り値として返すLoninBeanクラスのインスタンスを生成
		InfoBean infoBean = new InfoBean();
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成（全商品を選択する）
			String sql = "SELECT * FROM CANDI_INFO WHERE INFO_NO = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメータセット
			stmt.setInt(1, infoNo);
			//SQLの実行
			rs = stmt.executeQuery();

			//結果の取得
			while (rs.next()) {
				infoBean.setInfoNo(rs.getInt("INFO_NO"));
				infoBean.setSecName(rs.getString("SEC_NAME"));
				infoBean.setCandiName(rs.getString("CANDI_NAME"));
				infoBean.setCandiType(rs.getInt("CANDI_TYPE"));
				infoBean.setCandiStage(rs.getString("CANDI_STAGE"));
				infoBean.setRegiDate(rs.getString("REGI_DATE"));
				infoBean.setCandiResult(rs.getInt("CANDI_RESULT"));
				infoBean.setInterDate(rs.getString("INTER_DATE"));
				infoBean.setInterTime(rs.getString("INTER_TIME"));
				infoBean.setDateTime(rs.getTimestamp("UPDATE_TIME"));
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//値を設定したInfoBeanオブジェクトを戻り値として返す
		return infoBean;
	}

	/**
	 * ユーザ情報検索メソッド（ログインIDからユーザ情報を検索）
	 *
	 * @param loginId String型のログインID
	 * @return infoBean 値をセットしたInfoBeanオブジェクト
	 * @throws SQLException
	 */
	public UserBean userSearch(String loginId) {
		//戻り値として返すuserBeanクラスのインスタンスを生成
		UserBean userBean = new UserBean();
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成（全商品を選択する）
			String sql = "SELECT * FROM USER_INFO WHERE USER_NO = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメータセット
			stmt.setString(1, loginId);
			//SQLの実行
			rs = stmt.executeQuery();

			//結果の取得
			while (rs.next()) {
				userBean.setUserNo(rs.getInt("USER_NO"));
				userBean.setUserId(rs.getString("USER_ID"));
				userBean.setPassword(rs.getString("PASSWORD"));
				userBean.setSecName(rs.getString("SEC_NAME"));
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//値を設定したUserBeanオブジェクトを戻り値として返す
		return userBean;
	}

	/**
	 * 応募者の応募書類検索メソッド
	 * DBに登録されている応募書類を検索し渡す
	 *
	 * @param infoNo int型の応募者NO
	 * @return fileName DBに登録されている応募書類名を返す
	 * @throws SQLException
	 */
	public String candiImageDao(int infoNo) {
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;
		//応募書類名を格納する変数宣言
		String fileName = "";

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成
			String sql = "SELECT CANDI_PDF FROM CANDI_INFO WHERE INFO_NO = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメータセット
			stmt.setInt(1, infoNo);
			//SQLの実行
			rs = stmt.executeQuery();

			//結果を取得
			while (rs.next()) {
				fileName = rs.getString("CANDI_PDF");
			}

			//リザルトセットをクローズ
			rs.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//応募書類名を返す
		return fileName;
	}

	/**
	 * 応募者情報更新メソッド
	 * DB内の応募者情報を更新
	 *
	 * @param infoBean InfoBean型のオブジェクト
	 * @return num UPDATEした結果件数
	 * @throws SQLException
	 */
	public int candiUpdateDao(InfoBean infoBean) {
		//returnする変数宣言
		int num = 0;
		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//自動コミットを無効にする
			conn.setAutoCommit(false);

			//商品登録画面で登録された内容をDBのitem_tableに追加する（プレースホルダ）
			String sql = "UPDATE CANDI_INFO SET SEC_NAME = ?, CANDI_NAME = ?, CANDI_TYPE = ?, CANDI_PDF = ?, "
					+ "CANDI_STAGE = ?, REGI_DATE = ?, CANDI_RESULT = ?, INTER_DATE = ?, INTER_TIME = ?, UPDATE_TIME = ? WHERE INFO_NO = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメーターセット
			stmt.setString(1, infoBean.getSecName());
			stmt.setString(2, infoBean.getCandiName());
			stmt.setInt(3, infoBean.getCandiType());
			stmt.setString(4, infoBean.getCandiPdf());
			stmt.setString(5, infoBean.getCandiStage());
			stmt.setString(6, infoBean.getRegiDate());
			stmt.setInt(7, infoBean.getCandiResult());
			stmt.setString(8, infoBean.getInterDate());
			stmt.setString(9, infoBean.getInterTime());
			stmt.setTimestamp(10, infoBean.getDateTime());
			stmt.setInt(11, infoBean.getInfoNo());
			//SQLの実行
			num = stmt.executeUpdate();

			//ステートメントをクローズ
			stmt.close();
			//コミット
			conn.commit();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			num = 10;
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					//ロールバックしてからクローズ
					conn.rollback();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//試行結果のreturn
		return num;
	}

	/**
	 * 応募者削除メソッド
	 * 受け取った応募者Noの応募者情報を削除する
	 *
	 * @param infoNo int型の応募者No
	 * @return num  削除した結果件数
	 * @throws SQLException
	 */
	public int candiDeleteDao(int infoNo) {
		//returnする変数宣言
		int num = 0;

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成（商品Noを元に商品情報をSTOCK_TABLEから削除する設定）
			String sql = "DELETE FROM CANDI_INFO WHERE INFO_NO = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメータセット
			stmt.setInt(1, infoNo);
			//SQLの実行
			num = stmt.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					//ロールバックしてからクローズ
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//削除した結果件数を返す
		return num;
	}

	/**
	 * 応募者結果更新メソッド
	 *
	 * @param infoNo,candiResult int型の応募者NO,結果
	 * @return num UPDATEした件数を返す
	 * @throws SQLException
	 */
	public int candiResultUpdateDao(int infoNo, int candiResult) {
		//戻り値として返す変数宣言
		int num = 0;

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成（全商品を選択する）
			String sql = "UPDATE CANDI_INFO SET CANDI_RESULT = ? WHERE INFO_NO = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメータセット
			stmt.setInt(1, candiResult);
			stmt.setInt(2, infoNo);
			//SQLの実行
			num = stmt.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//更新した件数結果を返す
		return num;
	}

	/**
	 * メッセージ登録メソッド
	 * DBにチャットメッセージを登録
	 *
	 * @param messageBean MessageBean型に必要情報が格納されている
	 * @return num INSERTした結果件数
	 * @throws SQLException
	 */
	public int messageRegiDao(MessageBean messageBean) {
		//returnする変数宣言
		int num = 0;
		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//自動コミットを無効にする
			conn.setAutoCommit(false);

			//商品登録画面で登録された内容をDBのitem_tableに追加する（プレースホルダ）
			String sql = "INSERT INTO MESSAGE_TABLE(ROOM_NO, USER_ID, MESSAGE, UPDATE_TIME) VALUES(?, ?, ?, ?)";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメーターセット
			stmt.setInt(1, messageBean.getRoomNo());
			stmt.setString(2, messageBean.getUserId());
			stmt.setString(3, messageBean.getMessage());
			stmt.setTimestamp(4, messageBean.getDateTime());
			//SQLの実行
			num = stmt.executeUpdate();

			//ステートメントをクローズ
			stmt.close();
			//コミット
			conn.commit();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					//ロールバックしてからクローズ
					conn.rollback();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//試行結果のreturn
		return num;
	}

	/**
	 * メッセージ検索メソッド（一覧表示画面）
	 * DBに登録されている１ルームのメッセージを全て表示します
	 *
	 * @param infoNo int型のユーザNOからルーム検索
	 * @return messageList DBに登録されている１ルームのメッセージ情報を全て格納し渡す
	 * @throws SQLException
	 */
	public ArrayList<MessageBean> messageSearchDao(int infoNo) {
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;
		//検索した各商品を格納したItemBeanクラスのオブジェクトを格納する
		ArrayList<MessageBean> messageList = new ArrayList<>();

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成
			String sql = "SELECT * FROM MESSAGE_TABLE WHERE ROOM_NO = ? ORDER BY UPDATE_TIME ASC";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメーターセット
			stmt.setInt(1, infoNo);
			//SQLの実行
			rs = stmt.executeQuery();

			//結果を取得
			while (rs.next()) {
				MessageBean messageBean = new MessageBean();
				messageBean.setRoomNo(rs.getInt("ROOM_NO"));
				messageBean.setUserId(rs.getString("USER_ID"));
				messageBean.setMessage(rs.getString("MESSAGE"));
				messageBean.setDateTime(rs.getTimestamp("UPDATE_TIME"));
				messageList.add(messageBean);
			}

			//リザルトセットをクローズ
			rs.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//オブジェクトを格納したリストを返す
		return messageList;
	}

	/**
	 * メッセージ削除メソッド（応募者削除と同時に行う処理）
	 * DBに登録されている１ルームのメッセージを全て削除します
	 *
	 * @param infoNo int型のユーザNOからルーム検索
	 * @return count DBに登録されている１ルームのメッセージ削除件数を返す
	 * @throws SQLException
	 */
	public int messageDeleteDao(int infoNo) {
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;
		//検索した各商品を格納したItemBeanクラスのオブジェクトを格納する
		int count = 0;

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//自動コミットを無効にする
			conn.setAutoCommit(false);

			//プレースホルダでSQLを作成
			String sql = "DELETE FROM MESSAGE_TABLE WHERE ROOM_NO = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメーターセット
			stmt.setInt(1, infoNo);
			//SQLの実行
			count = stmt.executeUpdate();

			//ステートメントをクローズ
			stmt.close();
			//コミット
			conn.commit();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					//ロールバックしてからクローズ
					conn.rollback();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//試行結果のreturn
		return count;
	}

	/**
	 * 応募者の希望部署、ユーザの所属部署に
	 * 該当部署名があるかどうか確認メソッド
	 *
	 * @param secName String型の部署名
	 * @return count 該当件数を返す
	 * @throws SQLException
	 */
	public int countSectionDao(String secName) {
		//戻り値として返すcountの変数宣言
		int count = 0;
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成
			String sql = "SELECT COUNT(*) FROM CANDI_INFO WHERE SEC_NAME = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメータセット
			stmt.setString(1, secName);
			//SQLの実行
			rs = stmt.executeQuery();
			//ResultSetの１つ目にカーソルを当て、int型で抽出
			rs.next();
			//count変数に重複している件数をセット
			count += rs.getInt(1);

			//プレースホルダでSQLを作成
			sql = "SELECT COUNT(*) FROM USER_INFO WHERE SEC_NAME = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメータセット
			stmt.setString(1, secName);
			//SQLの実行
			rs = stmt.executeQuery();
			//ResultSetの１つ目にカーソルを当て、int型で抽出
			rs.next();
			//count変数に重複している件数をセット
			count += rs.getInt(1);

			//リザルトセットをクローズ
			rs.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 応募者希望部署、ユーザ所属部署に該当する件数を返す
		return count;
	}

	/**
	 * 部署名からユーザ検索しメールアドレスを取得
	 * メールアドレス取得メソッド
	 *
	 * @param secName String型の部署名
	 * @return mailaddress メールアドレスを格納したリスト
	 * @throws SQLException
	 */
	public ArrayList<String> getMailaddressDao(String secName) {
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;
		//戻り値として返すリストのインスタンスを生成
		ArrayList<String> mailaddress = new ArrayList<>();

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成
			String sql = "SELECT MAILADDRESS FROM USER_INFO WHERE SEC_NAME = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメーターセット
			stmt.setString(1, secName);
			//SQLの実行
			rs = stmt.executeQuery();

			//結果を取得
			while (rs.next()) {
				mailaddress.add(rs.getString("MAILADDRESS"));
			}

			//リザルトセットをクローズ
			rs.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//メールアドレスを格納したリストを返す
		return mailaddress;
	}

	/**
	 * 応募者検索メソッド
	 * 応募者名の部分（完全一致）検索よりヒットする応募者情報をDBより検索し表示する
	 *
	 * @param searchSec,searchName String型の部署名、String型の応募者名
	 * @return searchList 検索にヒットした応募者情報を全て格納し渡す
	 * @throws SQLException
	 */
	public ArrayList<InfoBean> selectSearchDao(String searchSec, String searchName) {
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;
		//検索した各商品を格納したItemBeanクラスのオブジェクトを格納する
		ArrayList<InfoBean> searchList = new ArrayList<>();

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成（全商品を選択する）
			String sql = "SELECT * FROM CANDI_INFO ORDER BY INFO_NO ASC";

			//応募者名がセットされている場合
			if ((searchName != null) && (!searchName.equals(""))) {
				//部署名がセットされている場合
				if ((searchSec != null) && (!searchSec.equals(""))) {
					//応募者名と部署NOで検索。
					sql = "SELECT * FROM CANDI_INFO WHERE CANDI_NAME LIKE ? AND SEC_NAME = ? ORDER BY INFO_NO ASC";
					//SQLをプリコンパイル
					stmt = conn.prepareStatement(sql);
					//パラメーターセット
					stmt.setString(1, "%" + searchName + "%");
					stmt.setString(2, searchSec);
				} else {
					//応募者名で検索。
					sql = "SELECT * FROM CANDI_INFO WHERE CANDI_NAME LIKE ? ORDER BY INFO_NO ASC";
					//SQLをプリコンパイル
					stmt = conn.prepareStatement(sql);
					//パラメータセット
					stmt.setString(1, "%" + searchName + "%");
				}
				//部署名がセットされている場合
			} else {
				if ((searchSec != null) && (!searchSec.equals(""))) {
					//部署名で検索。
					sql = "SELECT * FROM CANDI_INFO WHERE SEC_NAME = ? ORDER BY INFO_NO ASC";
					//SQLをプリコンパイル
					stmt = conn.prepareStatement(sql);
					//パラメータセット
					stmt.setString(1, searchSec);
				} else {
					//SQLをプリコンパイル
					stmt = conn.prepareStatement(sql);
				}
			}
			//部署名が全体の場合
			if ((searchSec != null) && (searchSec.equals("全体"))) {
				//応募者名がなければ
				if ((searchName.equals(""))) {
					//全体表示
					sql = "SELECT * FROM CANDI_INFO ORDER BY INFO_NO ASC";
					//SQLをプリコンパイル
					stmt = conn.prepareStatement(sql);
					//応募者名の指定があれば
				} else {
					//応募者名で検索。
					sql = "SELECT * FROM CANDI_INFO WHERE CANDI_NAME LIKE ? ORDER BY INFO_NO ASC";
					//SQLをプリコンパイル
					stmt = conn.prepareStatement(sql);
					//パラメータセット
					stmt.setString(1, "%" + searchName + "%");
				}

			}
			//SQLの実行
			rs = stmt.executeQuery();
			//結果を取得
			while (rs.next()) {
				InfoBean infoBean = new InfoBean();
				infoBean.setInfoNo(rs.getInt("INFO_NO"));
				infoBean.setSecName(rs.getString("SEC_NAME"));
				infoBean.setCandiName(rs.getString("CANDI_NAME"));
				infoBean.setCandiType(rs.getInt("CANDI_TYPE"));
				infoBean.setCandiPdf(rs.getString("CANDI_PDF"));
				infoBean.setCandiStage(rs.getString("CANDI_STAGE"));
				infoBean.setRegiDate(rs.getString("REGI_DATE"));
				infoBean.setCandiResult(rs.getInt("CANDI_RESULT"));
				infoBean.setInterDate(rs.getString("INTER_DATE"));
				infoBean.setInterTime(rs.getString("INTER_TIME"));
				infoBean.setDateTime(rs.getTimestamp("UPDATE_TIME"));
				searchList.add(infoBean);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//オブジェクトを格納したリストを返す
		return searchList;
	}
	/**
	 * ユーザ削除の可否検索メソッド
	 *
	 * @param secName String型の部署名
	 * @return count 検索件数結果を返す
	 * @throws SQLException
	 */
	public int secNameCheckDao(String secName) {
		//SELECTしたデータを格納する変数宣言
		ResultSet rs = null;
		//return用変数
		int count = 0;
		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成
			String sql = "SELECT COUNT(*) FROM CANDI_INFO WHERE SEC_NAME = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメーターセット
			stmt.setString(1, secName);
			//SQLの実行
			rs = stmt.executeQuery();

			//結果を取得
			rs.next();
			count = rs.getInt(1);
			//リザルトセットをクローズ
			rs.close();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//試行結果をreturn（返ってきた件数）
		return count;
	}
	/**
	 * 新規登録変更メソッド
	 *
	 * @param infoNo int型の応募者NO
	 * @throws SQLException
	 */
	public void newRegiUpdateDao(int infoNo,int newRegi) {

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//プレースホルダでSQLを作成
			String sql = "UPDATE CANDI_INFO SET NEW_REGI = ? WHERE INFO_NO = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメーターセット
			stmt.setInt(1, newRegi);
			stmt.setInt(2, infoNo);
			//SQLの実行
			stmt.executeUpdate();


		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * ラストメッセージ送信者メソッド
	 *
	 * @param infoNo,userId int型の応募者NO、String型のユーザID
	 * @throws SQLException
	 */
	public void lastMessageUserIdDao(int infoNo, String userId) {

		try {
			//OracleJDBCドライバーのロード
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//DBに接続（URL,USER_ID,PASSWORD）
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "AKARI", "AKARI_YUDIN");

			//自動コミットを無効にする
			conn.setAutoCommit(false);

			//商品登録画面で登録された内容をDBのitem_tableに追加する（プレースホルダ）
			String sql = "UPDATE CANDI_INFO SET MESSAGE_ID = ? WHERE INFO_NO = ?";
			//SQLをプリコンパイル
			stmt = conn.prepareStatement(sql);
			//パラメーターセット
			stmt.setInt(1, infoNo);
			stmt.setString(2, userId);
			//SQLの実行
			stmt.executeUpdate();

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				//ステートメント実行していたらステートメントをクローズ
				if (stmt != null) {
					stmt.close();
				}
				//データベース接続していたらステートメントをクローズ
				if (conn != null) {
					//ロールバックしてからクローズ
					conn.rollback();
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}