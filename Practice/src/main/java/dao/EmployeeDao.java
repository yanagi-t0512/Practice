package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Employee;

public class EmployeeDao {

	// データベース接続に使用する情報
	private final String JDBC_ULR = "jdbc:h2:tcp://localhost/~/practice";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";
	
	public List<Employee> findAll() {
		List<Employee> empList = new ArrayList<>();
		
		// データベースへ接続
		try (Connection conn = DriverManager.getConnection(
				JDBC_ULR, DB_USER, DB_PASS)) {
			
			// SELECT文を準備
			String sql = "SELECT ID, NAME, AGE FROM EMPLOYEE";
			PreparedStatement pStmt = conn.prepareCall(sql);
			
			// SELECT文を実行し、結果表を取得
			ResultSet rs = pStmt.executeQuery();
			
			// 結果表に格納されたレコードの内容を
			// Employeeインスタンスに設定し、ArrayListインスタンスに追加
			while(rs.next()) {
				String id = rs.getString("ID");
				String name = rs.getString("NAME");
				int age = rs.getInt("AGE");
				Employee employee = new Employee(id, name, age);
				empList.add(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return empList;
	}
}