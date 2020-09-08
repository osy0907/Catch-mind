package Client;

import Client.Core.socket.Controller;
import Client.Design.MainDesign;
import Client.Design.initDesign;

public class Main {

	static public void main(String[] args) {
		//test
		String id = null, pw = null;
		Controller Socket = new Controller(); // Controller의 객체 Soket 생성
		MainDesign design = new MainDesign(); // MainDesign의 객체 design 생성
		initDesign init = new initDesign(); // initDesign의 객체 init 생성
		init.makeFrame(); // initDesign의 객체 init의 makeFrame()호출
		do {
			id = init.getID();
			pw = init.getPW();
			System.out.println("");
		} while (init.getLoginchk() == false); // db에 값과 확인해서 로그인 성공이 아닐 경우 계속 반복

		Socket.setIP("127.0.0.1"); // Soket의 setIP()메서드를 호출 => init에서 얻어온 ip값을 매개변수로
		Socket.setPort(8888); // Soket의 setPort()메서드를 호출 => 8888(서버에서 설정한 포트번호)
		Socket.setId(id); // Soket의 setId()메서드를 호출 => init에서 얻어온 id값을 매개변수로
		design.makeFrame(); // MainDesign의 객체 design의 makeFrame() 호출

		Socket.start(); // socket의 쓰레드 실행(run()호출)
		Socket.setCurrentColor(design.getCurrentColor());//socket setCurrentColor()을 호출 -> design의 CurrentColor의 값을 얻어와 매개변수로
		Socket.setAnswerField(design.getAnswerField()); // socket setAnswerField()을 호출 -> design의 AnswerField의 값을 얻어와
                                                        // 매개변수로
		Socket.setTimerField(design.getTimerField()); // socket setTimerField()을 호출 -> design의 TimerField의 값을 얻어와 매개변수로
		Socket.setScreen(design.getScreen()); // socket setScreen()을 호출 -> design의 Screen의 값을 얻어와 매개변수로
		Socket.setBrush(design.getBrush()); // socket setBrush()을 호출 -> design의 Brush의 값을 얻어와 매개변수로
		Socket.setUserList(design.getUserList());// socket setUserList()을 호출 -> design의 UserList의 값을 얻어와 매개변수로
		Socket.setImgbuff(design.getImgbuff()); // socket setImgbuff()을 호출 -> design의 Imgbuff의 값을 얻어와 매개변수로

	}
}
