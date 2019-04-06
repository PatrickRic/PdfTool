package pdfCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.io.image.ImageDataFactory;

/**
 * Creates pdfs with the use of itextpdf which is under the AGPL license.
 * @author Patrick
 *
 */
public class PDFCreator {
	/**
	 * 
	 * @param fileData
	 *            Data of the sourcefiles
	 * @param saveTo
	 *            Destination of the pdf
	 * @return Optional error-message
	 */
	public static String createPDF(FileData[] fileData, String saveTo) {
		// Creates and instantiates new Pdf writer, if possible
		PdfWriter writer = null;
		try {
			File dest = new File(saveTo + ".pdf");
			if (dest.exists()) {
				int i = JOptionPane.showConfirmDialog(null,
						"Datei bereits vorhanden. Soll die Datei überschrieben werden?", "Warnung!",
						JOptionPane.CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (i == JOptionPane.CANCEL_OPTION) {
					return "Konvertierung abgebrochen";
				}
			}
			writer = new PdfWriter(new FileOutputStream(dest));
		} catch (FileNotFoundException e) {
			// Returns error-message, if given path exists
			return "Ungültiger Zieldateipfad";
		}
		// Creates pdf document
		PdfDocument pdfDocument = new PdfDocument(writer);
		Document doc = new Document(pdfDocument);
		try {
			// Adds new page for each image/existing page
			for (int x = 0; x < fileData.length; x++) {
				FileData fd = fileData[x];
				if (fd.isPdf()) {
					PdfReader reader;
					try {
						reader = new PdfReader(fd.getPath());
					} catch (Exception e) {
						doc.close();
						pdfDocument.close();
						return "Pdf an Pos." + (x + 1) + " konnte nicht gelesen werden";
					}
					PdfDocument srcPdf = new PdfDocument(reader);
					srcPdf.copyPagesTo(1, srcPdf.getNumberOfPages(), pdfDocument);
					srcPdf.close();
				} else {
					// Import image
					Image img = null;
					try {
						img = new Image(ImageDataFactory.create(fd.getPath()));
					} catch (Exception e) {
						pdfDocument.close();
						doc.close();
						return "Ungültiger Datei-pfad/typ an Pos." + (x + 1);
					}
					// Sets margins of document
					doc.setMargins(0f, 0f, 0f, 0f);
					// Resizes image
					img.scaleAbsolute(fd.getImageWidth(), fd.getImageHeight());
					// Adds image to pdf
					pdfDocument.addNewPage();
					float doc_height = pdfDocument.getDefaultPageSize().getHeight();
					img.setFixedPosition(pdfDocument.getNumberOfPages(), 0, doc_height - fd.getImageHeight());
					doc.add(img);
				}
			}
			doc.close();
			pdfDocument.close();
			return "";
		} catch (PdfException e) {
			doc.close();
			pdfDocument.close();
			return "Leeres Dokument";
		}
	}

}
