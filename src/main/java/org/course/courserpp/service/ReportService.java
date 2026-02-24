package org.course.courserpp.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    public byte[] generateExcel(List<ApplicationForCash> applications) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Заявки на выдачу наличных");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Идентификатор");
            header.createCell(1).setCellValue("Задержка");
            header.createCell(2).setCellValue("Сумма снятия");

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
            titleRun.setText("Заявки на выдачу наличных");
            titleRun.setBold(true);
            titleRun.setFontSize(16);

            XWPFTable table = doc.createTable();

            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("Идентификатор");
            headerRow.addNewTableCell().setText("Задержка");
            headerRow.addNewTableCell().setText("Сумма снятия");

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

    public byte[] generateExcelForScores(List<Score> scores) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet withdrawalsSheet = workbook.createSheet("Снятие наличных");
            Row wHeader = withdrawalsSheet.createRow(0);
            wHeader.createCell(0).setCellValue("Номер счёта");
            wHeader.createCell(1).setCellValue("Сумма снятия");
            wHeader.createCell(2).setCellValue("Дата");

            int wRowIdx = 1;
            for (Score score : scores) {
                for (CashWithdrawal cw : score.getCashWithdrawals()) {
                    Row row = withdrawalsSheet.createRow(wRowIdx++);
                    row.createCell(0).setCellValue(score.getNumber() != null ? score.getNumber() : "");
                    row.createCell(1).setCellValue(cw.getWithdrawalAmount() != null ? cw.getWithdrawalAmount() : 0L);
                    row.createCell(2).setCellValue(cw.getCreatedAt() != null ? cw.getCreatedAt().toString() : "");
                }
            }

            Sheet transfersSheet = workbook.createSheet("Переводы");
            Row tHeader = transfersSheet.createRow(0);
            tHeader.createCell(0).setCellValue("Номер счёта");
            tHeader.createCell(1).setCellValue("Сумма");
            tHeader.createCell(2).setCellValue("Дата");

            int tRowIdx = 1;
            for (Score score : scores) {
                for (MoneyTransfer mt : score.getMoneyTransfers()) {
                    Row row = transfersSheet.createRow(tRowIdx++);
                    row.createCell(0).setCellValue(score.getNumber() != null ? score.getNumber() : "");
                    row.createCell(1).setCellValue(mt.getSum() != null ? mt.getSum() : 0L);
                    row.createCell(2).setCellValue(mt.getCreatedAt() != null ? mt.getCreatedAt().toString() : "");
                }
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    public byte[] generateWordForScores(List<Score> scores) throws IOException {
        try (XWPFDocument doc = new XWPFDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            XWPFParagraph title = doc.createParagraph();
            XWPFRun titleRun = title.createRun();
            titleRun.setText("Отчёт по выбранным счетам");
            titleRun.setBold(true);
            titleRun.setFontSize(16);

            for (Score score : scores) {
                XWPFParagraph scoreTitle = doc.createParagraph();
                XWPFRun scoreTitleRun = scoreTitle.createRun();
                scoreTitleRun.setText("Счёт: " + (score.getNumber() != null ? score.getNumber() : "Н/Д"));
                scoreTitleRun.setBold(true);
                scoreTitleRun.setFontSize(13);

                XWPFParagraph balancePara = doc.createParagraph();
                XWPFRun balanceRun = balancePara.createRun();
                balanceRun.setText("Баланс: " + score.getBalance() + "  Валюта: " + score.getCurrency());

                if (!score.getCashWithdrawals().isEmpty()) {
                    XWPFParagraph cwTitle = doc.createParagraph();
                    XWPFRun cwTitleRun = cwTitle.createRun();
                    cwTitleRun.setText("Снятие наличных:");
                    cwTitleRun.setBold(true);

                    XWPFTable cwTable = doc.createTable();
                    XWPFTableRow cwHeaderRow = cwTable.getRow(0);
                    cwHeaderRow.getCell(0).setText("Сумма снятия");
                    cwHeaderRow.addNewTableCell().setText("Дата");

                    for (CashWithdrawal cw : score.getCashWithdrawals()) {
                        XWPFTableRow row = cwTable.createRow();
                        row.getCell(0).setText(cw.getWithdrawalAmount() != null ? String.valueOf(cw.getWithdrawalAmount()) : "0");
                        row.getCell(1).setText(cw.getCreatedAt() != null ? cw.getCreatedAt().toString() : "");
                    }
                }

                if (!score.getMoneyTransfers().isEmpty()) {
                    XWPFParagraph mtTitle = doc.createParagraph();
                    XWPFRun mtTitleRun = mtTitle.createRun();
                    mtTitleRun.setText("Переводы:");
                    mtTitleRun.setBold(true);

                    XWPFTable mtTable = doc.createTable();
                    XWPFTableRow mtHeaderRow = mtTable.getRow(0);
                    mtHeaderRow.getCell(0).setText("Сумма");
                    mtHeaderRow.addNewTableCell().setText("Дата");

                    for (MoneyTransfer mt : score.getMoneyTransfers()) {
                        XWPFTableRow row = mtTable.createRow();
                        row.getCell(0).setText(mt.getSum() != null ? String.valueOf(mt.getSum()) : "0");
                        row.getCell(1).setText(mt.getCreatedAt() != null ? mt.getCreatedAt().toString() : "");
                    }
                }
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

            PDFont font = loadFont(document, false);
            PDFont fontBold = loadFont(document, true);

            try (PDPageContentStream cs = new PDPageContentStream(document, page)) {
                float y = 750;
                float margin = 50;
                float lineHeight = 18;

                cs.beginText();
                cs.setFont(fontBold, 14);
                cs.newLineAtOffset(margin, y);
                cs.showText("Отчёт по счёту: " + (score.getNumber() != null ? score.getNumber() : "Н/Д"));
                cs.endText();
                y -= lineHeight * 2;

                cs.beginText();
                cs.setFont(font, 11);
                cs.newLineAtOffset(margin, y);
                cs.showText("Баланс: " + score.getBalance() + "  Валюта: " + score.getCurrency());
                cs.endText();
                y -= lineHeight * 2;

                cs.beginText();
                cs.setFont(fontBold, 12);
                cs.newLineAtOffset(margin, y);
                cs.showText("Снятие наличных:");
                cs.endText();
                y -= lineHeight;

                for (CashWithdrawal cw : cashWithdrawals) {
                    if (y < 50) break;
                    cs.beginText();
                    cs.setFont(font, 10);
                    cs.newLineAtOffset(margin, y);
                    String line = "Сумма: " + cw.getWithdrawalAmount() + "  Дата: " + cw.getCreatedAt();
                    cs.showText(line);
                    cs.endText();
                    y -= lineHeight;
                }

                y -= lineHeight;
                cs.beginText();
                cs.setFont(fontBold, 12);
                cs.newLineAtOffset(margin, y);
                cs.showText("Переводы:");
                cs.endText();
                y -= lineHeight;

                for (MoneyTransfer mt : moneyTransfers) {
                    if (y < 50) break;
                    cs.beginText();
                    cs.setFont(font, 10);
                    cs.newLineAtOffset(margin, y);
                    String line = "Сумма: " + mt.getSum() + "  Дата: " + mt.getCreatedAt();
                    cs.showText(line);
                    cs.endText();
                    y -= lineHeight;
                }
            }

            document.save(out);
            return out.toByteArray();
        }
    }

    public byte[] generatePdfForAllScores(List<Score> scores) throws IOException {
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PDFont font = loadFont(document, false);
            PDFont fontBold = loadFont(document, true);

            for (Score score : scores) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream cs = new PDPageContentStream(document, page)) {
                    float y = 750;
                    float margin = 50;
                    float lineHeight = 18;

                    cs.beginText();
                    cs.setFont(fontBold, 14);
                    cs.newLineAtOffset(margin, y);
                    cs.showText("Счёт №: " + (score.getNumber() != null ? score.getNumber() : "---"));
                    cs.endText();
                    y -= lineHeight * 2;

                    cs.beginText();
                    cs.setFont(font, 11);
                    cs.newLineAtOffset(margin, y);
                    cs.showText("Баланс: " + score.getBalance() + "  Валюта: " + score.getCurrency());
                    cs.endText();
                    y -= lineHeight * 2;

                    cs.beginText();
                    cs.setFont(fontBold, 12);
                    cs.newLineAtOffset(margin, y);
                    cs.showText("Снятие наличных:");
                    cs.endText();
                    y -= lineHeight;

                    List<CashWithdrawal> withdrawals = new ArrayList<>(score.getCashWithdrawals());
                    for (CashWithdrawal cw : withdrawals) {
                        if (y < 50) break;
                        cs.beginText();
                        cs.setFont(font, 10);
                        cs.newLineAtOffset(margin, y);
                        String line = "Сумма: " + cw.getWithdrawalAmount() + "  Дата: " + cw.getCreatedAt();
                        cs.showText(line);
                        cs.endText();
                        y -= lineHeight;
                    }

                    y -= lineHeight;
                    if (y < 50) {
                        y = 750;
                    }

                    cs.beginText();
                    cs.setFont(fontBold, 12);
                    cs.newLineAtOffset(margin, y);
                    cs.showText("Переводы:");
                    cs.endText();
                    y -= lineHeight;

                    List<MoneyTransfer> transfers = new ArrayList<>(score.getMoneyTransfers());
                    for (MoneyTransfer mt : transfers) {
                        if (y < 50) break;
                        cs.beginText();
                        cs.setFont(font, 10);
                        cs.newLineAtOffset(margin, y);
                        String line = "Сумма: " + mt.getSum() + "  Дата: " + mt.getCreatedAt();
                        cs.showText(line);
                        cs.endText();
                        y -= lineHeight;
                    }
                }
            }

            if (document.getNumberOfPages() == 0) {
                PDPage emptyPage = new PDPage();
                document.addPage(emptyPage);
                try (PDPageContentStream cs = new PDPageContentStream(document, emptyPage)) {
                    cs.beginText();
                    cs.setFont(font, 12);
                    cs.newLineAtOffset(50, 750);
                    cs.showText("Нет данных для отчёта.");
                    cs.endText();
                }
            }

            document.save(out);
            return out.toByteArray();
        }
    }

    private PDFont loadFont(PDDocument document, boolean bold) throws IOException {
        String resourcePath = bold ? "/fonts/DejaVuSans-Bold.ttf" : "/fonts/DejaVuSans.ttf";
        InputStream fontStream = getClass().getResourceAsStream(resourcePath);
        if (fontStream != null) {
            return PDType0Font.load(document, fontStream);
        }
        return bold
                ? new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD)
                : new PDType1Font(Standard14Fonts.FontName.HELVETICA);
    }
}
