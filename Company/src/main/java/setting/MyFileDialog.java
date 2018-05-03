package setting;

import java.awt.FileDialog;
import java.awt.Frame;

public class MyFileDialog extends FileDialog{
	private String filepath;
	private String dir;
	private String fileName;
	public static void main(String[] args){
		MyFileDialog t = new MyFileDialog(new Frame());
	}
	public MyFileDialog(Frame f){
		super(f);
	    this.setVisible(true);//表示する
	    dir=this.getDirectory();//ディレクトリーの取得
	    fileName=this.getFile();//File名の取得
	    filepath = dir + fileName;
	    System.out.println(filepath);
	    if (fileName==null)  System.exit(0);//ファイル名の設定が無ければ処理中止
	}
	String getFilePath() {
		return filepath;
	}
	String getRootPath() {
		return dir;
	}
	String getFileName() {
		return fileName;
	}
}