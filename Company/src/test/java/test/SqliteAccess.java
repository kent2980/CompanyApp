package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Sqlite3に接続して、データの作成、登録、削除を行います
 * ******************************************。
 * <マシンコード一覧>
 * 1-->"TCM",2-->"NXT",3-->"GX",4-->"TIM"
 * ******************************************
 * @author 健太郎
 *
 */
public class SqliteAccess {
	public static void main(String[] args) {
		SqliteAccess a = new SqliteAccess();
		System.out.println(a.getCassetteBango("GC18", "1S4058011", 2));
	}
	private Connection con;
	public SqliteAccess() {
		setConnection();
	}
	public void setConnection() {
		try {
			con = DriverManager.getConnection("jdbc:sqlite:./data/test.db");
		} catch (SQLException e) {
			System.out.println("DBにアクセスできません。");
		}
	}
	public void closeConnection() {
		try {
			con.close();
			System.out.println("データベースへの接続を終了しました。");
		} catch (SQLException e) {
			System.out.println("データベースはクローズです。" + e.getMessage());
		}
	}
	/**
	 * 取付け部品データを保存します。
	 * @param tana 棚番
	 * @param parts 部品コード
	 * @param cassette カセット番号
	 * @param mccode マシンコード
	 */
	public String setPartsData(String tanaName,String parts,int cassetteBango,int mcCode) {
		try {
			int cassetteCode = Integer.valueOf(String.valueOf(mcCode) + String.valueOf(cassetteBango));
			String sql = "UPDATE SET_PARTSLIST SET PARTS_NAME=?,TANA=? WHERE CASSETTE_CODE=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, parts);
			pstmt.setString(2, tanaName);
			pstmt.setInt(3, cassetteCode);
			pstmt.executeUpdate();
			pstmt.close();
			String mcname = mcCode==1?"TCM":mcCode==2?"NXT":mcCode==3?"GX":"TIM";
			System.out.println(tanaName + "," + mcname + "," + cassetteBango + "," + parts + " ---> 更新しました。");
			return tanaName + mcname + cassetteBango + parts;
		}catch(SQLException e) {
			System.out.println("データの更新が出来ませんでした。" + e.getMessage());
			return null;
		}
	}
	/**
	 * 取付け部品データを削除します。
	 * @param cassetteBango カセット番号
	 * @param mcCode　マシンコード
	 */
	public void deletePartsData(int mcCode,int cassetteBango) {
		try {
			String sql = "UPDATE SET_PARTSLIST SET PARTS_NAME=NULL,TANA=NULL WHERE EXISTS(SELECT * FROM SET_CASSETTELIST T2 WHERE SET_PARTSLIST.CASSETTE_CODE=T2.CASSETTE_CODE AND T2.MC_CODE=? AND T2.BANGO=?)";;
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, mcCode);
			pstmt.setInt(2, cassetteBango);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			System.out.println("削除できるデータが存在しません。" + e.getMessage());
		}
	}
	/**
	 * 取付け部品データを削除します。
	 * @param cassetteBango カセット番号
	 * @param mcCode　マシンコード
	 */
	public void deletePartsData(String tanaName,int mcCode,int cassetteBango) {
		try {
			String sql = "UPDATE SET_PARTSLIST SET PARTS_NAME=NULL,TANA=NULL WHERE EXISTS(SELECT * FROM SET_CASSETTELIST T2 WHERE SET_PARTSLIST.CASSETTE_CODE=T2.CASSETTE_CODE AND T2.MC_CODE=? AND T2.BANGO=?) AND TANA=?";;
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, mcCode);
			pstmt.setInt(2, cassetteBango);
			pstmt.setString(3, tanaName);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			System.out.println("削除できるデータが存在しません。" + e.getMessage());
		}
	}
	/**
	 * 取付け部品データを削除します。
	 * @param tanaName 棚番
	 * @param partsName　部品コード
	 */
	public void deletePartsData(String tanaName,String partsName) {
		try {
			String sql = "UPDATE SET_PARTSLIST SET PARTS_NAME=NULL,TANA=NULL WHERE PARTS_NAME=? AND TANA=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, partsName);
			pstmt.setString(2, tanaName);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			System.out.println("削除できるデータが存在しません。" + e.getMessage());
		}
	}
	/**
	 * 取付け部品データを削除します。
	 * @param tanaName 棚番
	 * @param partsName　部品コード
	 */
	public void deletePartsData(String tanaName,int mcCode) {
		try {
			String sql = "UPDATE SET_PARTSLIST SET PARTS_NAME=NULL,TANA=NULL WHERE EXISTS(SELECT * FROM SET_CASSETTELIST T2 WHERE SET_PARTSLIST.CASSETTE_CODE=T2.CASSETTE_CODE AND T2.MC_CODE=?) AND TANA=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, mcCode);
			pstmt.setString(2, tanaName);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			System.out.println("削除できるデータが存在しません。" + e.getMessage());
		}
	}
	/**
	 * 取付け部品データを削除します。
	 * @param tanaName 棚番
	 */
	public void deletePartsData(String tanaName) {
		try {
			String sql = "UPDATE SET_PARTSLIST SET PARTS_NAME=NULL,TANA=NULL WHERE TANA=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tanaName);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			System.out.println("削除できるデータが存在しません。" + e.getMessage());
		}
	}
	/**
	 * 取付け部品データを削除します。
	 * @param tanaName マシンコード
	 */
	public void deletePartsData(int mcCode) {
		try {
			String sql = "UPDATE SET_PARTSLIST SET PARTS_NAME=NULL,TANA=NULL WHERE EXISTS(SELECT * FROM SET_CASSETTELIST T2 WHERE SET_PARTSLIST.CASSETTE_CODE=T2.CASSETTE_CODE AND T2.MC_CODE=?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, mcCode);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			System.out.println("削除できるデータが存在しません。" + e.getMessage());
		}
	}
	//get部品コード
	public String getPartsName(int cassetteBango,int mcCode) {
		try {
			int cassetteCode = Integer.valueOf(String.valueOf(mcCode) + String.valueOf(cassetteBango));
			String sql = "SELECT PARTS_NAME FROM SET_PARTSLIST WHERE CASSETTE_CODE=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cassetteCode);
			ResultSet rs = pstmt.executeQuery();
			String name = rs.next() ? rs.getString("PARTS_NAME"):null;
			rs.close();
			pstmt.close();
			return name;
		}catch(SQLException e) {
			System.out.println("部品コードを検索出来ませんでした。" + e.getMessage());
			return null;
		}
	}
	public String getPartsName(String tanaName,int cassetteBango,int mcCode) {
		try {
			int cassetteCode = Integer.valueOf(String.valueOf(mcCode) + String.valueOf(cassetteBango));
			String sql = "SELECT PARTS_NAME FROM SET_PARTSLIST WHERE CASSETTE_CODE=? AND TANA=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cassetteCode);
			pstmt.setString(2, tanaName);
			ResultSet rs = pstmt.executeQuery();
			String name = rs.next() ? rs.getString("PARTS_NAME"):null;
			rs.close();
			pstmt.close();
			return name;
		}catch(SQLException e) {
			System.out.println("部品コードを検索出来ませんでした。" + e.getMessage());
			return null;
		}
	}
	//get棚番
	public String getTanaNAme(int cassetteBango,int mcCode) {
		try {
			int cassetteCode = Integer.valueOf(String.valueOf(mcCode) + String.valueOf(cassetteBango));
			String sql = "SELECT TANA FROM SET_PARTSLIST WHERE CASSETTE_CODE=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cassetteCode);
			ResultSet rs = pstmt.executeQuery();
			String name = rs.next() ? rs.getString("TANA"):null;
			rs.close();
			pstmt.close();
			return name;
		}catch(SQLException e) {
			System.out.println("棚番名を検索出来ませんでした。" + e.getMessage());
			return null;
		}
	}
	public String getTanaNAme(String tanaName,int cassetteBango,int mcCode) {
		try {
			int cassetteCode = Integer.valueOf(String.valueOf(mcCode) + String.valueOf(cassetteBango));
			String sql = "SELECT TANA FROM SET_PARTSLIST WHERE CASSETTE_CODE=? AND TANA=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cassetteCode);
			pstmt.setString(2, tanaName);
			ResultSet rs = pstmt.executeQuery();
			String name = rs.next() ? rs.getString("TANA"):null;
			rs.close();
			pstmt.close();
			return name;
		}catch(SQLException e) {
			System.out.println("棚番名を検索出来ませんでした。" + e.getMessage());
			return null;
		}
	}
	//getカセット番号
	public int getCassetteBango(String tanaName,String partsName,int mcCode) {
		try {
			String sql = "SELECT T2.BANGO FROM SET_PARTSLIST AS T1 JOIN SET_CASSETTELIST AS T2 USING(CASSETTE_CODE) WHERE T1.PARTS_NAME=? AND T1.TANA=? AND T2.MC_CODE=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, partsName);
			pstmt.setString(2, tanaName);
			pstmt.setInt(3, mcCode);
			ResultSet rs = pstmt.executeQuery();
			int bango = rs.next() ? rs.getInt("BANGO"):0;
			rs.close();
			pstmt.close();
			return bango;
		}catch(SQLException e) {
			System.out.println("カセット番号を検索出来ませんでした。" + e.getMessage());
			return 0;
		}
	}
	//getマシン名
	public String getMcName(String tanaName,String partsName) {
		try {
			String sql = "SELECT T2.MC_CODE FROM SET_PARTSLIST AS T1 JOIN SET_CASSETTELIST AS T2 USING(CASSETTE_CODE) WHERE T1.PARTS_NAME=?　AND T1.TANA=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, partsName);
			pstmt.setString(2, tanaName);
			ResultSet rs = pstmt.executeQuery();
			Integer code = rs.next() ? rs.getInt("MC_CODE"):null;
			String name = code.equals(1)?"TCM":code.equals(2)?"NXT":code.equals(3)?"GX":code.equals(4)?"TIM":null;
			rs.close();
			pstmt.close();
			return name;
		}catch(SQLException e) {
				System.out.println("マシン名を検索出来ませんでした。" + e.getMessage());
				return null;
			}
	}
	//設定
	public String getSettingPath(String item) {
		try {
			String sql = "SELECT PATH FROM SETTING WHERE ITEM=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, item);
			ResultSet rs = pstmt.executeQuery();
			String path = rs.next()?rs.getString("PATH"):null;
			rs.close();
			pstmt.close();
			return path;
		}catch(SQLException e) {
			System.out.println("設定ファイルが見つかりません" + e);
			return null;
		}
	}
	public void setSettingPath(String item,String path) {
		try {
			String sql = "UPDATE SETTING SET PATH=? WHERE ITEM=?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, path);
			pstmt.setString(2, item);
			pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			System.out.println("設定ファイルが書き込めません" + e);
		}
	}
}
