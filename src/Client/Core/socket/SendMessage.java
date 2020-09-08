package Client.Core.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SendMessage extends Thread {

	private Socket Server;
	static public PrintWriter send; // PrintWriter는 reader가 없는 오직 출력을 위한 스트림
    //소켓을 통해 서버로 메세지를 보내기위한 출력 스트림
	private String id;

	public void run() {
		makeSender(); // makeSender() 메서드 호출
		sendID(); // sendID() 메서드 호출
	}

	public void setId(String id) { // 해당 클래스의 id를 controller에서 보내온 id값으로 설정
		this.id = id; // contrller.id == SendMessage.id(init에서 최초 입력된 id값이 main-> controller 를 통해 넘어옴)
	}

	public void setSocket(Socket Server) { // Controller에서 생성된 socket으로
		this.Server = Server; // 해당 server에 대입(contrller.server == SendMessage.server)

	}

	private void makeSender() { // 출력스트림을 생성할 메서드 구현
		try {
			send = new PrintWriter(Server.getOutputStream()); // 소켓을 통해 출력스트림을 얻어와 서버로 출력을 할수있도록 생성
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendID() {
		send.println(id); // id를 출력해줌
		send.flush(); // send스트림을 모두 반환 후 비움
	}
}
