package View;

public abstract class AbstFrame {
	public int width;
	public int height;
	
	public AbstFrame() {
		MainFrame m = MainFrame.getMainFrame();
		width = m.getWidth();
		height = m.getHeight();
	}
}
