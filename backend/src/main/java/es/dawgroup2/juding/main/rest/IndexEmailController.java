package es.dawgroup2.juding.main.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RestController
@RequestMapping("/api")
public class IndexEmailController {

    @Autowired
    private JavaMailSender emailSender;

    /**
     * Sends a email when receiving needed parameters (used via asynchronous request).
     *
     * @param name    Name of sender.
     * @param email   Email of sender.
     * @param subject Subject of email.
     * @param message Body of email.
     * @return HTTP OK if successful, {@code Bad Request} if not.
     */
    @Operation(summary = "Sends a email when receiving needed parameters (used via asynchronous request).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email was sent properly.",
                    content = {@Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Email could not be correctly sent.",
                    content = @Content)
    })
    @GetMapping("/index-email")
    public ResponseEntity<?> sendEmail(@Parameter(description = "Name of sender.") @RequestParam String name,
                                       @Parameter(description = "Email of sender.") @RequestParam String email,
                                       @Parameter(description = "Subject of email.") @RequestParam String subject,
                                       @Parameter(description = "Body of email.") @RequestParam String message) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<h2>Mensaje por formulario web</h2>" +
                "<ul>" +
                "   <li>Remitente: " + name + " (" + email + ").</li>" +
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
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
