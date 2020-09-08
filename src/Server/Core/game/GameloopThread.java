package Server.Core.game;

import javax.swing.JTextArea;

import Server.Core.socket.ServerController;

class TimeloopThread extends Thread {
	static boolean timeChk = false;

	@Override
	public void run() {
		String Time;
		for (int i = 90; i >= 0; i--) {// i 초깃값이 게임중 턴에대한 시간제한(초단위)
			try {
				Time = i / 60 + ":" + i % 60;// 분과 초 를 나타냄
				System.out.println("CHAT:[알림] 턴 종료 " + i + "초 전");
				GameController.allUserMsg("TIME:" + Time);
				if (i == 0) {
					timeChk = true;// 시간 카운트 다운이 끝났을때 timeChk true로 바꿈
				} else {
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class GameloopThread extends Thread {

	private JTextArea screen;

	@Override
	public void run() {
		Game game = new Game();// 게임 객체를 생성하고
		String answer, Userstr;
		int index = 0;
		TimeloopThread time;
		game.start();//// 게임스타트 메소드를 실행시킨다. 이게 실행되면 정답을 읽어들인다.리스트도 저장한다.
		GameController.firstStart(); // 처음 게임 시작하면 점수 , 승자 초기화
		for (int i = 3; i > 0; i--) { // 3초뒤 시작
			try {
				screen.append("CHAT:[알림] 게임시작 " + i + "초 전\n");
				GameController.allUserMsg("CHAT:[알림] 게임시작 " + i + "초 전");
				screen.setCaretPosition(screen.getDocument().getLength());// 스크롤을 제일 아래로
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		while (game.hasMoreAnswer()) { // 가져올 정답이 있으면 계속 실행
			answer = game.getAnswer(); // 여기서 정답을 가져오는 것을 실행을 시킨다.(주호) answer 변수에 저장
			time = new TimeloopThread(); // 문제 시간제한 카운드다운 할 스레드 객체 생성
			time.timeChk = false; // 시간이 끝나지 않음을 의미
			time.start(); // // 타임루프 스레드 실행
			GameController.answer = answer;// 게임컨트롤러에 있는 answer변수에 저장하여 정답을 비교
			System.out.println(answer);// 정답을 출력
			GameController.gameflag = true;// 게임을 시작했는지 여부
			GameController.answerflag = false; // 정답 flag를 false로 설정
			GameController.ID = ServerController.List.get(index).getUserID();// 서버콘트롤러에 있는유저들 중 0번을 게임 컨트롤러 id에 저장
			GameController.allUserMsg("CHAT:[알림] " + GameController.ID + " user turn.");// 모든 유저에게 턴을 알려줌(주호)
			screen.append("[알림] " + GameController.ID + " user turn.\n");// 서버 채팅창에 턴을 알려줌
			screen.setCaretPosition(screen.getDocument().getLength());// 스크롤을 제일 아래로
			ServerController.List.get(index).sendMessage("SET:TRUE");// 턴을 넘겨준다.이것으로 출제자를 정함(주호중요)
			ServerController.List.get(index).sendMessage("SET:ANSWERON");//
			ServerController.List.get(index).sendMessage("CHAT:[알림] " + "당신차례입니다.");
			ServerController.List.get(index).sendMessage("CHAT:[알림] " + "정답은  " + answer + " 입니다.");
			ServerController.List.get(index).sendMessage("CHAT:[알림] " + "정답을 잘 설명해보세요!!!");
			ServerController.List.get(index).sendMessage("ANSWER:" + answer);
			while (true) {// 무한 반복문 정답이라면 빠저나오고 타임이 다 되었다면
				if (GameController.answerflag == true) {
					// 정답이 나오면 ( 정답 검사는 ServerThread의 waitMsg에서 판단 후 점수 더함)

					Userstr = new String();
					for (int j = 0; j < ServerController.List.size(); j++) {
						Userstr += ServerController.List.get(j).getUserID() + "\t"
								+ ServerController.List.get(j).getScore() + " , ";
					}
					// 유저 아이디와 점수목록을 가져옴

					GameController.allUserMsg("READY:" + Userstr);
					// 모든 유저에게 다시 모두 뿌림

					time.stop();
					// 시간 카운트다운 멈춤
					break;
					// 무한루프 나감
				} else {
					try {
						if (time.timeChk == true) {// 시간 카운트제한이 끝났을때 무한루프 끝냄
							GameController.allUserMsg("CHAT:[알림] Timeover!!");
							GameController.allUserMsg("CHAT:[알림] 정답은 " + GameController.answer + " 입니다!!!");
							screen.append("CHAT:[알림] Timeover!!\n"); // 서버쪽 채팅창에 시간초과 메세지 띄움
							screen.setCaretPosition(screen.getDocument().getLength());// 스크롤을 제일 아래로
							break;
						} else {
							Thread.sleep(1000);
						}
					} catch (InterruptedException e) {

					}
				}
			}
			// 정답이고 타임이 다 되었다면 빠저나오고
			ServerController.List.get(index).sendMessage("ANSWER:" + " ");
			GameController.allUserPermissionFalse(); // 다시 모든 유저의 턴과 화면을 초기화 시킨다.아래의 메세지를 보내는 메소드이다.
			// 모든유저에게 보낸다.

			++index;// 다음 사람
			if (index == ServerController.List.size()) {// 다음사람이 유저 크기와 같으면
				index = 0;// 다시 처음으로
			}
		}
		// while(game.hasMoreAnswer()) 정답목록을 모두 소진했을 시

		GameController.gameRank();
		screen.append("[알림] 게임이 끝났습니다.\n");// 문제가 모두 출제 후 게임 끝나면 점수 계산
		screen.append("[알림] " + GameController.Winner + "님이 우승하셨습니다.\n");// 서버창에 메시지 출력
		screen.setCaretPosition(screen.getDocument().getLength()); // 스크롤을 제일 아래로

	}

	public void setScreen(JTextArea screen) {
		this.screen = screen;
	}
}