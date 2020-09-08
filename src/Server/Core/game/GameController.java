package Server.Core.game;

import Server.Core.socket.ServerController;

public class GameController {
	static public String answer;
	static public boolean answerflag = false;
	static public boolean gameflag = false;
	static public String ID;
	static public String Winner;
	static public int temp;

	static public void firstStart() {
		for (int i = 0; i < ServerController.List.size(); i++) {
			ServerController.List.get(i).setScore(0);
			Winner = "";
			temp = 0;
			ServerController.List.get(i).sendMessage("CHAT:[SERVER] " + "게임을 시작합니다.");
			ServerController.List.get(i).sendMessage("SET:FALSE");
			ServerController.List.get(i).sendMessage("MODE:CLEAR");

		}
	}

	static public void allUserPermissionFalse() {
		for (int i = 0; i < ServerController.List.size(); i++) {
			ServerController.List.get(i).sendMessage("SET:FALSE");
			ServerController.List.get(i).sendMessage("MODE:CLEAR");
		}
	}

	static public void rightAnswer(String id) {//정답을 맞추면 모든사용자에게 보내는 메세지
		answerflag = true; //정답플레그를 true
		answer = "aaaaaaaaaaa";
		for (int i = 0; i < ServerController.List.size(); i++) {
			ServerController.List.get(i).sendMessage("CHAT:[알림] " + id + " 님이 맞추셨습니다.");
		}
	}

	static public void allUserMsg(String msg) {
		for (int i = 0; i < ServerController.List.size(); i++) {
			ServerController.List.get(i).sendMessage(msg);
		}
	}

	static public void gameRank() {
		temp = ServerController.List.get(0).getScore();// 첫 번째 유저 점수 저장
		for (int i = 1; i < ServerController.List.size(); i++) {// 유저 모두 비교
			temp = temp > ServerController.List.get(i).getScore() ? temp : ServerController.List.get(i).getScore();

		}
		for (int i = 0; i < ServerController.List.size(); i++) {
			if (temp == ServerController.List.get(i).getScore()) {// 가장 높은 점수인 temp과 유저 점수가 같으면
				Winner += ServerController.List.get(i).getUserID() + " ";// 해당 점수를 가진 유저의 아이디 가져옴
			}
		}
		allUserMsg("Winner:" + GameController.Winner);
        // Winner: id 와 같은 형태로 모든 유저에게 메시지 보냄 (우승자가 누군지
		// SHOWMESSAGEDIALOG형태로 알려줌)
        
		allUserMsg("CHAT:[알림] 게임이 끝났습니다.");
		allUserMsg("CHAT:[알림] " + GameController.Winner + " 님이 우승하셨습니다.");
	}

}
