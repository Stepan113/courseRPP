package org.course.courserpp.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.course.courserpp.model.ApplicationForCash;
import org.course.courserpp.model.CashWithdrawal;
import org.course.courserpp.model.MoneyTransfer;
import org.course.courserpp.model.Score;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReportService {

    public byte[] generateExcel(List<ApplicationForCash> applications) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("ApplicationForCash");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Delay");
            header.createCell(2).setCellValue("Withdrawal Amount");

            int rowIdx = 1;
            for (ApplicationForCash app : applications) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(app.getId() != null ? app.getId() : "");
                row.createCell(1).setCellValue(app.getDelay() != null ? app.getDelay() : 0L);
                row.createCell(2).setCellValue(app.getWithdrawalAmount() != null ? app.getWithdrawalAmount() : 0L);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    public byte[] generateWord(List<ApplicationForCash> applications) throws IOException {
        try (XWPFDocument doc = new XWPFDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            XWPFParagraph title = doc.createParagraph();
            XWPFRun titleRun = title.createRun();
            titleRun.setText("Applications For Cash");
            titleRun.setBold(true);
            titleRun.setFontSize(16);

            XWPFTable table = doc.createTable();

            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("ID");
            headerRow.addNewTableCell().setText("Delay");
            headerRow.addNewTableCell().setText("Withdrawal Amount");

            for (ApplicationForCash app : applications) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(app.getId() != null ? app.getId() : "");
                row.getCell(1).setText(app.getDelay() != null ? String.valueOf(app.getDelay()) : "0");
                row.getCell(2).setText(app.getWithdrawalAmount() != null ? String.valueOf(app.getWithdrawalAmount()) : "0");
            }

            doc.write(out);
            return out.toByteArray();
        }
    }

    public byte[] generatePdf(Score score, List<CashWithdrawal> cashWithdrawals, List<MoneyTransfer> moneyTransfers) throws IOException {
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PDPage page = new PDPage();
            document.addPage(page);

            PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

            try (PDPageContentStream cs = new PDPageContentStream(document, page)) {
                float y = 750;
                float margin = 50;
                float lineHeight = 18;

                cs.beginText();
                cs.setFont(fontBold, 14);
                cs.newLineAtOffset(margin, y);
                cs.showText("Score Report: " + (score.getNumber() != null ? score.getNumber() : "N/A"));
                cs.endText();
                y -= lineHeight * 2;

                cs.beginText();
                cs.setFont(font, 11);
                cs.newLineAtOffset(margin, y);
                cs.showText("Balance: " + score.getBalance() + "  Currency: " + score.getCurrency());
                cs.endText();
                y -= lineHeight * 2;

                cs.beginText();
                cs.setFont(fontBold, 12);
                cs.newLineAtOffset(margin, y);
                cs.showText("Cash Withdrawals:");
                cs.endText();
                y -= lineHeight;

                for (CashWithdrawal cw : cashWithdrawals) {
                    if (y < 50) break;
                    cs.beginText();
                    cs.setFont(font, 10);
                    cs.newLineAtOffset(margin, y);
                    String line = "Amount: " + cw.getWithdrawalAmount() + "  Date: " + cw.getCreatedAt();
                    cs.showText(line);
                    cs.endText();
                    y -= lineHeight;
                }

                y -= lineHeight;
                cs.beginText();
                cs.setFont(fontBold, 12);
                cs.newLineAtOffset(margin, y);
                cs.showText("Money Transfers:");
                cs.endText();
                y -= lineHeight;

                for (MoneyTransfer mt : moneyTransfers) {
                    if (y < 50) break;
                    cs.beginText();
                    cs.setFont(font, 10);
                    cs.newLineAtOffset(margin, y);
                    String line = "Sum: " + mt.getSum() + "  Date: " + mt.getCreatedAt();
                    cs.showText(line);
                    cs.endText();
                    y -= lineHeight;
                }
            }

            document.save(out);
            return out.toByteArray();
        }
    }
}
