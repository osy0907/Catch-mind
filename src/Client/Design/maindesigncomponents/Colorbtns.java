package Client.Design.maindesigncomponents;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Client.Core.GameController;
import Client.Core.socket.SendMessage;

public class Colorbtns {

	private Brush brush;
	static JPanel selectColor = new JPanel(); // 색들을 선택하기위한 JPanel. colorButton을 담고있다.
	private Color color = new Color(0, 0, 0); // color 선택할 색깔을 담을 변수이다. 검은색으로 초기화 시켜놨다.
	private JPanel currentColor; // 현재색을 나타내기 위한 JPanel이다.

	private JButton[][] colorButton = new JButton[5][5]; // 색깔들 지정을 위한 버튼이다. 위치지정을 편하게 하기위해 2차원배열로 선언하엿다.


	public void makebutton() {

		currentColor.setBackground(Color.BLACK); // 시작색은 검정이니 현재색은 검정색으로 설정한다.
		selectColor.setLayout(null); // 버튼들의 크기와 위치를 지정해주기위해 Layout 은 null
		selectColor.setBounds(10, 25, 130, 140);

		colorButton handler = new colorButton(); // colorButton 에 Mouse Event 설정을 위한 객체 생성

		for (int i = 0; i < colorButton.length; i++) { // 버튼들의 설정을 위한 이중 for 문
			for (int j = 0; j < colorButton[i].length; j++) {
				colorButton[i][j] = new JButton(); // 버튼의 각 원소를 초기화해준다.
				colorButton[i][j].setBounds((j * 27), (i * 25), 22, 22); // 버튼의 위치를 잡아준다. 버튼 크기는 20x10
				colorButton[i][j].addActionListener(handler); // 버튼 각원소를 ActionListener에 handler 추가
				selectColor.add(colorButton[i][j]); // 버튼을 select 에 담는다.
			}
		}

		/* 검은색 열 부분 색상지정 */
		colorButton[0][0].setBackground(Color.BLACK);
		colorButton[1][0].setBackground(new Color(81, 81, 81));
		colorButton[2][0].setBackground(new Color(130, 130, 130));
		colorButton[3][0].setBackground(new Color(192, 192, 192));
		colorButton[4][0].setBackground(new Color(230, 230, 230));
		/* 빨간색 열 부분 색상지정 */
		colorButton[0][1].setBackground(new Color(130, 0, 0));
		colorButton[1][1].setBackground(new Color(204, 0, 0));
		colorButton[2][1].setBackground(new Color(255, 0, 0));
		colorButton[3][1].setBackground(new Color(255, 64, 64));
		colorButton[4][1].setBackground(new Color(244, 155, 155));
		/* 블루 열 부분 색상지정 */
		colorButton[0][2].setBackground(new Color(0, 0, 153));
		colorButton[1][2].setBackground(new Color(0, 0, 204));
		colorButton[2][2].setBackground(new Color(0, 0, 255));
		colorButton[3][2].setBackground(new Color(51, 51, 255));
		colorButton[4][2].setBackground(new Color(102, 102, 255));
		/* 그린 열 부분 색상지정 */
		colorButton[0][3].setBackground(new Color(163, 163, 0));
		colorButton[1][3].setBackground(new Color(204, 204, 0));
		colorButton[2][3].setBackground(new Color(255, 255, 0));
		colorButton[3][3].setBackground(new Color(255, 255, 64));
		colorButton[4][3].setBackground(new Color(255, 255, 144));
		/* 노랑 열 부분 색상지정 */
		colorButton[0][4].setBackground(new Color(0, 104, 0));
		colorButton[1][4].setBackground(new Color(0, 130, 0));
		colorButton[2][4].setBackground(new Color(0, 204, 0));
		colorButton[3][4].setBackground(new Color(64, 255, 64));
		colorButton[4][4].setBackground(new Color(144, 255, 144));

	}
	class colorButton implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			JButton action = (JButton) e.getSource();
			//검은색 열 부분의 액션
			if (action == colorButton[0][0]) {
				if (GameController.turnflag == true) {
					SendMessage.send.println("Color:BLACK");
					SendMessage.send.flush();
					brush.setColor(Color.BLACK);
					currentColor.setBackground(brush.color);
				}
			}

