package setting;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import mainApp.SqliteAccess;

public class ExcelWrite {
	
	//フィールド
	private Workbook book1 = null;
	private Workbook book2 = null;
	private String modelName = null;
	private String kanriNo = null;
	private final SqliteAccess sq = new SqliteAccess();
	private Sheet sheet;
	private Sheet sheet2;
	
	//デフォルトコンストラクタ
	public ExcelWrite(String tanaban,int mccode) {
		try(FileInputStream in = new FileInputStream(sq.getSettingPath("INPUT"));
			FileInputStream in2 = new FileInputStream("./data/FORMAT.xls")) {
			book1 = WorkbookFactory.create(in);
			book2 = WorkbookFactory.create(in2);
			sheet = book1.getSheetAt(0);
			sheet2 = book2.getSheetAt(0);
		} catch (EncryptedDocumentException | IOException | InvalidFormatException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		System.out.println(sheet.getLastRowNum());
		//3行目の列幅を設定します。
		sheet.setColumnWidth(2, 3000);
		//セルの書式をコピーする
		Row b1 = sheet.getRow(10);
		Cell e1 = b1.getCell(0);
		//クリップ記入
		Row row = sheet.getRow(9);
		Cell a1 = row.createCell(2);
		a1.setCellValue("クリップ");
		a1.setCellStyle(e1.getCellStyle());
		//モデル名を取得
		row = sheet.getRow(3);
		a1 = row.getCell(1);
		modelName = a1.getStringCellValue();
		//管理Noを取得
		row = sheet.getRow(1);
		a1 = row.getCell(1);
		kanriNo = a1.getStringCellValue();
		//最終列&最終行を取得する
		int lastrow = sheet.getLastRowNum();
		int lastcolumn = sheet.getRow(0).getLastCellNum();
		//部品コードにカセット番号を追加する
		for(int i = 10;i<=lastrow;i++) {
			Row getRow = sheet.getRow(i);
			Row setRow = sheet.getRow(i);
			Cell getCell = getRow.getCell(1);
			Cell setCell = setRow.createCell(2);
			String partsName = getCell.getStringCellValue();
			System.out.println(partsName);
			int bango = sq.getCassetteBango(tanaban, partsName, mccode);
			if(bango!=0)
			setCell.setCellValue(String.valueOf(bango));
		}
		System.out.println("出力終了");
		//book1 --> book2 にコピーします。
		for(int y = 0;y<=lastrow;y++) {
			Row getRow = sheet.getRow(y);
			Row setRow = sheet2.createRow(y);
			for(int x = 0;x<=lastcolumn;x++) {
				Cell getCell = getRow.getCell(x);
				Cell setCell = setRow.createCell(x);
				if(getCell!=null) {
					System.out.println("x:" + x + ",y:" + y + "." + getCell.getCellTypeEnum());
					switch(getCell.getCellTypeEnum()) {
					case BLANK:
						continue;
					case NUMERIC:
		                setCell.setCellValue(String.format("%03d",(int)getCell.getNumericCellValue()));
		                continue;
		            case STRING:
		            	setCell.setCellValue(getCell.getStringCellValue());
		            	continue;
		            default:
		                continue;
					}
				}
			}
		}
		// 出力先のファイルを指定
		try(FileOutputStream out = new FileOutputStream(sq.getSettingPath("OUTPUT") + modelName + "_" + kanriNo + ".xls")) {
		    /***上記で作成したブックを出力先に書き込み***/
		    book2.write(out);
		} catch (IOException e) {
			System.out.println("ファイルが保存出来ませんでした。");
		}
		// 出力ファイルを開きます。
		try {
			Desktop.getDesktop().open(new File(sq.getSettingPath("OUTPUT") + modelName + "_" + kanriNo + ".xls"));
		} catch (IOException e) {
			System.out.println("対象ファイルが開けません。");
		}
	}
}
