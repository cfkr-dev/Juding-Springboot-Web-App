package es.dawgroup2.juding.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Controller
public class IndexController {

    @Autowired
    private JavaMailSender emailSender;

    @GetMapping("/")
    public String index(Model model, @RequestParam Optional<String> status) {
        if (status.isPresent())
            model.addAttribute("status", (status.get().equals("success")) ? status : false);
        return "index";
    }

    @PostMapping("/index-email")
    public String sendEmail(IndexEmail indexEmail) {
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
            e.printStackTrace();
        }
        return "/?status=success#contact";
    }
}
