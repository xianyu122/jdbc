package com.hfxt.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class JdbcUtil {

	private static ResourceBundle rb = ResourceBundle.getBundle("db");
	private static Connection conn = null;

	static {
		try {
			Class.forName(rb.getString("driver"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConn() {
		try {
			conn = DriverManager.getConnection(rb.getString("url"), rb.getString("user"), rb.getString("pass"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeAll(Connection conn, ResultSet res, Statement stmt) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				conn = null;
				e.printStackTrace();
			}
		}
		if (res != null) {
			try {
				res.close();
			} catch (SQLException e) {
				res = null;
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				stmt = null;
				e.printStackTrace();
			}
		}
	}

}
