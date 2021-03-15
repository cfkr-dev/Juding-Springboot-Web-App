package es.dawgroup2.juding.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
public class IndexEmailController {

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("/index-email")
    public boolean sendEmail(@RequestParam String name,
                             @RequestParam String email,
                             @RequestParam String subject,
                             @RequestParam String message) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<h2>Mensaje por formulario web</h2>" +
                "<ul>" +
                "   <li>Remitente: " + name+ " (" + email + ").</li>" +
                "   <li>Asunto: " + subject + ".</li>" +
                "   <li>Mensaje:</li>" +
                "</ul>" + message.replaceAll("\r|\n|\r\n", "<br>");
        try {
            helper.setText(htmlMsg, true); // Use this or above line.
            helper.setTo("juding.noreply@gmail.com");
            helper.setSubject("Mensaje página web: " + subject);
            helper.setFrom(name + "<" + email + ">");
            helper.setReplyTo(name + "<" + email + ">");
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }
}
