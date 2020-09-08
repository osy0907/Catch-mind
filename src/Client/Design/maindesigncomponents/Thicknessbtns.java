package Client.Design.maindesigncomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Client.Core.GameController;
import Client.Core.socket.SendMessage;

public class Thicknessbtns {

	private Brush brush;
	private JButton thin, nomal, thick;
	private int size;

	public void makeThicknessbtn() {
		thin = new JButton("thin");
		thin.setBounds(10, 25, 130, 30);
		thin.addActionListener(thicknessAction);
		nomal = new JButton("nomal");
		nomal.setBounds(10, 65, 130, 30);
		nomal.addActionListener(thicknessAction);
		thick = new JButton("thick");
		thick.setBounds(10, 105, 130, 30);
		thick.addActionListener(thicknessAction);
	}

	private ActionListener thicknessAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (GameController.turnflag == true) {
				thinkChange(e.getActionCommand());
				System.out.println(brush.getBrushType());
				if (brush.getBrushType().equals("brush")) {
					size *= 5;
					brush.setBrushSize(size);
				} else if (brush.getBrushType().equals("brush")) {
					brush.setBrushSize(size);
				}
				SendMessage.send.println("Think:" + size);
				SendMessage.send.flush();
			}

		}

	};

	private void thinkChange(String type) {
		if (type == "thin") {
			size = 1;
		} else if (type == "nomal") {
			size = 2;
		} else if (type == "thick") {
			size = 3;
		}
	}

	public void setBrush(Brush brush) {
		this.brush = brush;
	}

	public JButton thin() {
		return thin;
	}

	public JButton nomal() {
		return nomal;
	}

	public JButton thick() {
		return thick;
	}
}