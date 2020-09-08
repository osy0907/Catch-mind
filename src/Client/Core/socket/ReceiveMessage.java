package Client.Core.socket;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Client.Core.GameController;
import Client.Design.maindesigncomponents.Brush;

public class ReceiveMessage extends Thread {

	private Socket Server;
	private BufferedReader msgbuff; // 버퍼를 이용해 입력받는 BufferedReader(문자기반 보조스트림)
	private String msg;
	private Brush brush; // brush 변수 선언
	private BufferedImage imgbuff; // 버퍼를 이용한 이미지 처리
	private int x, y; // 좌표값을 설정할 변수
	private JTextArea screen, userList; // 대화창을 보여줄 JTextArea 객체
	private JTextField answerfield, TimerField; // 출제 문제를 보여줄 JTextField
	private JPanel currentColor;

	public void run() {
		makeMsgBuff(); // makeMsgBuff() 메서드 호출
		getMsg(); // getMsg() 메서드 호출
	}

	public void setSocket(Socket Server) { // Controller에서 생성된 socket으로
		this.Server = Server; // 해당 server에 대입(contrller.server == SendMessage.server)
	}

	private void makeMsgBuff() { // run()에서 호출한 메서드
		try {
			msgbuff = new BufferedReader(new InputStreamReader(Server.getInputStream()));
            //서버(소켓)으로부터 입력스트림을 얻어와서 데이터를 쉽게 읽을수있는 BufferedReader 객체를 생성
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getMsg() {
		while (true) {
			try {
				msg = msgbuff.readLine(); // 서버에서 보내온 메세지를 한줄씩 msg에 담는다.
				if (msg.contains(":")) { // msg 에 ':'이 포함되어있다면 실행
					String[] pars = msg.split(":"); // String 배열 pars에 msg를 ':'문자 기준으로 나눈 문자열을 담는다.
					if (pars[0].equals("Position")) {// Position : x,y ->좌표 값을 지정해주는 구문
						// mainDesign 에서 (GameController.turnflag == true)일때 서버로 출력후
						pars = pars[1].split(",");
						// ':' 기준으로 나눈 문자열 인덱스 1번을 ','문자 기준으로 나눠서 pars배열에 다시 담음(이전 문자배열 사라짐)
						x = Integer.parseInt(pars[0]); // ','문자 기준으로 pars[0] == x좌표값
						y = Integer.parseInt(pars[1]); // ','문자 기준으로 pars[1] == y좌표값
						brush.setX(x); // brush 객체에 setX() 메서드를 호출하여 x좌표값을 지정
						brush.setY(y);// brush 객체에 setY() 메서드를 호출하여 y좌표값을 지정
						brush.repaint(); // brush객체의 repaint()메서드를 호출하여 초기화(repaint는 기본정의된 메서드)
						brush.printAll(imgbuff.getGraphics()); // setImgbuff()로 설정한 imgbuff의 Graphics을 얻어옴
					} else if (pars[0].equals("Color")) {
						if (pars[1].equals("BLACK")) // : 로 나눈 인덱스 1번이 brack일 경우
							brush.setColor(Color.BLACK); // brush클래스 setColor(Color color) 메소드로 색을 설정
						else if (pars[1].equals("AUTOGRAYDEEP"))
							brush.setColor(new Color(81, 81, 81));
						else if (pars[1].equals("GRAY"))
							brush.setColor(new Color(130, 130, 130));
						else if (pars[1].equals("SILVER"))
							brush.setColor(new Color(192, 192, 192));
						else if (pars[1].equals("BRIGHTSILVER"))
							brush.setColor(new Color(230, 230, 230));
						else if (pars[1].equals("DARKREDBTN"))
							brush.setColor(new Color(104, 0, 0));
						else if (pars[1].equals("REDDEEP"))
							brush.setColor(new Color(204, 0, 0));
						else if (pars[1].equals("RED"))
							brush.setColor(new Color(255, 0, 0));
						else if (pars[1].equals("BRIGHTRED"))
							brush.setColor(new Color(255, 64, 64));
						else if (pars[1].equals("BRIGHTRED2"))
							brush.setColor(new Color(244, 155, 155));
						else if (pars[1].equals("DEEPDARKBLUE"))
							brush.setColor(new Color(0, 0, 153));
						else if (pars[1].equals("DEEPBLUE"))
							brush.setColor(new Color(0, 0, 204));
						else if (pars[1].equals("BLUE"))
							brush.setColor(new Color(0, 0, 255));
						else if (pars[1].equals("BRIGHTBLUE"))
							brush.setColor(new Color(51, 51, 255));
						else if (pars[1].equals("BRIGHTBLUE2"))
							brush.setColor(new Color(102, 102, 255));
						else if (pars[1].equals("DEEPDARKGREEN"))
							brush.setColor(new Color(0, 104, 0));
						else if (pars[1].equals("DEEPGREEN"))
							brush.setColor(new Color(0, 130, 0));
						else if (pars[1].equals("GREEN"))
							brush.setColor(new Color(0, 204, 0));
						else if (pars[1].equals("BRIGHTGREEN"))
							brush.setColor(new Color(64, 255, 64));
						else if (pars[1].equals("BRIGHTGREEN2"))
							brush.setColor(new Color(144, 255, 144));
						else if (pars[1].equals("DEEPDARKYELLOW"))
							brush.setColor(new Color(163, 163, 0));
						else if (pars[1].equals("DEEPYELLOW"))
							brush.setColor(new Color(204, 204, 0));
						else if (pars[1].equals("YELLOW"))
							brush.setColor(new Color(255, 255, 0));
						else if (pars[1].equals("BRIGHTYELLOW"))
							brush.setColor(new Color(255, 255, 64));
						else if (pars[1].equals("BRIGHTYELLOW2"))
							brush.setColor(new Color(255, 255, 144));
						else if (pars[1].equals("WHITE"))
							brush.setColor(Color.WHITE);
						currentColor.setBackground(brush.color);
						// mainDesign 에서 색 버튼 클릭시 버튼의 (GameController.turnflag == true)일때
					} else if (pars[0].equals("Think")) {
                        //Think(굵기)일 경우 실행
						try {
							brush.setBrushSize(Integer.parseInt(pars[1])); // brush클래스 setBrushSize로 브러쉬 크기 지정
						} catch (NumberFormatException e) {
							brush.setBrushSize(2);
						}

					} else if (pars[0].equals("Line")) {
						brush.setBrushType(pars[1]);

					} else if (pars[0].equals("CHAT")) { // mainDesign에서 drawChat() 이 호출한 drawInputText() 메서드에서
						// keyPressed 액션, 전송버튼 클릭 발생시 [(VK_ENTER) --> 엔터키를 눌렀을때 실행됨] 서버로 "CHAT:" +
						// input.getText()" 전송된 메세지, 혹은 서버쪽에서 바로 보내는 메세지를 처리
						screen.append(pars[1] + "\n"); // : 문자 기준으로 나눈 뒤의 getText(입력값)을 screen(JTextArea)에 덧붙힌다.
						screen.setCaretPosition(screen.getDocument().getLength()); // 맨 아래로 스크롤 한다.
					} else if (pars[0].equals("JOIN")) { // main(Server) -> ServerController -> acceptClient ->
						// ServerThead - joinchat() 메서드로 ("JOIN:" + ID);) 를 전달받아서 처리
						screen.append(pars[1] + " join the room.\n");
						screen.setCaretPosition(screen.getDocument().getLength()); // 맨 아래로 스크롤 한다.
					} else if (pars[0].equals("MODE")) { // 모두 지우기 버튼 클릭시 MainDesign - ClearButtonEvent() 발생하여
						if (pars[1].equals("CLEAR")) // "MODE:CLEAR"를 처리하여
							ClearScreen(); // ClearScreen() 메서드 실행
					} else if (pars[0].equals("SET")) { // (주호 중요) , 읽은 값이  
						if (pars[1].equals("FALSE")) {// 서버에서 받아와서 set : false 라면
							GameController.turnflag = false;// 턴 플레그를 FALSE로 설정한다.
							answerfield.setBackground(new Color(255, 255, 255));
							answerfield.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

						} else if (pars[1].equals("TRUE")) {// 서버에서 받아와서 set : TURE 라면
							GameController.turnflag = true;// TURNFLAG를 TURE로 설정한다.
						}
						if (pars[1].equals("ANSWERON")) { // 메세지에 : 기준으로 나눈 배열 인덱스 1번값이 ANSWERON인 경우
							answerfield.setBackground(new Color(249, 204, 204));
							answerfield.setBorder(BorderFactory.createLineBorder(new Color(249, 137, 137), 1));
						}
					} else if (pars[0].equals("ANSWER")) { // 서버에서 정답 받아옴
						answerfield.setText(pars[1]); // mainDesign - drawAnswerField 에
														// 받아온 정답을 오른쪽 화면에 출력
					} else if (pars[0].equals("READY")) {
						pars = pars[1].split(" , ");
						userList.setText("");
						for (int i = 0; i < pars.length; i++) {
							userList.append("\t" + pars[i] + "\n");
						}
					} else if (pars[0].equals("Winner")) { // 서버에서 정답 받아옴
						JOptionPane.showMessageDialog(brush.getParent(), "게임이 끝났습니다.\n" + pars[1] + " 님이 우승했습니다.");
					} else if (pars[0].equals("TIME")) { // 서버에서 정답 받아옴
						TimerField.setText(pars[1] + " : " + pars[2]);
					}
				}
			} catch (IOException e)

			{

			}
		}
	}

	private void ClearScreen() { // X표 = 전체지우기를 눌렀을때 실행 메서드
		brush.setFlag(false); // brush 객체의 flag를 false로 설정
		brush.repaint(); // brush객체의 repaint()메서드를 호출하여 초기화(repaint는 기본정의된 메서드)
		brush.printAll(imgbuff.getGraphics()); // setImgbuff()로 설정한 imgbuff의 Graphics을 얻어옴
	}

	public void setBrush(Brush brush) {
		this.brush = brush; // main-> Controller을 통해 MainDesign의 makeBrush()메서드를 통해 생성된
		// bush를 전달 받아서 bush에 대입
		// screen => main에서 생성된 mainDesign의 객체 호출한 makeBrush()메서드를 통해 생성된
		// brush가 Controller로 보내져서 ReceiveMessage(this: 현재 클래스)로 전달
		// mainDesign.screen == ReceiveMessage.screen
	}

	public void setImgbuff(BufferedImage imgbuff) {
		this.imgbuff = imgbuff; // main-> Controller을 통해 MainDesign의 imgbuff의 값을 전달 받아서 imgbuff에 대입
		// imgbuff => main에서 생성된 mainDesign의 객체에서 Controller로 보내져서 ReceiveMessage(this:
		// 현재 클래스)로 전달
		// mainDesign.imgbuff == ReceiveMessage.imgbuff
	}

	public void setScreen(JTextArea screen) {
		this.screen = screen; // main-> Controller을 통해 MainDesign의 screen의 값을 전달 받아서 screen에 대입
		// screen => main에서 생성된 mainDesign의 객체에서 Controller로 보내져서 ReceiveMessage(this:
		// 현재 클래스)로 전달
		// mainDesign.screen == ReceiveMessage.screen
	}

	public void setAnswerField(JTextField answerfield) {
		this.answerfield = answerfield; // main-> Controller을 통해 MainDesign의 screen의 값을 전달 받아서 answerfield에 대입
		// screen => main에서 생성된 mainDesign의 객체에서 Controller로 보내져서 ReceiveMessage(this:
		// 현재 클래스)로 전달
		// mainDesign.answerfield == ReceiveMessage.answerfield
	}

	public void setUserList(JTextArea userList) {
		this.userList = userList;
	}

	public JPanel getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(JPanel currentColor) {
		this.currentColor = currentColor;
	}

	public JTextField getTimerField() {
		return TimerField;
	}

	public void setTimerField(JTextField timerField) {
		TimerField = timerField;
	}
}
