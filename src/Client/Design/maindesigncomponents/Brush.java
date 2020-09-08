package Client.Design.maindesigncomponents;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

public class Brush extends JLabel {

	private int x;
	private int y;
	public boolean flag = false;
	public Color color = Color.black;
	private static int brushSize = 10;
	private static String brushType = "brush";

	@Override
	public void paint(Graphics g) {
		// paint 메서드 호출 시점 : GUI Application이 실행 될때, damage를 입은 영역을 복구할때 비활성화 되어 다른 창에
		// 가려진 활성화된 다음 다시 보여질 때

		// 1) Component.repaint() 메소드 호출 (update() 메소드 호출)
		// 2) Component.update(g) 메소드 호출 (영역을 clear 한 후 paint 메소드 호출)
		// 3) Component.paint(g) 메소드 호출
		if (flag == true) {
			g.setColor(color);
			g.fillOval(x - 2, y - 2, brushSize, brushSize * 2);
		} else if (flag == false) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 1000, 1000);
			flag = true;
		}
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public void setBrushSize(int brushSize) {
		Brush.brushSize = brushSize;
	}

	public String getBrushType() {
		return Brush.brushType;
	}

	public void setBrushType(String brushType) {
		Brush.brushType = brushType;
	}
}