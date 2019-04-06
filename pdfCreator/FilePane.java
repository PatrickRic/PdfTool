package pdfCreator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Pane that displays the information for a given file and enables user-made
 * changes, if it is an image-file
 * 
 * @author Patrick
 *
 */
public class FilePane extends JScrollPane {

	private static final long serialVersionUID = 2476483939677250379L;
	/*
	 * Path of the displayed file
	 */
	private String path;
	/*
	 * Original width of image, if it is an image
	 */
	private int img_orgWidth;
	/*
	 * Original height of image, if it is an image
	 */
	private int img_orgHeight;
	/**
	 * Width of image, if it is an image
	 */
	protected float img_width;
	/**
	 * Height of image, if it is an image
	 */
	protected float img_height;
	/*
	 * Width of pdf page (A2)
	 */
	private final int PAGE_WIDTH = 595;
	/*
	 * Height of pdf page (A2)
	 */
	private final int PAGE_HEIGHT = 842;

	/**
	 * Creates FilePane for file at the given path
	 * 
	 * @param path
	 */
	public FilePane(String path) {
		// Saves path and extension
		this.path = path;
		String filetype = path.substring(path.length() - 3);
		// Creates JPanel for all the content
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		// Creates JLabel for the path
		JLabel lbl_path = new JLabel("Dateipfad: " + path);
		lbl_path.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
		lbl_path.setAlignmentX(Component.LEFT_ALIGNMENT);
		lbl_path.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		contentPane.add(lbl_path);
		// Creates JPanel for the width of the file
		JPanel jp_width = new JPanel();
		jp_width.setAlignmentX(Component.LEFT_ALIGNMENT);
		jp_width.setMaximumSize(new Dimension(400, 30));
		jp_width.setLayout(new BoxLayout(jp_width, BoxLayout.X_AXIS));
		JLabel lbl_width = new JLabel("% der Seitenbreite");
		JTextField jtf_width = new JTextField("Originalbreite", 15);
		jp_width.add(lbl_width);
		jp_width.add(jtf_width);
		contentPane.add(jp_width);
		// Creates JPanel for the height of the file
		JPanel jp_height = new JPanel();
		jp_height.setAlignmentX(Component.LEFT_ALIGNMENT);
		jp_height.setMaximumSize(new Dimension(400, 30));
		jp_height.setLayout(new BoxLayout(jp_height, BoxLayout.X_AXIS));
		JLabel lbl_height = new JLabel("% der Seitenhöhe ");
		JTextField jtf_height = new JTextField("Originalhöhe", 15);
		jp_height.add(lbl_height);
		jp_height.add(jtf_height);
		contentPane.add(jp_height);
		// If it is a pdf the size can not be change by the user
		if (filetype.equals("pdf")) {
			jtf_width.setEditable(false);
			jtf_height.setEditable(false);
		} else {
			try {
				// Creates JPanel to display a model of the pdf page with the image
				BufferedImage bimg = ImageIO.read(new File(path));
				img_width = img_orgWidth = bimg.getWidth();
				img_height = img_orgHeight = bimg.getHeight();
				int mp_width = PAGE_WIDTH / 5;
				int mp_height = PAGE_HEIGHT / 5;
				JPanel jp_model = new JPanel() {

					private static final long serialVersionUID = 6796650979051533416L;

					@Override
					public void paintComponent(Graphics g) {
						g.setColor(Color.white);
						g.fillRect(0, 0, mp_width, mp_height);
						Image img = bimg.getScaledInstance((int) img_width / 5, (int) img_height / 5,
								Image.SCALE_SMOOTH);
						g.drawImage(img, 0, 0, null);
						g.setColor(Color.black);
						g.drawRect(0, 0, mp_width, mp_height);
						if (img.getWidth(null) > 119) {
							g.setColor(Color.red);
							g.drawRect(mp_width + 1, 0, img.getWidth(null) - mp_width, img.getHeight(null));
						}
						if (img.getHeight(null) > mp_height) {
							g.setColor(Color.red);
							g.drawRect(0, mp_height + 1, img.getWidth(null), img.getHeight(null) - mp_height);
						}
						g.dispose();
					}
				};
				contentPane.add(jp_model);
				// Listens to changes of the width- and height-textfields
				jtf_width.getDocument().addDocumentListener(new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent e) {
						updateWidth(jtf_width);
						jp_model.repaint();
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						updateWidth(jtf_width);
						jp_model.repaint();
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						updateWidth(jtf_width);
						jp_model.repaint();
					}
				});
				jtf_height.getDocument().addDocumentListener(new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent e) {
						updateHeight(jtf_height);
						jp_model.repaint();
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						updateHeight(jtf_height);
						jp_model.repaint();
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						updateHeight(jtf_height);
						jp_model.repaint();
					}
				});
			} catch (IOException e) {
				// Do nothing
			}
		}
		// Adds contentPane
		this.getViewport().add(contentPane);
		this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	}

	/**
	 * 
	 * @return Width of image, if it is an image
	 */
	public float getImageWidth() {
		return img_width;
	}

	/**
	 * 
	 * @return Height of image, if it is an image
	 */
	public float getImageHeight() {
		return img_height;
	}

	/**
	 * 
	 * @return Path of the file
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Returns the parth of the path after the last slash
	 * 
	 * @return Name of the file
	 */
	public String getFilename() {
		String[] splitPath = path.split("/");
		return splitPath[splitPath.length - 1];
	}

	/**
	 * Updates the width of the image according to the content of the given
	 * JTextField
	 * 
	 * @param jtf
	 *            JTextField where the information is read from
	 */
	protected void updateWidth(JTextField jtf) {
		try {
			int i = Integer.parseInt(jtf.getText());
			if (i > 0) {
				img_width = i * PAGE_WIDTH / 100;
				return;
			}
		} catch (NumberFormatException e) {
			// Do nothing
		}
		img_width = img_orgWidth;
	}

	/**
	 * Updates the height of the image according to the content of the given
	 * JTextField
	 * 
	 * @param jtf
	 *            JTextField where the information is read from
	 */
	protected void updateHeight(JTextField jtf) {
		try {
			int i = Integer.parseInt(jtf.getText());
			if (i > 0) {
				img_height = i * PAGE_HEIGHT / 100;
				return;
			}
		} catch (NumberFormatException e) {
			// Do nothing
		}
		img_height = img_orgHeight;
	}

}
