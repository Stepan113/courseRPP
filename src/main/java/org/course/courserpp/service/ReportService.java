package org.course.courserpp.service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.course.courserpp.model.Card;
import org.course.courserpp.model.CashWithdrawal;
import org.course.courserpp.model.MoneyTransfer;
import org.course.courserpp.model.ReplenishmentOperation;
import org.course.courserpp.model.Score;
import org.course.courserpp.repository.CardRepository;
import org.course.courserpp.repository.CashWithdrawalRepository;
import org.course.courserpp.repository.MoneyTransferRepository;
import org.course.courserpp.repository.ReplenishmentOperationRepository;
import org.course.courserpp.repository.ScoreRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ReportService {

    private final CardRepository cardRepository;
    private final ScoreRepository scoreRepository;
    private final CashWithdrawalRepository cashWithdrawalRepository;
    private final MoneyTransferRepository moneyTransferRepository;
    private final ReplenishmentOperationRepository replenishmentOperationRepository;
    private final JavaMailSender mailSender;

    public ReportService(CardRepository cardRepository,
                         ScoreRepository scoreRepository,
                         CashWithdrawalRepository cashWithdrawalRepository,
                         MoneyTransferRepository moneyTransferRepository,
                         ReplenishmentOperationRepository replenishmentOperationRepository,
                         JavaMailSender mailSender) {
        this.cardRepository = cardRepository;
        this.scoreRepository = scoreRepository;
        this.cashWithdrawalRepository = cashWithdrawalRepository;
        this.moneyTransferRepository = moneyTransferRepository;
        this.replenishmentOperationRepository = replenishmentOperationRepository;
        this.mailSender = mailSender;
    }

    public byte[] generatePdfReport() throws DocumentException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        BaseFont baseFont = BaseFont.createFont(
                "/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf",
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
        );
        Font titleFont = new Font(baseFont, 18, Font.BOLD);
        Font headerFont = new Font(baseFont, 14, Font.BOLD);
        Font normalFont = new Font(baseFont, 12, Font.NORMAL);

        document.add(new Paragraph("Отчёт по банковским операциям", titleFont));
        document.add(new Paragraph(" "));

        addCardsSection(document, headerFont, normalFont);
        addScoresSection(document, headerFont, normalFont);
        addCashWithdrawalsSection(document, headerFont, normalFont);
        addMoneyTransfersSection(document, headerFont, normalFont);
        addReplenishmentOperationsSection(document, headerFont, normalFont);

        document.close();
        return outputStream.toByteArray();
    }

    public void sendReportByEmail(String toEmail) throws DocumentException, IOException, MessagingException {
        byte[] pdfBytes = generatePdfReport();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(toEmail);
        helper.setSubject("Отчёт по банковским операциям");
        helper.setText("Во вложении находится отчёт по банковским операциям в формате PDF.");
        helper.addAttachment("отчёт.pdf", new ByteArrayResource(pdfBytes), "application/pdf");

        mailSender.send(message);
    }

    private void addCardsSection(Document document, Font headerFont, Font normalFont) throws DocumentException {
        List<Card> cards = cardRepository.findAll();
        document.add(new Paragraph("Карты", headerFont));
        if (cards.isEmpty()) {
            document.add(new Paragraph("Нет данных", normalFont));
        } else {
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.addCell(new PdfPCell(new Phrase("Номер карты", normalFont)));
            table.addCell(new PdfPCell(new Phrase("Баланс", normalFont)));
            table.addCell(new PdfPCell(new Phrase("CVC", normalFont)));
            for (Card card : cards) {
                table.addCell(new PdfPCell(new Phrase(card.getNumber(), normalFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(card.getBalance()), normalFont)));
                table.addCell(new PdfPCell(new Phrase(card.getCVC(), normalFont)));
            }
            document.add(table);
        }
        document.add(new Paragraph(" "));
    }

    private void addScoresSection(Document document, Font headerFont, Font normalFont) throws DocumentException {
        List<Score> scores = scoreRepository.findAll();
        document.add(new Paragraph("Счета", headerFont));
        if (scores.isEmpty()) {
            document.add(new Paragraph("Нет данных", normalFont));
        } else {
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.addCell(new PdfPCell(new Phrase("Номер счёта", normalFont)));
            table.addCell(new PdfPCell(new Phrase("Баланс", normalFont)));
            table.addCell(new PdfPCell(new Phrase("Валюта", normalFont)));
            for (Score score : scores) {
                table.addCell(new PdfPCell(new Phrase(score.getNumber(), normalFont)));
                table.addCell(new PdfPCell(new Phrase(score.getBalance(), normalFont)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(score.getCurrency()), normalFont)));
            }
            document.add(table);
        }
        document.add(new Paragraph(" "));
    }

    private void addCashWithdrawalsSection(Document document, Font headerFont, Font normalFont) throws DocumentException {
        List<CashWithdrawal> withdrawals = cashWithdrawalRepository.findAll();
        document.add(new Paragraph("Снятие наличных", headerFont));
        if (withdrawals.isEmpty()) {
            document.add(new Paragraph("Нет данных", normalFont));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell(new PdfPCell(new Phrase("Сумма снятия", normalFont)));
            table.addCell(new PdfPCell(new Phrase("Дата", normalFont)));
            for (CashWithdrawal w : withdrawals) {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(w.getWithdrawalAmount()), normalFont)));
                table.addCell(new PdfPCell(new Phrase(
                        w.getCreatedAt() != null ? sdf.format(w.getCreatedAt()) : "-", normalFont)));
            }
            document.add(table);
        }
        document.add(new Paragraph(" "));
    }

    private void addMoneyTransfersSection(Document document, Font headerFont, Font normalFont) throws DocumentException {
        List<MoneyTransfer> transfers = moneyTransferRepository.findAll();
        document.add(new Paragraph("Денежные переводы", headerFont));
        if (transfers.isEmpty()) {
            document.add(new Paragraph("Нет данных", normalFont));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell(new PdfPCell(new Phrase("Сумма", normalFont)));
            table.addCell(new PdfPCell(new Phrase("Дата", normalFont)));
            for (MoneyTransfer t : transfers) {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(t.getSum()), normalFont)));
                table.addCell(new PdfPCell(new Phrase(
                        t.getCreatedAt() != null ? sdf.format(t.getCreatedAt()) : "-", normalFont)));
            }
            document.add(table);
        }
        document.add(new Paragraph(" "));
    }

    private void addReplenishmentOperationsSection(Document document, Font headerFont, Font normalFont) throws DocumentException {
        List<ReplenishmentOperation> operations = replenishmentOperationRepository.findAll();
        document.add(new Paragraph("Операции пополнения", headerFont));
        if (operations.isEmpty()) {
            document.add(new Paragraph("Нет данных", normalFont));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell(new PdfPCell(new Phrase("Сумма", normalFont)));
            table.addCell(new PdfPCell(new Phrase("Дата", normalFont)));
            for (ReplenishmentOperation op : operations) {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(op.getSum()), normalFont)));
                table.addCell(new PdfPCell(new Phrase(
                        op.getCreatedAt() != null ? sdf.format(op.getCreatedAt()) : "-", normalFont)));
            }
            document.add(table);
        }
        document.add(new Paragraph(" "));
    }
}
