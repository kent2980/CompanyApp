package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import test.SqliteAccess;

public class Sample {
	static SqliteAccess sq = new SqliteAccess();
	  public static void main(String args[]) {
		    try {
		      File f = new File("./data/NXT.csv");
		      BufferedReader br = new BufferedReader(new FileReader(f));
		 
		      String line;
		      // 1行ずつCSVファイルを読み込む
		      while ((line = br.readLine()) != null) {
		        String[] data = line.split(",", 0); // 行をカンマ区切りで配列に変換
		        for (String elem : data) {
		          System.out.println(elem);
		        }
		        sq.setPartsData("GC18", data[0], Integer.parseInt(data[1]), 2);
		      }
		      br.close();

		    } catch (IOException e) {
		      System.out.println(e);
		    }
	 }
}