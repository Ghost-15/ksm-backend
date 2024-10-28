package www.com.ksm_backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
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
    @Async
    public void send_ContactUs(ContactUsDTO contactUsDTO, HttpServletResponse response) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(contactUsDTO.getEmail());
        helper.setTo("tatibatchi15@gmail.com");
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
                "<div>Monsieur/Madame <b>" + contactUsDTO.getFullName() + "</b></div>\n" +
                "\n" +
                "<div>Message : <b>" + contactUsDTO.getMessage() + "</b></div>\n" +
                "\n" +
                "<div>Email : <b>" + contactUsDTO.getEmail() + "</b></div>\n" +
                "\n" +
                "<div>Numero : <b>" + contactUsDTO.getPhoneNumber() + "</b></div>\n" +
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
    public void send_WelcomeEmail(String getUsername, String getFirst_name) throws MessagingException {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("tatibatchi15@gmail.com");
//        message.setTo(to);
//        message.setSubject("""
//                Merci de votre Confiance,
//
//                Ndaqo Vous Souhaitons La Bienvenu""");
//        String htmlContent = "<h1>This is a test Spring Boot email</h1>" +
//                             "<p>It can contain <strong>HTML</strong> content.</p>";
//        message.setText(htmlContent);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("tatibatchi15@gmail.com");
        helper.setTo(getUsername);

        String texte = String.format(
                "Hi "+ getFirst_name +"%s, " +
                "<br /> We are delighted to count you among the members of our platform%s; " +
                "Thanks"
        );
        helper.setText(texte);
        helper.setSubject("""
            Merci de votre Confiance,

            Vous Souhaitons La Bienvenu""");

//            FileSystemResource fileSystem = new FileSystemResource(new File(attachment));
//            helper.addAttachment(Objects.requireNonNull(fileSystem.getFilename()), fileSystem);

        mailSender.send(message);
    }
    @Async
    public void send_changePswdEmail(String getUsername, String getFirst_name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tatibatchi15@gmail.com");
        message.setTo(getUsername);
        message.setSubject("Your activation Code");
//        String content = "Dear [[name]],<br>"
//                + "Please click the link below to verify your registration:<br>"
//                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
//                + "Thank you,<br>"
//                + "Your company name.";
//        content = content.replace("[[name]]", user.getFullName());
//        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
//        content = content.replace("[[URL]]", verifyURL);
//        <div class="container text-center">
//    <h3>You have signed up successfully!</h3>
//    <p>Please check your email to verify your account.</p>
//    <h4><a th:href="/@{/login}">Click here to Login</a></h4>
//</div>
        String texte = String.format(
                "Hi "+getFirst_name+
                "%s, <br /> You have successfully change your password."
        );
        message.setText(texte);

        mailSender.send(message);
    }
//    @Async
//    public void send_ForgotPswdEmail(Token token) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("tatibatchi15@gmail.com");
//        message.setTo(token.getUser().getUsername());
//        message.setSubject("Your activation Code");
//
//        String texte = String.format(
//                "Hi "+token.getUser().getFirst_name()+
//                "%s, <br /> Use this secret code : "+token.getCode()+" to complete the configuration." +
//                "<br /> The code will expire in 15 min"
//
//        );
//        message.setText(texte);
//
//        mailSender.send(message);
//    }
}