			else if (action == colorButton[1][0]) {
				if (GameController.turnflag == true) {
					SendMessage.send.println("Color:AUTOGRAYDEEP");
					SendMessage.send.flush();
					brush.setColor(new Color(81, 81, 81));
					currentColor.setBackground(brush.color);
				}
			}

			else if (action == colorButton[2][0]) {
				if (GameController.turnflag == true) {
					SendMessage.send.println("Color:GRAY");
					SendMessage.send.flush();
					brush.setColor(new Color(130, 130, 130));
					currentColor.setBackground(brush.color);
				}
			}

			else if (action == colorButton[3][0]) {
				if (GameController.turnflag == true) {
					SendMessage.send.println("Color:SILVER");
					SendMessage.send.flush();
					brush.setColor(new Color(192, 192, 192));
					currentColor.setBackground(brush.color);
				}
			}

			else if (action == colorButton[4][0]) {
				if (GameController.turnflag == true) {
					SendMessage.send.println("Color:BRIGHTSILVER");
					SendMessage.send.flush();
					brush.setColor(new Color(230, 230, 230));
					currentColor.setBackground(brush.color);
				}
			}
			/*---빨간색 열 부분의 액션이 발생했을 때 currentColor색과 변수에color에 색을 지정---*/
			else if (action == colorButton[0][1]) {
				if (GameController.turnflag == true) {
					brush.setColor(new Color(104, 0, 0));
					currentColor.setBackground(brush.color);
					SendMessage.send.println("Color:DARKRED");
					SendMessage.send.flush();
				}
			}

			else if (action == colorButton[1][1]) {
				if (GameController.turnflag == true) {
					brush.setColor(new Color(204, 0, 0));
					currentColor.setBackground(brush.color);
					SendMessage.send.println("Color:REDDEEP");
					SendMessage.send.flush();
				}
			}

			else if (action == colorButton[2][1]) {
				if (GameController.turnflag == true) {
					brush.setColor(new Color(255, 0, 0));
					currentColor.setBackground(brush.color);
					SendMessage.send.println("Color:RED");
					SendMessage.send.flush();
				}
			}

			else if (action == colorButton[3][1]) {
				if (GameController.turnflag == true) {
					brush.setColor(new Color(255, 64, 64));
					currentColor.setBackground(brush.color);
					SendMessage.send.println("Color:BRIGHTRED");
					SendMessage.send.flush();
				}
			}

			else if (action == colorButton[4][1]) {
				if (GameController.turnflag == true) {
					brush.setColor(new Color(244, 155, 155));
					currentColor.setBackground(brush.color);
					SendMessage.send.println("Color:BRIGHTRED2");
					SendMessage.send.flush();
				}
			}

			//파란색 열 부분의 액션
			else if (action == colorButton[0][2]) {
				if (GameController.turnflag == true) {
					brush.setColor(new Color(0, 0, 153));
					currentColor.setBackground(brush.color);
					SendMessage.send.println("Color:DEEPDARKBLUE");
					SendMessage.send.flush();
				}
			}

			else if (action == colorButton[1][2]) {
				if (GameController.turnflag == true) {
					brush.setColor(new Color(0, 0, 204));
					currentColor.setBackground(brush.color);
					SendMessage.send.println("Color:DEEPBLUE");
					SendMessage.send.flush();
				}
			}

			else if (action == colorButton[2][2]) {
				if (GameController.turnflag == true) {
					brush.setColor(new Color(0, 0, 255));
					currentColor.setBackground(brush.color);
					SendMessage.send.println("Color:BLUE");
					SendMessage.send.flush();
				}
			}

