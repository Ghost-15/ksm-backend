package www.com.ksm_backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import www.com.ksm_backend.dto.*;
import www.com.ksm_backend.service.AuthService;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/backend")
//@RequiredArgsConstructor
@AllArgsConstructor
public class AuthController {

  private final AuthService service;

  @PostMapping("/auth")
  public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request, HttpServletResponse response) {
    return ResponseEntity.ok(service.authenticate(request, response));
  }
  @GetMapping("/refresh")
  public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
    return ResponseEntity.ok(service.refreshToken(request, response));
  }
  @PostMapping("/register")
  @PreAuthorize("hasAnyRole('CEO','DEV')")
  public void addUser(@RequestBody RegisterRequest request, HttpServletResponse response){
    service.addUser(request, response);
  }
  @PostMapping("/changePswd")
  public void changePassword(@RequestBody PswdDTO pswdDTO, Principal connectedUser, HttpServletResponse response) {
    service.changePassword(pswdDTO, connectedUser, response);
  }
//  @PostMapping("/forgotPassword")
//  public void forgotPassword(@RequestBody AuthRequest request) {
//    service.forgotPassword(request);
//  }
  @PostMapping("/getCode")
  public void findByCode(@RequestBody MessageDTO code, HttpServletResponse response) throws IOException {
    service.findByCode(code, response);
  }
  @PostMapping("/savePassword")
  public void savePassword(@RequestBody PswdDTO pswdDTO, HttpServletResponse response) {
    service.savePassword(pswdDTO, response);
  }
}
