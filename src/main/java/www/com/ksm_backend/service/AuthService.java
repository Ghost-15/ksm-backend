package www.com.ksm_backend.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import www.com.ksm_backend.config.TokenType;
import www.com.ksm_backend.config.Writer;
import www.com.ksm_backend.dto.*;
import www.com.ksm_backend.entity.Code;
import www.com.ksm_backend.entity.Role;
import www.com.ksm_backend.entity.Token;
import www.com.ksm_backend.entity.User;
import www.com.ksm_backend.repository.CodeRepository;
import www.com.ksm_backend.repository.TokenRepository;
import www.com.ksm_backend.repository.UserRepository;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@AllArgsConstructor
@Service
public class AuthService {
  @Value("${frontend-url}")
  protected String reinitialisationURL;
//          = "http://localhost:5173/";
  private UserRepository userRepository;
//  private UserService userService;
  private TokenRepository tokenRepository;
  private CodeRepository codeRepository;
  private PasswordEncoder passwordEncoder;
  private AuthenticationManager authenticationManager;
  private JwtService jwtService;
  private EmailService emailService;
  private UserDetailsService userDetailsService;

  public void addUser(RegisterRequest request, HttpServletResponse response) {
    String randomCode = UUID.randomUUID().toString();

    User user = new User();
    user.setFirst_name(request.getFirst_name());
    user.setLast_name(request.getLast_name());
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(randomCode));
    user.setPhone_number(request.getPhone_number());
    user.setRole(request.getRole());

    try {
      userRepository.save(user);
      emailService.send_WelcomeEmail(request.getUsername(), request.getLast_name());
      response.setStatus(200);
    } catch (Exception e) {
      response.setStatus(406);
    }
  }

  public AuthResponse authenticate(AuthRequest request, HttpServletResponse response) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        )
    );

    var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
    String jwtToken = jwtService.generateAccesToken(user);

    Cookie authCookie = new Cookie(String.valueOf(user.getUser_id()), jwtService.generateRefreshToken(user));
    authCookie.setHttpOnly(true);
    authCookie.setSecure(true);
    authCookie.setPath("/");
    authCookie.setMaxAge((int) Duration.of(1, ChronoUnit.DAYS).toSeconds());

    response.addCookie(authCookie);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthResponse.builder()
        .access_token(jwtToken)
        .role(user.getRole())
        .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUser_id());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
    String token;
    String username;
    String jwtToken;
    String role;
//    String authorizationHeader = request.getHeader(AUTHORIZATION);
//    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//      String refreshToken = authorizationHeader.substring("Bearer ".length());
//      System.out.println("refreshToken: "+refreshToken);

      if(request.getCookies() != null){
        for(Cookie cookie: request.getCookies()){

            token = cookie.getValue();
            username = jwtService.extractUsername(token);
            var user = userRepository.findByUsername(username).orElseThrow();
            final var userDetails = userDetailsService.loadUserByUsername(username);
            role = user.getRole().toString();
            Cookie DSC = new Cookie(String.valueOf(user.getUser_id()), null);
            DSC.setMaxAge(0);
            response.addCookie(DSC);

            if (jwtService.isTokenValid(token, userDetails)) {
              try {
                jwtToken = jwtService.generateAccesToken(userDetails);
                revokeAllUserTokens(user);
                saveUserToken(user, jwtToken);

                Cookie authCookie = new Cookie(String.valueOf(user.getUser_id()), jwtService.generateRefreshToken(userDetails));
                authCookie.setHttpOnly(true);
                authCookie.setSecure(true);
                authCookie.setPath("/");
                authCookie.setMaxAge((int) Duration.of(1, ChronoUnit.DAYS).toSeconds());

                String json = Writer.JSON_WRITER.writeValueAsString(
                        AuthResponse.builder()
                                .access_token(jwtToken)
                                .role(Role.valueOf(role))
                                .build());
                response.getWriter().write(json);
                response.addCookie(authCookie);

              } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(FORBIDDEN.value());
              }
            } else {
              response.setStatus(UNAUTHORIZED.value());
            }
        }
      } else {
        response.setStatus(UNAUTHORIZED.value());
      }
//    }else{
//      response.setStatus(UNAUTHORIZED.value());
//    }
//    return null;
  }
  public void changePswd(PswdDTO request, Principal connectedUser, HttpServletResponse response) {
    var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
    if (passwordEncoder.matches(request.getCurrentPswd(), user.getPassword()) ) {
      if (request.getNewPswd().equals(request.getComfirmPswd())){
        user.setPassword(passwordEncoder.encode(request.getComfirmPswd()));
        try {
          userRepository.save(user);
          emailService.send_ConfiNewPswd(user.getUsername(), user.getFirst_name(), response);
          response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e){
          response.setStatus(406);
        }
      } else {
        response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
      }
    } else {
      response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }
  }
  public void linkToNewPswd(AuthRequest request, HttpServletResponse response){
    var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
    String randomCode = UUID.randomUUID().toString();
    Code code = Code.builder()
            .code(randomCode)
            .created(LocalDateTime.now())
            .expires(LocalDateTime.now().plusMinutes(15))
            .user(user)
            .build();
    codeRepository.save(code);
    try {
      emailService.send_linkToNewPswd(user.getUsername(), reinitialisationURL + randomCode, response);
      response.setStatus(200);
    } catch (Exception e){
      response.setStatus(406);
    }
  }
  public void confirm(String code, HttpServletResponse response) {
    Code checkCode = codeRepository.findByCode(code).orElseThrow();
    LocalDateTime checkValidated = codeRepository.findValidatedWhereCodeIs(code);
    try {
      if (String.valueOf(checkValidated).contains("-")) {
        response.setStatus(406);
      } else if (LocalDateTime.now().isAfter(checkCode.getExpires())) {
        response.setStatus(408);
      } else {
        checkCode.setValidated(LocalDateTime.now());
        codeRepository.save(checkCode);

        String json = Writer.JSON_WRITER.writeValueAsString(
                MessageDTO.builder()
                        .username(checkCode.getUser().getUsername())
                        .build());

        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
      }
    } catch (Exception e){
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }
  public void changePswdLink(PswdDTO pswdDTO, HttpServletResponse response) {
    var user = userRepository.findByUsername(pswdDTO.getUsername()).orElseThrow();
    if (pswdDTO.getNewPswd().equals(pswdDTO.getComfirmPswd())){
      user.setPassword(passwordEncoder.encode(pswdDTO.getComfirmPswd()));
      try {
        userRepository.save(user);
        emailService.send_ConfiNewPswd(user.getUsername(), user.getFirst_name(), response);
        response.setStatus(200);
      } catch (Exception e){
        response.setStatus(406);
      }
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }
  }
}
