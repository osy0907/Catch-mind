package Server.Core.game;

import java.util.ArrayList;

public class Game {

	private FileRead file; // 파일리드 클래스 변수
	private static ArrayList<String> answerList; // static변수로 설정
	private String answer;

	public void start() { 
		readFile();
		saveAnswer();
	}

	private void readFile() { // 파일리드 클래스를 정의해서 정답을 읽어들임
		file = new FileRead();
		file.read(); 
	}

	private void saveAnswer() { // 읽은 파일을 static변수 정답목록에 저장
		answerList = file.getAnswer();
	}

	public void print() { // 정답을 순서대로 출력하는 메소드 // 여기서 실행 시키지 않음
		for (int i = 0; i < answerList.size(); i++) {
			System.out.println(answerList.get(i));
		}
	}

	public boolean hasMoreAnswer() { // 정답리스트에서 정답을 랜덤으로 추출하고 지우는 메소드 // 여기서 실행시키지 않음//GameloopThread에서 실행
		if (answerList.size() != 0) {//정답이있다면 인덱스로 랜덤으로 값을 가지고 오고 해당되는 것은 리스트에서 지운다.
			int index = (int) (Math.random() * answerList.size());  
			answer = answerList.get(index);
			answerList.remove(index);
			return true;
		} else
			return false;
	}

	public String getAnswer() {
		return this.answer;
	}
}
