package es.dawgroup2.juding.indexEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
public class IndexEmailController {

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("/index-email")
    public boolean sendEmail(IndexEmail indexEmail) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<h2>Mensaje por formulario web</h2>" +
                "<ul>" +
                "   <li>Remitente: " + indexEmail.getName() + " (" + indexEmail.getEmail() + ").</li>" +
                "   <li>Asunto: " + indexEmail.getSubject() + ".</li>" +
                "   <li>Mensaje:</li>" +
                "</ul>" + indexEmail.getMessage().replaceAll("\r|\n|\r\n", "<br>");
        try {
            helper.setText(htmlMsg, true); // Use this or above line.
            helper.setTo("juding.noreply@gmail.com");
            helper.setSubject("Mensaje página web: " + indexEmail.getSubject());
            helper.setFrom(indexEmail.getName() + "<" + indexEmail.getEmail() + ">");
            helper.setReplyTo(indexEmail.getName() + "<" + indexEmail.getEmail() + ">");
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }
}
