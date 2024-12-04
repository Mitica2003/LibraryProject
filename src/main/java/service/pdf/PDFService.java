package service.pdf;

import view.model.SaleDTO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileOutputStream;
import java.util.*;

public class PDFService {
    public void generateSalesReport(String filePath, List<SaleDTO> sales) {
        try {
            PdfWriter pdfWriter = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Sales Report")
                    .setBold()
                    .setFontSize(18)
                    .setMarginBottom(20));

            float[] columnWidths = {100, 50, 100, 50, 100};
            Table table = new Table(columnWidths);

            table.addCell(new Paragraph("Title"));
            table.addCell(new Paragraph("Quantity"));
            table.addCell(new Paragraph("Total Price"));
            table.addCell(new Paragraph("Seller_ID"));
            table.addCell(new Paragraph("Timestamp"));

            for (SaleDTO sale : sales) {
                table.addCell(sale.getBookTitle());
                table.addCell(String.valueOf(sale.getQuantity()));
                table.addCell(String.valueOf(sale.getTotalPrice()));
                table.addCell(String.valueOf(sale.getID()));
                table.addCell(String.valueOf(sale.getTimestamp()));
            }

            document.add(table);
            document.close();

            System.out.println("PDF was generated at " + filePath);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
