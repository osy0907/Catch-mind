package Client.Design.maindesigncomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Client.Core.GameController;
import Client.Core.socket.SendMessage;

public class Penbtn {

	private Brush brush;
	private JButton penButton, brushButton;
	private int size;

	public void makePenbtn() {
		brushButton = new JButton("brush"); // Brush 버튼 생성
		penButton = new JButton("pencil"); // Pen 버튼 생성
		brushButton.setBounds(10, 30, 130, 30); // brush 버튼 좌표 설정
		brushButton.addActionListener(brushAction); // brush 버튼 ActionListener 에 brushAction 추가

		penButton.setBounds(10, 70, 130, 30); // pen 버튼 좌표 설정
		penButton.addActionListener(brushAction); // brush 버튼 ActionListener 에 handler 추가

	}

	private ActionListener brushAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (GameController.turnflag == true) {
				brushChange(e.getActionCommand()); //눌린버튼의 텍스트값을 매개변수로 brushChange()메서드 호출
				brush.setBrushSize(size);
				SendMessage.send.println("Line:" + brush.getBrushType());
				SendMessage.send.println("Think:" + size);
				SendMessage.send.flush();

			}
		}

	};

	private void brushChange(String type) {
		if (type == "brush") {
			size = 10;
			brush.setBrushType("brush");

		} else if (type == "pencil") {
			size = 2;
			brush.setBrushType("pencil");
		}
	}

	public void setBrush(Brush brush) {
		this.brush = brush;
	}

	public JButton penButton() {
		return penButton;
	}

	public JButton brushButton() {
		return brushButton;
	}

}