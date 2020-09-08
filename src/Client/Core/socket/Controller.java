package Client.Core.socket;

import java.awt.image.BufferedImage;
import java.net.Socket;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Client.Design.maindesigncomponents.Brush;

public class Controller {

	private Socket Server; // 연결된 클라이언트와 통신을 담당하는 Socket 객체 생성
	private String ip = null;
	private int port = 0;
	private SendMessage SendThread; // SendMessage 변수 선언
	private ReceiveMessage ReceiveThread; // ReceiveMessage 변수 선언
	private Brush brush; // brush 변수 선언
	private BufferedImage imgbuff; // BufferedImage -> 이미지의 입출력을 담당하는 객체
	private JTextArea screen, userList;
	private String id;
	private JTextField answerfield, TimerField;

	private JPanel currentColor;

	public void start() { // run으로 호출 시 실행
		if (ip != null && port != 0) { // ip와 port값이 초기화값이 아닌 다른 값이라면 실행
			connectServer(); // connectServer() 메서드 호출
			
		}
	}


	public void setIP(String ip) { // 다른 클래스파일에서 private로 선언된 ip 값을 바꿀수 있도록 해주는 메소드 -> (main)에서 사용
		this.ip = ip;
	}

	public void setPort(int port) {// 다른 클래스파일에서 private로 선언된 port 값을 바꿀수 있도록 해주는 메소드 -> (main)에서 사용
		this.port = port;
	}

	private void connectServer() {
		try {
			Server = new Socket(ip, port); // 통신을 할 socket객체에입력된 ip와 port번호 매개변수로 전달
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void makeThread() {
		SendThread = new SendMessage(); // SendMessage 변수 SendThread에 객체 생성 후 참조
		ReceiveThread = new ReceiveMessage(); // ReceiveMessage 변수 ReceiveThread 객체 생성 후 참조
		SendThread.setSocket(Server); // SendMessage 객체의 setSocket을 통해 생성되어있는 socket매개변수로 전달
		SendThread.setId(id); // SendMessage 객체의 setId메서드를 통해 id 값 입력받은 id값으로 설정
		ReceiveThread.setAnswerField(answerfield); // ReceiveThread(ReceiveMessage)에 main을 통해 받아온 MainDesign의
													// answerfield을 전달하여 설정
		ReceiveThread.setSocket(Server);// ReceiveThread(ReceiveMessage)에 메인에 입력된 ip,port로 생성된 socket 전달하여 설정
		ReceiveThread.setBrush(brush); // ReceiveThread(ReceiveMessage)에 main을 통해 받아온 MainDesign의
										// brush을 전달하여 설정
		ReceiveThread.setScreen(screen); // ReceiveThread(ReceiveMessage)에 main을 통해 받아온 MainDesign의 screen을 전달하여 설정
		ReceiveThread.setImgbuff(imgbuff); // ReceiveThread(ReceiveMessage)에 main을 통해 받아온 MainDesign의 imgbuff을 전달하여 설정
		ReceiveThread.setUserList(userList); // ReceiveThread(ReceiveMessage)에 main을 통해 받아온 MainDesign의 userList를 전달하여
												// 설정
		ReceiveThread.setTimerField(TimerField);
		ReceiveThread.setCurrentColor(currentColor);
		SendThread.start(); // SendThread의 run() 메서드 실행
		ReceiveThread.start(); // ReceiveThread의 run() 메서드 실행
	}

	public void setId(String id) {
		this.id = id;// 다른 클래스파일에서 private로 선언된 port 값을 바꿀수 있도록 해주는 메소드 -> (main)에서 사용
	}

	public void setScreen(JTextArea screen) {
		this.screen = screen; // main을 통해 MainDesign의 screen의 값을 전달 받아서 screen에 대입
	}

	public void setBrush(Brush brush) {
		this.brush = brush; // main을 통해 MainDesign의 brush의 값을 전달 받아서 brush에 대입
	}

	public void setImgbuff(BufferedImage imgbuff) {
		this.imgbuff = imgbuff; // main을 통해 MainDesign의 imgbuff의 값을 전달 받아서 imgbuff에 대입
		makeThread();
	}

	public void setAnswerField(JTextField answerfield) {
		this.answerfield = answerfield; // main을 통해 MainDesign의 AnswerField의 값을 전달 받아서 answerfield에 대입
	}

	public void setUserList(JTextArea userList) {
		this.userList = userList; // main을 통해 MainDesign의 userList의 값을 전달 받아서 userList에 대입
	}

	public JPanel getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(JPanel currentColor) {
		this.currentColor = currentColor;
	}

	public void setTimerField(JTextField timerField) {
		TimerField = timerField;
	}

}
