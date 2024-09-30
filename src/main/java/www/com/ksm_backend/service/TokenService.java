package www.com.ksm_backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import www.com.ksm_backend.entity.Token;
import www.com.ksm_backend.entity.User;
import www.com.ksm_backend.repository.TokenRepository;

import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private EmailService emailService;

    public void createTokenByUser(User user) {
        Token token = new Token();
        token.setUser(user);
        Instant creation = Instant.now();
        token.setCreation(creation);
        Instant expiration = creation.plus(15, MINUTES);
        token.setExpiration(expiration);
        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);

        token.setCode(code);
        tokenRepository.save(token);
//        emailService.send_ForgotPswdEmail(token);
    }

}
