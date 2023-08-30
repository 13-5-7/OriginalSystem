package util;

import java.io.File;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import model.InfoBean;

public class FileUploadOriginal {
	/**
	 * ファイルアップロードメソッド
	 * @param request
	 * @param path
	 * @return ItemBean
	 * @throws
	 */
	public InfoBean  FileUp(HttpServletRequest request, String path) {
		// return用変数
		InfoBean infoBean = new InfoBean();
		// データを処理するコンストラクタ
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(10000000);		// 10MBを最大値とする
		factory.setRepository(new File(path));	// バッファ内のデータの一時保存領域を指定、Fileクラスとは？

		try {
			// こっちのコンストラクタが推奨されている
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(10000000);		//ファイルサイズの最大値
			upload.setHeaderEncoding("UTF-8");	//ファイル名の文字コード
			// 受け取ったリクエストのFileItemをListに格納
			List<FileItem> list = upload.parseRequest(request);
			// イテレータ
			Iterator<FileItem> iterator = list.iterator();

			// イテレータで先頭から順に評価
			while(iterator.hasNext()) {
				FileItem file = iterator.next();
				// フォームに入力された値かどうか？
				if(!file.isFormField()) {
					// ファイル名を取得
					String fileName = file.getName();
					// 拡張子チェック
					String[] exs = {"pdf"};
					if(extensionCheck(fileName, exs)) {
						// ファイル名を保存
						infoBean.setCandiPdf(fileName);
						file.write(new File(path + "/" + fileName));
					}else {
						infoBean.setCandiPdf("");
					}
				}else {
					// フォームのnameに応じで、ItemBeanに保存
					switch(file.getFieldName()) {
					case "secName" :
						infoBean.setSecName(file.getString("UTF-8"));
						break;
					case "candiName" :
						infoBean.setCandiName(file.getString("UTF-8"));
						break;
					case "candiType" :
						infoBean.setCandiType(Integer.parseInt(file.getString("UTF-8")));
						break;
					case "candiStage" :
						infoBean.setCandiStage(file.getString("UTF-8"));
						break;
					case "regiDate" :
						infoBean.setRegiDate(file.getString("UTF-8"));
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infoBean;
	}

	public InfoBean  FileUpUpdate(HttpServletRequest request, String path) {
		// return用変数
		InfoBean infoBean = new InfoBean();
		// データを処理するコンストラクタ
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(10000000);		// 10MBを最大値とする
		factory.setRepository(new File(path));	// バッファ内のデータの一時保存領域を指定、Fileクラスとは？

		try {
			// こっちのコンストラクタが推奨されている
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(10000000);		//ファイルサイズの最大値
			upload.setHeaderEncoding("UTF-8");	//ファイル名の文字コード
			// 受け取ったリクエストのFileItemをListに格納
			List<FileItem> list = upload.parseRequest(request);
			// イテレータ
			Iterator<FileItem> iterator = list.iterator();

			// イテレータで先頭から順に評価
			while(iterator.hasNext()) {
				FileItem file = iterator.next();
				// フォームに入力された値かどうか？
				if(!file.isFormField()) {
					// ファイル名を取得
					String fileName = file.getName();
					// 拡張子チェック
					String[] exs = {"pdf"};
					if(extensionCheck(fileName, exs)) {
						// ファイル名を保存
						infoBean.setCandiPdf(fileName);
						file.write(new File(path + "/" + fileName));
					}else {
						infoBean.setCandiPdf("");
					}
				}else {
					// フォームのnameに応じで、InfoBeanに保存
					switch(file.getFieldName()) {
					case "infoNo" :
						infoBean.setInfoNo(Integer.parseInt(file.getString("UTF-8")));
						break;
					case "secName" :
						infoBean.setSecName(file.getString("UTF-8"));
						break;
					case "candiName" :
						infoBean.setCandiName(file.getString("UTF-8"));
						break;
					case "candiType" :
						infoBean.setCandiType(Integer.parseInt(file.getString("UTF-8")));
						break;
					case "candiStage" :
						infoBean.setCandiStage(file.getString("UTF-8"));
						break;
					case "regiDate" :
						infoBean.setRegiDate(file.getString("UTF-8"));
						break;
					case "dateTime" :
						String updateTime = file.getString("UTF-8");
						Timestamp beforeTime = Timestamp.valueOf(updateTime);
						infoBean.setDateTime(beforeTime);
						break;
					case "interDate" :
						infoBean.setInterTime(file.getString("UTF-8"));
						break;
					case "interTime" :
						infoBean.setInterTime(file.getString("UTF-8"));
						break;
					case "candiResult" :
						infoBean.setCandiResult(Integer.parseInt(file.getString("UTF-8")));
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infoBean;
	}


	/**
	 * 拡張子チェックメソッド
	 * @param String fileName, String[] サポートする拡張子リスト
	 * @return boolean
	 */
	public boolean extensionCheck(String fileName, String[] exs) {
		boolean extension = false;
		// "."で分割（エスケープ処理）
		String[] str = fileName.split("\\.");
		// 拡張子（配列の最後の値）を取得
		String extens = str[str.length - 1];
		// 拡張子が「png」「jpg」「jpeg」のいずれかであればtrueを返す
		for(String ex : exs) {
			if(extens.equals(ex)) {
				extension = true;
			}
		}
		return extension;
	}
}
