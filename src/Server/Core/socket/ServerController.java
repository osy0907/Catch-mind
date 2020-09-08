package Server.Core.socket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Server.Core.User;
import Server.Core.game.GameController;
import Server.Core.game.GameloopThread;

public class ServerController {

	private int port = 0;
	private ServerSocket Server;
	private Socket Client;
	private User user;
	public static ArrayList<User> List;
	private JTextArea screen;
	private JTextField join;
	private JButton startbtn;
	private static String lastColor, lastThink, LastLine; // 마지막 펜 두께와 색을 저장해둔다.

	public void start() {
		if (port != 0) { //포트번호 0이 아니면
			List = new ArrayList<User>(); // 유저객체를 담을 어레이리스트 생성
			makeServerSocket();// 포트번호 8888인 서버소켓 생성
			makeClientSocket();//서버소켓에서 반환하는 소켓객체를 담을 소켓 생성
			StartEvent(); // 스타트 버튼 클릭시 발생하는 이벤트 처리에 대한 내용 
			acceptClient(); // 서버소켓에서 접근 기다리다가 유저가 접근하면 소켓객체 반환 후 유저객체 저장하고 서버스레드실행
		} else
			System.out.println("set server port");
	}

	public void setPort(int port) {
		this.port = port;
	}

	private void makeServerSocket() {
		try {
			Server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void makeClientSocket() {
		Client = new Socket();
	}

	private void acceptClient() {
		while (true) {
			try {
				Client = Server.accept();
				ServerThread th = new ServerThread();
				user = new User();
				user.setSocket(Client);
				user.makeWriter();
				th.setUser(user);
				th.setJoinField(join);
				th.setScreen(screen);
				List.add(user);
				th.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void StartEvent(){ // StartButton 클릭시 작동하는 이벤트 처리
		startbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkStart()) {
					screen.append("[ERROR] 최소 두명 이상 접속 시 게임 시작이 가능합니다.\n");
					screen.setCaretPosition(screen.getDocument().getLength()); // 스크린창의 스크롤을 내린다.
				} else {
					gamestart();
				}
			}
		});
	}

	private boolean checkStart() {
		if (List.size() > 1)
			return false;
		else
			return true;
	}

	private void gamestart() {
		screen.append("[SERVER] Start the game.\n");
		screen.setCaretPosition(screen.getDocument().getLength());
		GameloopThread game = new GameloopThread();
		game.setScreen(screen);        
		if (GameController.gameflag == false) { // 게임 시작이 안되었을때만 게임시작 가능하게 함
			game.start();
			GameController.gameflag = true;
		} else {
			JOptionPane.showMessageDialog(null, "게임이 시작되었습니다.");
		}

	}

	public void setScreen(JTextArea screen) {
		this.screen = screen;
	}

	public void setJoinField(JTextField join) {
		this.join = join;
	}

	public void setStartButton(JButton startbtn) {
		this.startbtn = startbtn;
	}

	public static String getLastColor() {
		return lastColor;
	}

	public static void setLastColor(String lastColor) {
		ServerController.lastColor = lastColor;
	}

	public static String getLastThink() {
		return lastThink;
	}

	public static void setLastThink(String lastThink) {
		ServerController.lastThink = lastThink;
	}

	public static String getLastLine() {
		return LastLine;
	}

	public static void setLastLine(String lastLine) {
		LastLine = lastLine;
	}

}
