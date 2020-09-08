package Client.Design;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import Client.Core.GameController;
import Client.Core.socket.SendMessage;
import Client.Design.maindesigncomponents.Brush;
import Client.Design.maindesigncomponents.Colorbtns;
import Client.Design.maindesigncomponents.Eraserbtn;
import Client.Design.maindesigncomponents.Penbtn;
import Client.Design.maindesigncomponents.Thicknessbtns;

public class MainDesign {

	private final int panelWidth = 840; // 그림판 너비
	private final int panelHeight = 671; // 그림판 높이
	private JFrame frame;
	private BufferedImage imgbuff;
	private JLabel imgpanel;
	private Brush brush;
	private JButton clearbtn, sendbtn;
	private JTextField answerfield;
	private JTextArea screen, userList;
	private JTextField input, TimerField;

	private Colorbtns btns;
	private Eraserbtn eraserbtn;
	private Thicknessbtns thicknessbtns;
	private Penbtn penbtn;
	private JPanel panel, colorbtnPanel, linePanel, brushJPanel, userJPanel;
	private static JPanel currentColor = new JPanel();

	/**
	 * @wbp.parser.entryPoint
	 */
	public void makeFrame() {
		drawFrame();
		drawImgSection();
		makeBrush();
		makeMouseEvent();
		brushSelect();
		lineSelect();
		drawButtons();
		drawAnswerField();
		drawUserList();
		drawChat();
		drawTimer();
		frame.repaint();
	}

	private void drawFrame() { // 게임판 프레임
		frame = new JFrame("캐치캐치");
		frame.setSize(1300, 700); // 프레임의 크기를 1300 * 700의로 설정
		frame.setLocationRelativeTo(null); //프레임을 화면 가운데로 띄운다.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Frame 을 종료하기 위한 메소드
		frame.setResizable(false); // Frame 을 크기조정을 불가능하게 한다.
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
	}

