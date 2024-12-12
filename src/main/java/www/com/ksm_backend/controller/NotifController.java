package www.com.ksm_backend.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import www.com.ksm_backend.dto.ContactUsDTO;
import www.com.ksm_backend.service.EmailService;

@RestController
@RequestMapping("/notif")
@AllArgsConstructor
@RequiredArgsConstructor
public class NotifController {
    private EmailService service;

    @PostMapping("/contactUs")
    public void contactUs(@RequestBody ContactUsDTO contactUsDTO, HttpServletResponse response) throws MessagingException {
        service.send_ContactUs(contactUsDTO, response);
    }
}
