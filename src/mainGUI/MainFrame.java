package mainGUI;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import pdfCreator.CreatorPanel;
/**
 * Main frame of the application that shows all specific panels
 * @author Patrick
 *
 */
public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 5728568798164406948L;

	private MainFrame() {
		//Frame configuration
		this.setTitle("PdfTool");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setFocusable(true);
		this.setResizable(false);
		//Adds components
		this.add(new CreatorPanel());
		this.pack();
		//Makes frame visible
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}
