package pdfCreator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * JPanel that enables the user to create a pdf from given images and pdfs.
 * 
 * @author Patrick
 *
 */
public class CreatorPanel extends JPanel {

	private static final long serialVersionUID = -8494502455926029725L;
	/**
	 * The path for the last destination
	 */
	protected String lastDest;
	/**
	 * The JMenuItem for the save-option
	 */
	private JMenuItem jmi_save;

	/**
	 * Creates new CreatorPanel
	 */
	public CreatorPanel() {
		// Panel configuration
		this.setLayout(new BorderLayout());
		// Creates Message-field
		JTextField jTf_messages = new JTextField(40);
		jTf_messages.setEditable(false);
		// Creates JTabbedPane to display the selected files
		JTabbedPane jtp = new JTabbedPane(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		jtp.setPreferredSize(new Dimension(100, 330));
		// Creates JMenuBar
		JMenuBar jmb = new JMenuBar();
		JMenu jm_file = new JMenu("Datei");
		JMenuItem jmi_addFile = new JMenuItem("Hinzufügen");
		jmi_addFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Adds new sourcefile
				jTf_messages.setText(addFile(jtp));
			}

		});
		jm_file.add(jmi_addFile);
		JMenu jm_export = new JMenu("Exportieren");
		JMenuItem jmi_saveAs = new JMenuItem("Speichern unter...");
		jmi_saveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Saves pdf under a new, user-given destination
				jTf_messages.setText(saveAs(jtp));
			}
		});
		jm_export.add(jmi_saveAs);
		jmi_save = new JMenuItem("Speichern");
		jmi_save.setEnabled(false);
		jmi_save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Saves pdf under the last destination
				jTf_messages.setText(save(jtp));
			}
		});
		jm_export.add(jmi_save);
		jmb.add(jm_file);
		jmb.add(jm_export);
		// Adds JButtons to JMenuBar
		JButton bt_delete = new JButton("Löschen");
		bt_delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Deletes selected sourcefile
				jTf_messages.setText(delete(jtp));
			}
		});
		JButton bt_deleteAll = new JButton("Alle löschen");
		bt_deleteAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Removes all sourcefiles
				jtp.removeAll();
			}
		});
		JButton bt_moveLeft = new JButton("<-");
		bt_moveLeft.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Moves selected sourcefile to the left
				jTf_messages.setText(move(jtp, -1));
			}
		});
		JButton bt_moveRight = new JButton("->");
		bt_moveRight.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Moves selected sourcefile to the right
				jTf_messages.setText(move(jtp, 1));
			}
		});
		Border border = BorderFactory.createEmptyBorder(0, 10, 0, 0);
		bt_delete.setBorder(border);
		bt_deleteAll.setBorder(border);
		bt_moveLeft.setBorder(border);
		bt_moveRight.setBorder(border);
		jmb.add(bt_delete);
		jmb.add(bt_deleteAll);
		jmb.add(bt_moveLeft);
		jmb.add(bt_moveRight);
		// Adds components to panel
		this.add(jTf_messages, BorderLayout.NORTH);
		this.add(jtp, BorderLayout.CENTER);
		this.add(jmb, BorderLayout.SOUTH);
		// Adds KeyBindings for shortcuts
		// ctrl+s
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control S"), "s");
		this.getActionMap().put("s", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jTf_messages.setText(save(jtp));
			}
		});
		// ctrl+y
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control Y"), "y");
		this.getActionMap().put("y", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jTf_messages.setText(saveAs(jtp));
			}
		});
		// ctrl+a
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control A"), "a");
		this.getActionMap().put("a", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jTf_messages.setText(addFile(jtp));
			}
		});
		// ctrl+d
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control D"), "d");
		this.getActionMap().put("d", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jTf_messages.setText(delete(jtp));
			}
		});
		// ctrl+r
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control R"), "r");
		this.getActionMap().put("r", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jtp.removeAll();
			}
		});
		// ctrl+q
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control Q"), "q");
		this.getActionMap().put("q", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jTf_messages.setText(move(jtp, -1));
			}
		});
		// ctrl+w
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control W"), "w");
		this.getActionMap().put("w", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jTf_messages.setText(move(jtp, 1));
			}
		});
	}

	/**
	 * Adds new FilePane to the given JTabbedPane. File is selected in a
	 * JFileChooser
	 * 
	 * @param jtp
	 *            JTabbedPane to add the new FilePane to
	 * @return Optional error-message
	 */
	protected String addFile(JTabbedPane jtp) {
		// Creates file chooser
		JFileChooser jFC_conversion = new JFileChooser();
		// Sets filter-options for file chooser
		jFC_conversion.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jFC_conversion.removeChoosableFileFilter(jFC_conversion.getFileFilter());
		jFC_conversion.setFileFilter((new FileNameExtensionFilter(".jpg, .png, .gif, .bmp, .tiff",
				new String[] { "jpg", "png", "gif", "bmp", "tiff" })));
		jFC_conversion.addChoosableFileFilter(new FileNameExtensionFilter(".pdf", new String[] { "pdf" }));
		// If use confirms
		if (jFC_conversion.showDialog(null, "Hinzufügen") == JFileChooser.APPROVE_OPTION) {
			String path = jFC_conversion.getSelectedFile().getPath();
			FilePane fp = new FilePane(path);
			jtp.addTab(fp.getFilename(), fp);
			return "";
		}
		return "Dateisuche abgebrochen";
	}

	/**
	 * Creates pdf and saves it to a file which is selected in a JFileChooser
	 * 
	 * @param jtp
	 *            JTabbedPane to read the data from
	 * @return Optional error-message
	 */
	protected String saveAs(JTabbedPane jtp) {

		// Creates filechooser to choose destination
		JFileChooser jFC_save = new JFileChooser();
		jFC_save.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// Destination-path
		String saveTo;
		// Read destination path if user approves
		if (jFC_save.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			try {
				saveTo = jFC_save.getSelectedFile().getCanonicalPath();
			} catch (IOException e) {
				return "Zieldatei fehlerhaft";
			}
			lastDest = saveTo;
			jmi_save.setEnabled(true);
		} else {
			return "";
		}
		return convert(saveTo, jtp);
	}

	protected String save(JTabbedPane jtp) {
		if (lastDest != null) {
			return convert(lastDest, jtp);
		}
		return "Keine Zieldatei gefunden";
	}

	/*
	 * 
	 * Creates pdf with data read from the given JTabbedPane and saves it to the
	 * given destination
	 * 
	 * @param dest Destination path
	 * 
	 * @param jtp JTabbedPane to read the data from
	 * 
	 * @return Optional error-message
	 */
	private String convert(String dest, JTabbedPane jtp) {
		// Creates Array for FileData
		FileData[] fileData = new FileData[jtp.getTabCount()];
		// Creates FileData for each row
		for (int i = 0; i < jtp.getTabCount(); i++) {
			// Gets FilePanel
			FilePane fp = (FilePane) jtp.getComponentAt(i);
			if (fp.getPath().equals(dest) || fp.getPath().equals(dest + ".pdf")) {
				return "Zieldatei darf keine Sourcedatei sein";
			}
			// Creates FileData and adds it to the Array
			FileData fd = new FileData(fp.getPath(), fp.getImageWidth(), fp.getImageHeight());
			fileData[i] = fd;
		}
		// Creates pdf and returns success/error message
		return PDFCreator.createPDF(fileData, dest);
	}

	/**
	 * Deletes the selected Component of the given JTabbedPane
	 * 
	 * @param jtp
	 *            JTabbedPAne to delete the component from
	 * @return Optional error-message
	 */
	protected String delete(JTabbedPane jtp) {
		int i = jtp.getSelectedIndex();
		if (i != -1) {
			jtp.remove(i);
			return "";
		}
		return "Keine Datei ausgewählt";
	}

	/**
	 * Moves the selected component of the given JTabbedPane into the given
	 * direction
	 * 
	 * @param jtp
	 * @param dir
	 *            Direction. 1 = right, -1 = left
	 * @return Optional error-message
	 */
	protected String move(JTabbedPane jtp, int dir) {
		int i = jtp.getSelectedIndex();
		try {
			FilePane fp = (FilePane) jtp.getComponentAt(i);
			FilePane fp2 = (FilePane) jtp.getComponentAt(i + dir);
			jtp.remove(fp);
			jtp.remove(fp2);
			jtp.insertTab(fp.getFilename(), null, fp, null, i + (int) (0.5 * dir - 0.5));
			jtp.insertTab(fp2.getFilename(), null, fp2, null, i);
			jtp.setSelectedComponent(fp);
			return "";
		} catch (IndexOutOfBoundsException e) {
			return "Datei kann nicht verschoben werden";
		}

	}

}
