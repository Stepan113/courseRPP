package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.course.courserpp.model.CashWithdrawal;
import org.course.courserpp.model.MoneyTransfer;
import org.course.courserpp.model.Score;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    public String generateTextReport(List<Score> scores, Date from, Date to) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Bank Account Report ===\n");
        sb.append("Period: ").append(from).append(" - ").append(to).append("\n\n");
        for (Score score : scores) {
            sb.append("Account: ").append(score.getNumber()).append("\n");
            sb.append("Balance: ").append(score.getBalance()).append("\n");
            sb.append("Currency: ").append(score.getCurrency()).append("\n");

            List<CashWithdrawal> filteredCW = score.getCashWithdrawals().stream()
                    .filter(cw -> cw.getCreatedAt() != null && !cw.getCreatedAt().before(from) && !cw.getCreatedAt().after(to))
                    .collect(Collectors.toList());
            sb.append("Cash Withdrawals (").append(filteredCW.size()).append("):\n");
            for (CashWithdrawal cw : filteredCW) {
                sb.append("  - Amount: ").append(cw.getWithdrawalAmount()).append(", Date: ").append(cw.getCreatedAt()).append("\n");
            }

            List<MoneyTransfer> filteredMT = score.getMoneyTransfers().stream()
                    .filter(mt -> mt.getCreatedAt() != null && !mt.getCreatedAt().before(from) && !mt.getCreatedAt().after(to))
                    .collect(Collectors.toList());
            sb.append("Money Transfers (").append(filteredMT.size()).append("):\n");
            for (MoneyTransfer mt : filteredMT) {
                sb.append("  - Sum: ").append(mt.getSum()).append(", Date: ").append(mt.getCreatedAt()).append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public byte[] generateExcelReport(List<Score> scores, Date from, Date to) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Account Report");
            int rowNum = 0;

            Row headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("Account Number");
            headerRow.createCell(1).setCellValue("Balance");
            headerRow.createCell(2).setCellValue("Currency");
            headerRow.createCell(3).setCellValue("Type");
            headerRow.createCell(4).setCellValue("Amount");
            headerRow.createCell(5).setCellValue("Date");

            for (Score score : scores) {
                List<CashWithdrawal> filteredCW = score.getCashWithdrawals().stream()
                        .filter(cw -> cw.getCreatedAt() != null && !cw.getCreatedAt().before(from) && !cw.getCreatedAt().after(to))
                        .collect(Collectors.toList());
                for (CashWithdrawal cw : filteredCW) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(score.getNumber());
                    row.createCell(1).setCellValue(score.getBalance());
                    row.createCell(2).setCellValue(score.getCurrency());
                    row.createCell(3).setCellValue("Cash Withdrawal");
                    row.createCell(4).setCellValue(cw.getWithdrawalAmount() != null ? cw.getWithdrawalAmount() : 0);
                    row.createCell(5).setCellValue(cw.getCreatedAt() != null ? cw.getCreatedAt().toString() : "");
                }

                List<MoneyTransfer> filteredMT = score.getMoneyTransfers().stream()
                        .filter(mt -> mt.getCreatedAt() != null && !mt.getCreatedAt().before(from) && !mt.getCreatedAt().after(to))
                        .collect(Collectors.toList());
                for (MoneyTransfer mt : filteredMT) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(score.getNumber());
                    row.createCell(1).setCellValue(score.getBalance());
                    row.createCell(2).setCellValue(score.getCurrency());
                    row.createCell(3).setCellValue("Money Transfer");
                    row.createCell(4).setCellValue(mt.getSum() != null ? mt.getSum() : 0);
                    row.createCell(5).setCellValue(mt.getCreatedAt() != null ? mt.getCreatedAt().toString() : "");
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            return baos.toByteArray();
        }
    }
}
