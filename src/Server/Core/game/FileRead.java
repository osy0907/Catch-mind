package Server.Core.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileRead { /// 정답을 읽어오는 클래스

	private File file;//파일을 저장하는 클래스
	private final String dir = "src" + File.separator + "Server" + File.separator + "Core" + File.separator + "Game"
			+ File.separator + "answer.txt"; //정답의 경로를 dir변수에 저장
	private ArrayList<String> list; // 정답을 리스트에 저장

	public void read() { // 이 메소드가 실행되면 파일을 읽어서 리스트에 저장 
		makeList();
		readstart();
	}

	private void makeList() {
		list = new ArrayList<String>();
	}

	private void readstart() {
		try {
			file = new File(dir);

			BufferedReader bufReader = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = bufReader.readLine()) != null) {
				list.add(line);
			}
			bufReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public ArrayList<String> getAnswer() {
		return this.list;
	}
}
