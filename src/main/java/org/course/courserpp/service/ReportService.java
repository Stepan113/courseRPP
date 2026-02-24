package org.course.courserpp.service;

import lombok.RequiredArgsConstructor;
import org.course.courserpp.model.ApplicationForCash;
import org.course.courserpp.model.CashWithdrawal;
import org.course.courserpp.model.MoneyTransfer;
import org.course.courserpp.model.Score;
import org.course.courserpp.repository.ApplicationForCashRepository;
import org.course.courserpp.repository.CashWithdrawalRepository;
import org.course.courserpp.repository.MoneyTransferRepository;
import org.course.courserpp.repository.ScoreRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ScoreRepository scoreRepository;
    private final CashWithdrawalRepository cashWithdrawalRepository;
    private final MoneyTransferRepository moneyTransferRepository;
    private final ApplicationForCashRepository applicationForCashRepository;
    private final JavaMailSender mailSender;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    public String generateWithdrawalReportText(String scoreId) {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new IllegalArgumentException("Score not found: " + scoreId));
        List<CashWithdrawal> withdrawals = cashWithdrawalRepository.findAllByScoreId(scoreId);

        StringBuilder sb = new StringBuilder();
        sb.append("Отчёт по выдачам наличности\n");
        sb.append("Счёт: ").append(score.getNumber()).append("\n");
        sb.append("Баланс: ").append(score.getBalance()).append("\n");
        sb.append("=".repeat(50)).append("\n");
        sb.append(String.format("%-36s %-15s %-20s %-36s%n",
                "ID", "Сумма", "Дата", "Заявка"));
        sb.append("-".repeat(110)).append("\n");
        for (CashWithdrawal cw : withdrawals) {
            String appId = cw.getApplicationForCash() != null ? cw.getApplicationForCash().getId() : "-";
            sb.append(String.format("%-36s %-15d %-20s %-36s%n",
                    cw.getId(),
                    cw.getWithdrawalAmount(),
                    cw.getCreatedAt() != null ? DATE_FORMAT.format(cw.getCreatedAt().toInstant()) : "-",
                    appId));
        }
        return sb.toString();
    }

    public String generateWithdrawalReportCsv(String scoreId) {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new IllegalArgumentException("Score not found: " + scoreId));
        List<CashWithdrawal> withdrawals = cashWithdrawalRepository.findAllByScoreId(scoreId);

        StringBuilder sb = new StringBuilder();
        sb.append("Счёт;").append(score.getNumber()).append("\n");
        sb.append("Баланс;").append(score.getBalance()).append("\n\n");
        sb.append("ID;Сумма;Дата;Заявка\n");
        for (CashWithdrawal cw : withdrawals) {
            String appId = cw.getApplicationForCash() != null ? cw.getApplicationForCash().getId() : "";
            sb.append(cw.getId()).append(";")
                    .append(cw.getWithdrawalAmount()).append(";")
                    .append(cw.getCreatedAt() != null ? DATE_FORMAT.format(cw.getCreatedAt().toInstant()) : "").append(";")
                    .append(appId).append("\n");
        }
        return sb.toString();
    }

    public String generateTransferReportText(String scoreId) {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new IllegalArgumentException("Score not found: " + scoreId));
        List<MoneyTransfer> transfers = moneyTransferRepository.findAllByScoreId(scoreId);

        StringBuilder sb = new StringBuilder();
        sb.append("Отчёт по переводам\n");
        sb.append("Счёт: ").append(score.getNumber()).append("\n");
        sb.append("Баланс: ").append(score.getBalance()).append("\n");
        sb.append("=".repeat(60)).append("\n");
        sb.append(String.format("%-36s %-15s %-20s%n", "ID", "Сумма", "Дата"));
        sb.append("-".repeat(75)).append("\n");
        for (MoneyTransfer mt : transfers) {
            sb.append(String.format("%-36s %-15d %-20s%n",
                    mt.getId(),
                    mt.getSum(),
                    mt.getCreatedAt() != null ? DATE_FORMAT.format(mt.getCreatedAt().toInstant()) : "-"));
        }
        return sb.toString();
    }

    public String generateTransferReportCsv(String scoreId) {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new IllegalArgumentException("Score not found: " + scoreId));
        List<MoneyTransfer> transfers = moneyTransferRepository.findAllByScoreId(scoreId);

        StringBuilder sb = new StringBuilder();
        sb.append("Счёт;").append(score.getNumber()).append("\n");
        sb.append("Баланс;").append(score.getBalance()).append("\n\n");
        sb.append("ID;Сумма;Дата\n");
        for (MoneyTransfer mt : transfers) {
            sb.append(mt.getId()).append(";")
                    .append(mt.getSum()).append(";")
                    .append(mt.getCreatedAt() != null ? DATE_FORMAT.format(mt.getCreatedAt().toInstant()) : "").append("\n");
        }
        return sb.toString();
    }

    public String generateApplicationsReportText(String scoreId) {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new IllegalArgumentException("Score not found: " + scoreId));
        List<ApplicationForCash> applications = applicationForCashRepository.findAllByScoreId(scoreId);

        StringBuilder sb = new StringBuilder();
        sb.append("Список заявок по счёту\n");
        sb.append("Счёт: ").append(score.getNumber()).append("\n");
        sb.append("=".repeat(60)).append("\n");
        sb.append(String.format("%-36s %-15s %-15s%n", "ID", "Задержка", "Сумма"));
        sb.append("-".repeat(70)).append("\n");
        for (ApplicationForCash app : applications) {
            sb.append(String.format("%-36s %-15d %-15d%n",
                    app.getId(),
                    app.getDelay(),
                    app.getWithdrawalAmount()));
        }
        return sb.toString();
    }

    public String generateApplicationsReportCsv(String scoreId) {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new IllegalArgumentException("Score not found: " + scoreId));
        List<ApplicationForCash> applications = applicationForCashRepository.findAllByScoreId(scoreId);

        StringBuilder sb = new StringBuilder();
        sb.append("Счёт;").append(score.getNumber()).append("\n\n");
        sb.append("ID;Задержка;Сумма\n");
        for (ApplicationForCash app : applications) {
            sb.append(app.getId()).append(";")
                    .append(app.getDelay()).append(";")
                    .append(app.getWithdrawalAmount()).append("\n");
        }
        return sb.toString();
    }

    public String generateAccountStatementText(String scoreId, Date from, Date to) {
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new IllegalArgumentException("Score not found: " + scoreId));
        List<CashWithdrawal> withdrawals = cashWithdrawalRepository.findAllByScoreIdAndPeriod(scoreId, from, to);
        List<MoneyTransfer> transfers = moneyTransferRepository.findAllByScoreIdAndPeriod(scoreId, from, to);

        StringBuilder sb = new StringBuilder();
        sb.append("Выписка по счёту\n");
        sb.append("Счёт: ").append(score.getNumber()).append("\n");
        sb.append("Баланс: ").append(score.getBalance()).append("\n");
        sb.append("Период: ").append(DATE_FORMAT.format(from.toInstant())).append(" — ").append(DATE_FORMAT.format(to.toInstant())).append("\n");
        sb.append("=".repeat(60)).append("\n\n");

        sb.append("--- Выдачи наличности ---\n");
        sb.append(String.format("%-36s %-15s %-20s %-36s%n", "ID", "Сумма", "Дата", "Заявка"));
        sb.append("-".repeat(110)).append("\n");
        for (CashWithdrawal cw : withdrawals) {
            String appId = cw.getApplicationForCash() != null ? cw.getApplicationForCash().getId() : "-";
            sb.append(String.format("%-36s %-15d %-20s %-36s%n",
                    cw.getId(),
                    cw.getWithdrawalAmount(),
                    cw.getCreatedAt() != null ? DATE_FORMAT.format(cw.getCreatedAt().toInstant()) : "-",
                    appId));
        }

        sb.append("\n--- Переводы ---\n");
        sb.append(String.format("%-36s %-15s %-20s%n", "ID", "Сумма", "Дата"));
        sb.append("-".repeat(75)).append("\n");
        for (MoneyTransfer mt : transfers) {
            sb.append(String.format("%-36s %-15d %-20s%n",
                    mt.getId(),
                    mt.getSum(),
                    mt.getCreatedAt() != null ? DATE_FORMAT.format(mt.getCreatedAt().toInstant()) : "-"));
        }
        return sb.toString();
    }

    public void sendAccountStatementByEmail(String scoreId, Date from, Date to, String email) {
        String statement = generateAccountStatementText(scoreId, from, to);
        Score score = scoreRepository.findById(scoreId)
                .orElseThrow(() -> new IllegalArgumentException("Score not found: " + scoreId));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Выписка по счёту " + score.getNumber());
        message.setText(statement);
        mailSender.send(message);
    }
}
