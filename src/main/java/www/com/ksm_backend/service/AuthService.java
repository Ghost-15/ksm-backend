package www.com.ksm_backend.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import www.com.ksm_backend.config.HelperUtils;
import www.com.ksm_backend.dto.*;
import www.com.ksm_backend.entity.Role;
import www.com.ksm_backend.entity.User;
import www.com.ksm_backend.repository.RoleRepository;
import www.com.ksm_backend.repository.TokenRepository;
import www.com.ksm_backend.repository.UserRepository;

import java.io.IOException;
import java.security.Principal;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@Service
public class AuthService {
  @Autowired
  private UserRepository userRepository;
//  @Autowired
//  private UserService userService;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private TokenRepository tokenRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtService jwtService;
  @Autowired
  private TokenService tokenService;
  @Autowired
  private EmailService emailService;
  @Autowired
  private UserDetailsService userDetailsService;

  public void addUser(RegisterRequest request, HttpServletResponse response) {
    User user = new User();
    user.setFirst_name(request.getFirst_name());
    user.setLast_name(request.getLast_name());
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setPhone_number(request.getPhone_number());
    Set<Role> roles = new HashSet<>();
    roles.add(roleRepository.findByName(request.getRole()));
    user.setRoles(roles);

    try {
      userRepository.save(user);
//      emailService.send_WelcomeEmail(request.getUsername(), request.getFirst_name());

      response.setStatus(200);
    } catch (Exception e) {
      response.setStatus(406);
    }
  }

  public AuthResponse authenticate(AuthRequest request, HttpServletResponse response) {

//    authenticationManager.authenticate(
//        new UsernamePasswordAuthenticationToken(
//            request.getUsername(),
//            request.getPassword()
//        )
//    );

    var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
    String jwtToken = jwtService.generateAccesToken(user);

//    List<SimpleGrantedAuthority> sga = user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList();
    Cookie authCookie = new Cookie(String.valueOf(user.getUser_id()), jwtService.generateRefreshToken(user));
    authCookie.setHttpOnly(true);
    authCookie.setSecure(true);
    authCookie.setPath("/");
    authCookie.setMaxAge((int) Duration.of(60, ChronoUnit.MINUTES).toSeconds());
    System.out.println("cookie: "+jwtService.generateRefreshToken(user));

    response.addCookie(authCookie);

//    revokeAllUserTokens(user);
//    saveUserToken(user, jwtToken);
    return AuthResponse.builder()
        .access_token(jwtToken)
        .build();
  }

//  private void saveUserToken(User user, String jwtToken) {
//    var token = Token.builder()
//        .user(user)
//        .token(jwtToken)
//        .tokenType(TokenType.BEARER)
//        .expired(false)
//        .revoked(false)
//        .build();
//    tokenRepository.save(token);
//  }

//  private void revokeAllUserTokens(User user) {
//    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUser_id());
//    if (validUserTokens.isEmpty())
//      return;
//    validUserTokens.forEach(token -> {
//      token.setExpired(true);
//      token.setRevoked(true);
//    });
//    tokenRepository.saveAll(validUserTokens);
//  }

  public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
    String token;
    String username;
//    String authorizationHeader = request.getHeader(AUTHORIZATION);
//    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//      String refreshToken = authorizationHeader.substring("Bearer ".length());
//      System.out.println("refreshToken: "+refreshToken);
      if(request.getCookies() != null){
        for(Cookie cookie: request.getCookies()){
            System.out.println("cookie: "+cookie.getValue());

            token = cookie.getValue();
            username = jwtService.extractUsername(token);
            var user = userRepository.findByUsername(username).orElseThrow();
            final var userDetails = userDetailsService.loadUserByUsername(username);

            Cookie DSC = new Cookie(String.valueOf(user.getUser_id()), null);
            DSC.setMaxAge(0);
            response.addCookie(DSC);

            if (jwtService.isTokenValid(token, userDetails)) {
              try {

                Cookie authCookie = new Cookie(String.valueOf(user.getUser_id()), jwtService.generateRefreshToken(user));
                authCookie.setHttpOnly(true);
                authCookie.setSecure(true);
                authCookie.setPath("/");
                authCookie.setMaxAge((int) Duration.of(60, ChronoUnit.MINUTES).toSeconds());

                Map<String, Object> mapToken = new HashMap<>();
                mapToken.put("token", jwtService.generateAccesToken(user));

                String json = HelperUtils.JSON_WRITER.writeValueAsString(mapToken);
                response.setContentType("application/json; charset=UTF-8");
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
  }
  public void changePassword(PswdDTO request, Principal connectedUser, HttpServletResponse response) {
    var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

    if (passwordEncoder.matches(request.getCurrentPswd(), user.getPassword()) ) {
      if (request.getNewPswd().equals(request.getComfirmPswd())){
        user.setPassword(passwordEncoder.encode(request.getComfirmPswd()));
//      userRepository.save(user);
//      emailService.send_changePswdEmail(user.getUsername(), user.getFirst_name());
      } else {
        response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
      }
    } else {
      response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }
  }
  public void forgotPassword(AuthRequest request) {
    var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
    tokenService.createTokenByUser(user);
  }
  public void findByCode(MessageDTO code, HttpServletResponse response) throws IOException {
    var token = tokenRepository.findByCode(code.getCode());
    System.out.println("token: "+token);
    System.out.println(token.getUser().getUsername());
    if(Instant.now().isAfter(token.getExpiration())){

      Map<String, Object> mapToken = new HashMap<>();
      mapToken.put("username", token.getUser().getUsername());

      String json = HelperUtils.JSON_WRITER.writeValueAsString(mapToken);
      response.setContentType("application/json; charset=UTF-8");
      response.getWriter().write(json);
//      response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }else {
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
    }
  }
  public void savePassword(PswdDTO pswdDTO, HttpServletResponse response) {
    var user = userRepository.findByUsername(pswdDTO.getUsername()).orElseThrow();

    if (pswdDTO.getNewPswd().equals(pswdDTO.getComfirmPswd())){
      user.setPassword(passwordEncoder.encode(pswdDTO.getComfirmPswd()));
//      userRepository.save(user);
//      emailService.send_changePswdEmail(user.getUsername(), user.getFirst_name());
      System.out.println("Bien joue Negro");
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
    } else {
      response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }
  }
}
