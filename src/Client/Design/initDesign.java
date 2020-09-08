package Client.Design;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class initDesign {
	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	private LoginJDBC db;
	private String id = null, pw = null;
	private boolean loginchk;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void makeFrame() {
		drawFrame();
	}

	private void drawFrame() {
		// TODO Auto-generated method stub
		frame = new JFrame("로그인");
		frame.setSize(295, 168);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);//원도우를 매개변수 안의 컴포넌트에 따라 상대적인 위치를 지정할수 있다. 
		                                  //null일 경우 원도우 창을 화면의 가운데에 띄우는 역할을 한다.

	
		JPanel logPane = new JPanel(); // JPanel의 객체 logPane 생성
		logPane.setBounds(39, 5, 199, 109);
		logPane.setLayout(new GridLayout(0, 1, 5, 5));

		JPanel panel1 = new JPanel(); // JPanel의 객체 panel1 생성
		panel1.setLayout(null);
		JLabel lblNewLabel1 = new JLabel("ID"); // JLabel의 객체 lblNewLabel1생성
		lblNewLabel1.setFont(new Font("맑은고딕", Font.BOLD, 12));
		lblNewLabel1.setBounds(0, 10, 47, 23);
		lblNewLabel1.setHorizontalAlignment(SwingConstants.CENTER); // JLabel의 가운데정렬
		panel1.add(lblNewLabel1);

		textField = new JTextField();
		textField.setBounds(52, 10, 120, 23);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		panel1.add(textField);
		textField.setColumns(10);
		panel1.setBounds(30, 30, 20, 20);
		logPane.add(panel1);

		JPanel panel2 = new JPanel();
		panel2.setLayout(null);
		JLabel lblNewLabel2 = new JLabel("PW");
		lblNewLabel2.setFont(new Font("맑은고딕", Font.BOLD, 12));
		lblNewLabel2.setBounds(0, 8, 47, 23);
		lblNewLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		panel2.add(lblNewLabel2);

		passwordField = new JPasswordField(10);
		passwordField.setBounds(52, 8, 120, 23);
		passwordField.setHorizontalAlignment(JTextField.CENTER);
		panel2.add(passwordField);
		logPane.add(panel2);
		JPanel panel3 = new JPanel();
		JButton btnNewButton = new JButton("로그인");

		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				id = textField.getText();
				pw = passwordField.getText();
				db = new LoginJDBC(btnNewButton.getText(), id, pw);

				loginchk = db.getLoginchk(); // db의 ㅣoginchk를 얻어온다.

				if (loginchk == true) {
					frame.dispose(); // 원하는 하나의 Frame만 종료 시키기 위해서 dispose() 메소드를 사용
				}

			}
		});

		JButton btnNewButton_1 = new JButton("회원가입");
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				id = textField.getText();
				pw = passwordField.getText();
				db = new LoginJDBC(btnNewButton_1.getText(), id, pw);

			}
		});
		frame.getContentPane().setLayout(null);

		panel3.add(btnNewButton);
		panel3.add(btnNewButton_1);

		logPane.add(panel3);
		frame.getContentPane().add(logPane);
		frame.setVisible(true);

	}

	public String getID() {
		return this.id;
	}

	public String getPW() {
		return this.pw;
	}

	public boolean getLoginchk() {
		return this.loginchk;
	}

}