package Client.Design.maindesigncomponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Client.Core.GameController;
import Client.Core.socket.SendMessage;

public class Eraserbtn extends JButton {

	private Brush brush;

	public Eraserbtn() {
		super("지우개");
		setBounds(10, 295, 125, 30);
		setFont(new Font("맑은 고딕", Font.PLAIN, 14));
	}

	private void makeEvent() {
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GameController.turnflag == true) {
					SendMessage.send.println("Color:WHITE");
					SendMessage.send.flush();
					brush.setColor(Color.WHITE);
				}
			}
		});
	}

	public void setBrush(Brush brush) {
		this.brush = brush;
		makeEvent();
	}
}