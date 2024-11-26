package www.com.ksm_backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import www.com.ksm_backend.dto.ContactUsDTO;
import www.com.ksm_backend.entity.Token;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    @Value("${no-reploy-email}")
    protected String NoReplyEmail;
    @Value("${frontend-url}")
    protected String ForgotPswdURL;
    @Async
    public void send_ContactUs(ContactUsDTO contactUsDTO, HttpServletResponse response) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(contactUsDTO.getEmail());
        helper.setTo(NoReplyEmail);
        helper.setSubject("Contact Us");

        String html = "<!doctype html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "      xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\"\n" +
                "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>noreply@email</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div>Monsieur/Madame " + contactUsDTO.getFullName() + "<br/></div>\n" +
                "\n" +
                "<div><br/>Message : " + contactUsDTO.getMessage() + "<br/></div>\n" +
                "\n" +
                "<div><br/>Email : " + contactUsDTO.getEmail() + "<br/></div>\n" +
                "\n" +
                "<div><br/>Numero : " + contactUsDTO.getPhoneNumber() + "</div>\n" +
                "</body>\n" +
                "</html>\n";

        helper.setText(html, true);
        try {
            mailSender.send(message);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(406);
        }
    }
    @Async
    public void send_WelcomeEmail(String getUsername, String getLast_name) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(NoReplyEmail);
        helper.setTo(getUsername);
        helper.setSubject("Bienvenu sur notre plateforme KongoSafeManagement Shop");

        String html = "<!doctype html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "      xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\"\n" +
                "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>noreply@email</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div>Cher, " + getLast_name + "<br/></div>\n" +
                "\n" +
                "<div><br/>Bienvenue chez KongoSafeManagement Shop<br/></div>\n" +
                "\n" +
                "<div><br/>Votre compte a été créé et un mot de passe vous a été automatiquement affectter.<br/></div>\n" +
                "\n" +
                "<div><br/>Veuillez cliquer sur ce lien pour réinitialiser votre mot de passe : <a href='"+ ForgotPswdURL +"'>ForgotPswdURL</a> <br/></div>\n" +
                "\n" +
                "<div><br/><br/><br/>L’équipe KongoSafeManagement Shop<br/></div>\n" +
                "</body>\n" +
                "</html>\n";

        helper.setText(html, true);
        mailSender.send(mimeMessage);
    }
    @Async
    public void send_linkToNewPswd(String getUsername, String getReinitialisationURL, HttpServletResponse response) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(NoReplyEmail);
        helper.setTo(getUsername);
        helper.setSubject("Créez un nouveau mot de passe");

        String html = "<!doctype html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "      xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\"\n" +
                "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>noreply@email</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div>Voulez-vous réinitialiser votre mot de passe ?<br/></div>\n" +
                "\n" +
                "<div><br/>Nous avons reçu une demande de réinitialisation de mot de passe<br/></div>\n" +
                "\n" +
                "<div><br/>Veuillez cliquer sur ce lien : <a href='"+ getReinitialisationURL +"'> réinitialisationUrl </a> </b></div>\n" +
                "\n" +
                "<div><br/>Le code expirera dans 15 minutes<br/></div>\n" +
                "\n" +
                "<div><br/>Si vous n'êtes pas à l'origine de cette demande, vous pouvez ignorer cet email.<br/></div>\n" +
                "<div><br/><br/><br/>L’équipe KongoSafeManagement Shop<br/></div>\n" +
                "</body>\n" +
                "</html>\n";

        helper.setText(html, true);
        try {
            mailSender.send(mimeMessage);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(406);
        }
    }
    @Async
    public void send_ConfiNewPswd(String getUsername, String getFirst_name, HttpServletResponse response) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(NoReplyEmail);
        helper.setTo(getUsername);
        helper.setSubject("Confirmation du changement de votre mot de passe");

        String html = "<!doctype html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                "      xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\"\n" +
                "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>noreply@email</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div>Cher, " + getFirst_name + "<br/></div>\n" +
                "\n" +
                "<div><br/>Nous vous informons que votre mot de passe a bien été modifié.<br/></div>\n" +
                "\n" +
                "<div><br/>Si vous n’êtes pas à l’origine de cette demande, " +
                "nous vous invitons à modifier le mot de passe ou à nous envoyé un email.<br/></div>\n" +
                "\n" +
                "<div><br/>Nous vous remercions pour votre confiance.<br/></div>\n" +
                "<div><br/><br/>L’équipe KongoSafeManagement Shop<br/></div>\n" +
                "</body>\n" +
                "</html>\n";

        helper.setText(html, true);
        try {
            mailSender.send(mimeMessage);
            response.setStatus(200);
        } catch (Exception e) {
            response.setStatus(406);
        }
    }

//            FileSystemResource fileSystem = new FileSystemResource(new File(attachment));
//            helper.addAttachment(Objects.requireNonNull(fileSystem.getFilename()), fileSystem);
}
