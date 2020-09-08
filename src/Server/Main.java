package Server;

import Server.Core.socket.ServerController;

public class Main {
	static public void main(String[] args) {
		ServerController server = new ServerController();
		MainDesign design = new MainDesign();
		design.makeFrame();
		server.setStartButton(design.getButton());
		server.setJoinField(design.getJoinField());
		server.setScreen(design.getScreen());
		server.setPort(8888);//서버포트 8888로 설정
		server.start(); // ServerController의 start메서드 실행
	}
}