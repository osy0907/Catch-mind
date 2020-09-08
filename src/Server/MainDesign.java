package Server;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Server.Core.socket.ServerController;

public class MainDesign {

	private JFrame frame;
	private JButton startbtn;
	private JTextArea screen;

	private JTextField inputfield, joinfield;
	private JScrollPane scroll;


	public void makeFrame() {
		drawFrame();
		drawStartButton();
		drawJoinField();
		drawScreen();
		drawInputField();
		frame.repaint();
	}

	private void drawFrame() {
		frame = new JFrame("캐치캐치");
		frame.setResizable(false);
		frame.setSize(514, 441);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

	}

	private void drawStartButton() {
		startbtn = new JButton("게임 시작");
		startbtn.setBounds(10, 10, 480, 50);
		frame.getContentPane().add(startbtn);
	}

	private void drawJoinField() {
		joinfield = new JTextField();
		joinfield.setBounds(10, 70, 480, 30);
		joinfield.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		joinfield.setDisabledTextColor(Color.BLACK);
		joinfield.setEnabled(false);
		frame.getContentPane().add(joinfield);
	}

	private void drawScreen() {
		screen = new JTextArea();
		screen.setBounds(10, 110, 480, 250);
		screen.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		screen.setEnabled(false);
		screen.setDisabledTextColor(Color.BLACK);
		scroll = new JScrollPane(screen, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(10, 110, 480, 250);
		screen.setCaretPosition(screen.getDocument().getLength());
		frame.getContentPane().add(scroll);
	}

	private void drawInputField() {
		inputfield = new JTextField();
		inputfield.setBounds(10, 363, 480, 30);
		inputfield.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		frame.getContentPane().add(inputfield);
		inputfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					screen.append("[SERVER] " + inputfield.getText() + "\n");
					screen.setCaretPosition(screen.getDocument().getLength());
					for (int i = 0; i < ServerController.List.size(); i++) {
						ServerController.List.get(i).sendMessage("CHAT:[SERVER] " + inputfield.getText());
					}
					inputfield.setText("");
				}
			}

		});
	}

	public JTextArea getScreen() {
		return this.screen;
	}

	public JTextField getJoinField() {
		return this.joinfield;
	}

	public JButton getButton() {
		return this.startbtn;
	}
}