	private void drawImgSection() { // 이미지를 그려낼 그림판 객체를 프레임에 생성
		imgbuff = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_ARGB);
		imgpanel = new JLabel(new ImageIcon(imgbuff));
		imgpanel.setBounds(180, 0, panelWidth, panelHeight);
		imgpanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		frame.getContentPane().add(imgpanel);
	}

	private void makeBrush() { // 그림을 그릴때 좌표를 공유할 브러쉬 객체를 프레임에 생성
		brush = new Brush();
		brush.setBounds(180, 0, panelWidth, panelHeight);
		brush.repaint();
		brush.printAll(imgbuff.getGraphics());
		frame.getContentPane().add(brush);
	}

	private void drawTimer() { //타이머를 표시할 필드를 구현하는 메서드
		TimerField = new JTextField();
		TimerField.setBackground(SystemColor.inactiveCaptionBorder);
		TimerField.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		TimerField.setEnabled(false);
		TimerField.setHorizontalAlignment(SwingConstants.CENTER);
		TimerField.setBounds(13, 10, 155, 44);
		frame.getContentPane().add(TimerField);
		TimerField.setColumns(10);
	}

	private void makeMouseEvent() {
		imgpanel.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) { // 마우스 드래그시
				if (GameController.turnflag == true) { // 게임의 차례가 내순서일 경우 실행
					SendMessage.send.println("Position:" + e.getX() + "," + e.getY()); // 서버로 좌표값 전송
					SendMessage.send.flush(); 
				}
			}

			public void mouseMoved(MouseEvent e) {

			}
		});
	}

	private void brushSelect() { // 그리기도구 설정

		brushJPanel = new JPanel();
		brushJPanel.setLayout(null);
		brushJPanel.setBounds(13, 69, 155, 111);
		brushJPanel.setBorder(new TitledBorder("그리기 도구"));

		penbtn = new Penbtn();
		penbtn.setBrush(brush);
		penbtn.makePenbtn();

		brushJPanel.add(penbtn.penButton()); // 버튼을 패널에 추가
		brushJPanel.add(penbtn.brushButton()); // 버튼을 패널에 추가

		frame.getContentPane().add(brushJPanel); // 패널을 프레임에 추가

	}

	private void lineSelect() { // 선 두께 설정

		linePanel = new JPanel();
		linePanel.setLayout(null);
		linePanel.setBounds(13, 186, 155, 147);
		linePanel.setBorder(new TitledBorder("펜 두께"));

		thicknessbtns = new Thicknessbtns();
		thicknessbtns.setBrush(brush);
		thicknessbtns.makeThicknessbtn();

		linePanel.add(thicknessbtns.thin()); // 버튼을 패널에 추가
		linePanel.add(thicknessbtns.nomal()); // 버튼을 패널에 추가
		linePanel.add(thicknessbtns.thick()); // 버튼을 패널에 추가

		frame.getContentPane().add(linePanel); // 패널을 프레임에 추가

	}

	private void drawButtons() { // 컬러선택 버튼
		btns = new Colorbtns();
		btns.setBrush(brush);
		btns.setCurrentColor(currentColor);
		btns.makebutton();

		currentColor.setBorder(new TitledBorder("Current Color"));
		currentColor.setBounds(10, 165, 130, 30);

		colorbtnPanel = new JPanel();
		colorbtnPanel.setLayout(null);
		colorbtnPanel.setBounds(13, 452, 155, 210);
		colorbtnPanel.setBorder(new TitledBorder("Color"));
		currentColor.setBackground(brush.color);

		colorbtnPanel.add(btns.getSelectColor());

		colorbtnPanel.add(currentColor);
		frame.getContentPane().add(colorbtnPanel);

		makeEraserButton();
	}

	private void makeEraserButton() { // 지우개버튼
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(13, 337, 155, 110);
		panel.setBorder(new TitledBorder("지우개"));
		eraserbtn = new Eraserbtn();
		eraserbtn.setBounds(10, 25, 130, 30);
		eraserbtn.setBrush(brush);
		panel.add(eraserbtn);
		makeClearButton();
		frame.getContentPane().add(panel);
	}

	private void makeClearButton() {
		drawClearButton();
		ClearButtonEvent();
	}

	private void drawClearButton() { // 전체지우기 버튼을 프레임에 추가하는 메서드
		clearbtn = new JButton("전체 지우기");
		clearbtn.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		clearbtn.setBounds(10, 65, 130, 30);
		panel.add(clearbtn);

	}

	private void ClearButtonEvent() { // 전체지우기 버튼 액션 메서드
		clearbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GameController.turnflag == true) {
					SendMessage.send.println("MODE:CLEAR");
					SendMessage.send.flush();
				}
			}
		});
	}

	private void drawAnswerField() { // 문제출제 필드
		answerfield = new JTextField();
		answerfield.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		Border listBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
		answerfield.setBorder(listBorder);
		answerfield.enable(false);
		answerfield.setDisabledTextColor(Color.BLACK);
		answerfield.setHorizontalAlignment(JTextField.CENTER);
		answerfield.setBounds(1045, 10, 230, 60);
		frame.getContentPane().add(answerfield);
	}

	private void drawChat() {
		drawScreen();
		drawInputText();
	}

	private void drawUserList() { // 접속중인 유저 목록을 띄울 영역
		userJPanel = new JPanel();
		userJPanel.setBounds(1045, 80, 230, 90);
		userList = new JTextArea();
		userList.setTabSize(5);
		JLabel userLabel = new JLabel("ID       /       Score");
		userLabel.setOpaque(true); // JLabel에 배경색을 설정하기 위해 사용
		userLabel.setForeground(Color.BLACK); // 
		userLabel.setBackground(SystemColor.inactiveCaption); // 활성 창 제목 표시줄의 텍스트 색
		userLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userLabel.setFont(new Font("맑은고딕", Font.BOLD, 13));
		userLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		userLabel.setBounds(0, 0, 230, 25);
		JScrollPane scrol2 = new JScrollPane(userList);
		userJPanel.setLayout(null);
		userJPanel.add(userLabel);
		scrol2.setBounds(0, 25, 230, 65);
		userList.setEnabled(false);
		Border screenborder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
		userList.setDisabledTextColor(Color.BLACK);
		userList.setFont(userList.getFont().deriveFont(10));
		userList.setBorder(screenborder);
		userList.setCaretPosition(userList.getDocument().getLength());

		userJPanel.add(scrol2);
		frame.getContentPane().add(userJPanel);
	}

	private void drawScreen() { // 대화내용을 띄울 대화창
		screen = new JTextArea(); // JTextArea 로 대화창을 구현
		screen.setBackground(new Color(253, 250, 230));
		JScrollPane scroll = new JScrollPane(screen); // 대화창에 스크롤바 생성
		scroll.setBounds(1045, 182, 230, 440); 
		Border screenborder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
		screen.setEnabled(false); // 화면 내에서는 수정할수 없도록 enable(false)
		screen.setDisabledTextColor(Color.BLACK);
		screen.setFont(screen.getFont().deriveFont(10)); // 현재의 Font 객체를 복제해 새로운 스타일을 적용
		screen.setBorder(screenborder);
		screen.setCaretPosition(screen.getDocument().getLength()); //스크롤바를 제일 아래로 위치
		frame.getContentPane().add(scroll);
	}

	private void drawInputText() { // 대화 내용 입력할 텍스트필드
		input = new JTextField(); // 대화내용을 입력해서 보낼 텍스트필드 생성
		input.setBounds(1045, 632, 167, 30);
		Border inputborder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1); // 테두리 설정
		input.setBorder(inputborder);
		input.addKeyListener(new KeyAdapter() { // 텍스트필드에 키액션 추가
			@Override
			public void keyPressed(KeyEvent e) { // 키를 눌렀을때 이벤트 발생
				if (e.getKeyCode() == KeyEvent.VK_ENTER) { // 엔터기를 눌렀다면 실행
					SendMessage.send.println("CHAT:" + input.getText()); // 서버로 메세지("CHAT:" + input.getText()) 전송
					SendMessage.send.flush(); // 버퍼를 비움
					input.setText(""); // 전송후에 텍스트 필드는 다시 reset(아무값도 없게)
				}
			}

		});
		sendbtn = new JButton("전송"); 
		sendbtn.setBounds(1215, 632, 59, 30);

		sendbtn.setFont(new Font("돋움", Font.BOLD, 10));
		sendbtn.addActionListener(new ActionListener() { //전송버튼에도 엔터를 눌렀을때와 같이 서버로 메세지 전송

			@Override
			public void actionPerformed(ActionEvent e) {
				SendMessage.send.println("CHAT:" + input.getText());
				SendMessage.send.flush();
				input.setText(""); // 서버로 전송후 텍스트필드 초기화

			}
		});

		frame.getContentPane().add(input);
		frame.getContentPane().add(sendbtn);

	}

	public Brush getBrush() {
		return this.brush;
	}

	public BufferedImage getImgbuff() {
		return this.imgbuff;
	}

	public JTextArea getScreen() {
		return this.screen;
	}

	public JTextField getAnswerField() {
		return this.answerfield;
	}

	public JTextArea getUserList() {
		return this.userList;
	}

	public void setCurrentColor(JPanel currentColor) {
		this.currentColor = currentColor;
	}

	public JPanel getCurrentColor() {
		return currentColor;
	}

	public JTextField getTimerField() {
		return TimerField;
	}
}