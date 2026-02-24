package org.course.courserpp.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPdfReport(String to, byte[] pdfBytes, String subject) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText("В приложении находится отчёт в формате PDF.", false);
            helper.addAttachment("отчёт.pdf", new ByteArrayResource(pdfBytes), "application/pdf");
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка отправки письма", e);
        }
    }
}