			else if (action == colorButton[3][2]) {
				if (GameController.turnflag == true) {
					brush.setColor(new Color(51, 51, 255));
					currentColor.setBackground(brush.color);
					SendMessage.send.println("Color:BRIGHTBLUE");
					SendMessage.send.flush();
				}
			}

			else if (action == colorButton[4][2]) {
				if (GameController.turnflag == true) {
					brush.setColor(new Color(102, 102, 255));
					currentColor.setBackground(brush.color);
					SendMessage.send.println("Color:BRIGHTBLUE2");
					SendMessage.send.flush();
				}
			}

			//노란색 열 부분의 액션
			else if (action == colorButton[0][3]) {
				if (GameController.turnflag == true) {
					brush.setColor(new Color(163, 163, 0));
					currentColor.setBackground(brush.color);
					SendMessage.send.println("Color:DEEPDARKYELLOW");
					SendMessage.send.flush();
				}
			}

			else if (action == colorButton[1][3]) {
				if (GameController.turnflag == true) {
					brush.setColor(new Color(204, 204, 0));
					currentColor.setBackground(brush.color);
					SendMessage.send.println("Color:DEEPYELLOW");
					SendMessage.send.flush();
				}
			}

			else if (action == colorButton[2][3]) {
				if (GameController.turnflag == true) {
					SendMessage.send.println("Color:YELLOW");
					SendMessage.send.flush();
					brush.setColor(new Color(255, 255, 0));
					currentColor.setBackground(brush.color);
				}
			}

			else if (action == colorButton[3][3]) {
				if (GameController.turnflag == true) {
					SendMessage.send.println("Color:BRIGHTYELLOW");
					SendMessage.send.flush();
					brush.setColor(new Color(255, 255, 64));
					currentColor.setBackground(brush.color);
				}
			}

			else if (action == colorButton[4][3]) {
				if (GameController.turnflag == true) {
					SendMessage.send.println("Color:BRIGHTYELLOW2");
					SendMessage.send.flush();
					brush.setColor(new Color(255, 255, 144));
					currentColor.setBackground(brush.color);
				}
			}

			//초록색 열 부분의 액션
			else if (action == colorButton[0][4]) {
				if (GameController.turnflag == true) {
					SendMessage.send.println("Color:DEEPDARKGREEN");
					SendMessage.send.flush();
					brush.setColor(new Color(0, 104, 0));
					currentColor.setBackground(brush.color);
				}
			}

			else if (action == colorButton[1][4]) {
				if (GameController.turnflag == true) {
					SendMessage.send.println("Color:DEEPGREEN");
					SendMessage.send.flush();
					brush.setColor(new Color(0, 130, 0));
					currentColor.setBackground(brush.color);
				}
			}

			else if (action == colorButton[2][4]) {
				if (GameController.turnflag == true) {
					SendMessage.send.println("Color:GREEN");
					SendMessage.send.flush();
					brush.setColor(new Color(0, 204, 0));
					currentColor.setBackground(brush.color);
				}
			}

			else if (action == colorButton[3][4]) {
				if (GameController.turnflag == true) {
					SendMessage.send.println("Color:BRIGHTGREEN");
					SendMessage.send.flush();
					brush.setColor(new Color(64, 255, 64));
					currentColor.setBackground(brush.color);
				}
			}

			else if (action == colorButton[4][4]) {
				if (GameController.turnflag == true) {
					SendMessage.send.println("Color:BRIGHTGREEN2");
					SendMessage.send.flush();
					brush.setColor(new Color(144, 255, 144));
					currentColor.setBackground(brush.color);
				}
			}

			else {
			}
		}

	}

	public JPanel getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(JPanel currentColor) {
		this.currentColor = currentColor;
	}

	public Brush getBrush() {
		return brush;
	}

	public void setBrush(Brush brush) {
		this.brush = brush;
	}

	public JPanel getSelectColor() {
		return selectColor;
	}

}