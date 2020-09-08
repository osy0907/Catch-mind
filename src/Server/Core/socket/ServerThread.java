package Server.Core.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import Server.Core.User;
import Server.Core.game.GameController;

public class ServerThread extends Thread {

	private User user;
	private Socket Client;
	private BufferedReader userin;
	private String msg;
	private String ID;
	private JTextArea screen;
	private JTextField join;

	public void run() {
		super.run();
		setClient();
		makeUserInBuffer();
		joinchat();
		waitMsg();
	}

	public void setUser(User user) {
		this.user = user;
	}

	private void makeUserInBuffer() {
		try {
			userin = new BufferedReader(new InputStreamReader(Client.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void waitMsg() {
		while (true) {
			try {
				msg = userin.readLine();// 유저가 보낸 스트림을 입력 받아 한줄씩 저장
				if (msg.contains("CHAT:")) {// 메시지에 chat: 이 포함되어 있으면
					msg += " "; // 아무것도 입력하지않고 엔터눌렀을시 멈춤방지
					String[] pars = msg.split(":"); // : 로 구분하여 배열에 저장
					System.out.println(pars[1]); // chat 다음 메시지 출력
					if (pars[1].equals(GameController.answer + " ") && GameController.answerflag == false
							&& (!(this.ID).equals(GameController.ID))) {
                        //만약 게임컨트롤러의 정답과 같다면 뒤쪽에 다른 조건들은 추가적인 유효성 검사
						GameController.answerflag = true; // 정답처리맞춤으로 표시
						GameController.rightAnswer(ID); // 정답자 
						for (int i = 0; i < ServerController.List.size(); i++) {
							if (ServerController.List.get(i).getUserID().equals(this.ID)) {//현재 저장되어있는 변수 ID와 같다면
								ServerController.List.get(i).setScore(ServerController.List.get(i).getScore() + 1);// 점수추가
							}
						}
					}

					if (pars[0].equals("CHAT")) { // 입력받은 스트림이 채팅일때
						pars[1] += " ";
						msg = "CHAT:" + "[" + ID + "] " + pars[1];
					}

				}
        

				if (msg.contains("Color:") || msg.contains("Think:") || msg.contains("Line:")) {
					String[] pars = msg.split(":");
					if (msg.contains("Color:")) {
						ServerController.setLastColor(pars[1]);
					} else if (msg.contains("Think:")) {
						ServerController.setLastThink(pars[1]);
					} else if (msg.contains("Line:")) {
						ServerController.setLastLine(pars[1]);
					}
				}

				allUserSendMsg();

			} catch (IOException e) {
				msg = "CHAT:" + ID + " out the room.";
				allUserSendMsg();
				// Client.close();
				ServerController.List.remove(user);
				JoinFieldUpdate();
				userListText();
				break;
			}
		}
}

	private void joinchat() {
		try {
			ID = userin.readLine();
			user.setUserID(ID);
			JoinFieldUpdate();
			screen.append(ID + " join the room\n");
			userListText();// 현재 접속하는 유저 모두에게 유저목록과 점수를 String형으로 데이터 값을 넘겨준다.
		} catch (IOException e) {

		}

		for (int i = 0; i < ServerController.List.size(); i++) {
			ServerController.List.get(i).sendMessage("JOIN:" + ID);
			if (ServerController.List.size() > 1) {
				ServerController.List.get(i).sendMessage("Color:" + ServerController.getLastColor());
				ServerController.List.get(i).sendMessage("Think:" + ServerController.getLastThink());
				ServerController.List.get(i).sendMessage("Line:" + ServerController.getLastLine());
			} else {
				ServerController.setLastColor("BLACK");
				ServerController.setLastThink("10");
				ServerController.setLastLine("brush");

			}
		}
	}

	private void userListText() { // 유저목록에 띄울 아이디 전송
		String str = new String();
		for (int i = 0; i < ServerController.List.size(); i++) { // 유저목록의 사이즈 만큼 반복문 실행
			str += ServerController.List.get(i).getUserID() + "  \t  " + ServerController.List.get(i).getScore()
					+ " , "; // String str에 유저 목록에 있는 순서대로 "아이디 점수 ," 형식으로 계속 추가
		}
		GameController.allUserMsg("READY:" + str); 
		//GameController.allUserMsg 메서드를 사용해  모든 유저에게  메세지 전송

	}

	private void allUserSendMsg() {
		for (int i = 0; i < ServerController.List.size(); i++) {
			ServerController.List.get(i).sendMessage(msg);
		}
		if (msg.contains("CHAT:")) {
			String pars[] = msg.split(":");
			screen.append(pars[1] + "\n");
			screen.setCaretPosition(screen.getDocument().getLength());
		}
	}

	private void JoinFieldUpdate() {
		String str = new String();
		str = "현재 접속 유저  : ";
		for (int i = 0; i < ServerController.List.size(); i++) {
			str += ServerController.List.get(i).getUserID() + " ";
		}
		join.setText(str);
	}

	private void setClient() {
		this.Client = user.getClient();
	}

	public void setScreen(JTextArea screen) {
		this.screen = screen;
	}

	public void setJoinField(JTextField join) {
		this.join = join;
	}

}
