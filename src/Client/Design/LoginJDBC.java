package Client.Design;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LoginJDBC extends JFrame {
	Connection con = null;
	Statement stmt = null;

	private boolean Loginchk = false; // Loginchk의 기본 값을 false로 설정

	public LoginJDBC(String loginType, String id, String pw) {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // JDBC 드라이버 로딩
			con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:ORCLOSY", "scott", "tiger"); // DB연결
			stmt = con.createStatement(); // Connection 인스턴스를 이용해서 Statement 객체 생성
			System.out.println("DB 접속 완료!");

			if (loginType == "로그인") {// 앞에서 만들었던 버튼의 텍스트를 비교 로그인버튼을 눌렀을 경우
				String sql = "select id from member2 where Id = '" + id + "' and Pw = '" + pw + "'";
				ResultSet rs = stmt.executeQuery(sql); // Statement 객체의 결과를 executeQuery 메서드를 이용해 rs에 받음

				if (!rs.next()) {// 일치하는 아이디와 비밀번호가 없을 때
					if (!id.equals("")) {// 아이디가 빈칸이 아니라면
						JOptionPane.showMessageDialog(getParent(), "로그인 실패하였습니다.");// 메시지를 띄움
					} else {
						JOptionPane.showMessageDialog(getParent(), "아이디를 입력해주세요.");
					}
				} else {// 아이디와 비밀번호가 일치할 경우
					Loginchk = true; // 로그인 성공시에만 Loginchk가 true가 된다.
					JOptionPane.showMessageDialog(getParent(), "로그인 성공하였습니다.");

				}

			} else if (loginType == "회원가입") {
				String sql = "select Id from member2 where Id = '" + id + "'";
				ResultSet rs = stmt.executeQuery(sql);

				if (!rs.next()) {
					if (!id.equals("")) {
						String sql2 = "insert into member2(Id,Pw) values('" + id + "','" + pw.toString() + "')";
						System.out.println(sql2);
						stmt.executeUpdate(sql2);
						JOptionPane.showMessageDialog(getParent(), "회원가입에 성공했습니다.");
					} else {
						JOptionPane.showMessageDialog(getParent(), "아이디를 입력해주세요.");
					}
				} else {
					JOptionPane.showMessageDialog(getParent(), "이미 있는 아이디 입니다.");
				}

			}

		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public boolean getLoginchk() {
		return Loginchk;
	}

}