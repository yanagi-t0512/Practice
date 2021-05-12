package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Mutter;

public class MutterDao {

	// データベース接続に使用する情報
	private final String JDBC_ULR = "jdbc:h2:tcp://localhost/~/docoTsubu";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";
	
	public List<Mutter> findAll() {
		List<Mutter> mutterList = new ArrayList<>();
		
		// データベースへ接続
		try (Connection conn = DriverManager.getConnection(
				JDBC_ULR, DB_USER, DB_PASS)) {
			
			// SELECT文を準備
			String sql = "SELECT ID, NAME, TEXT FROM MUTTER ORDER BY ID DESC";
			PreparedStatement pStmt = conn.prepareCall(sql);
			
			// SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();
			
			// SELECT文の結果を、ArrayListインスタンスに追加
			while(rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("NAME");
				String text = rs.getString("TEXT");
				Mutter mutter = new Mutter(id, name, text);
				mutterList.add(mutter);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return mutterList;
	}
	
	public boolean create(Mutter mutter) {
		// データベースへ接続
		try (Connection conn = DriverManager.getConnection(
				JDBC_ULR, DB_USER, DB_PASS)) {
			
			// INSERT文を準備
			String sql = "INSERT INTO MUTTER(NAME, TEXT) VALUES(?, ?)";
			PreparedStatement pStmt = conn.prepareCall(sql);
			
			// INSERT文中の「?」に使用する値を設定しSQLを完成
			pStmt.setString(1, mutter.getUserName());
			pStmt.setString(2, mutter.getText());
			
			// INSERT文を実行
			int result = pStmt.executeUpdate();
			if(result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}