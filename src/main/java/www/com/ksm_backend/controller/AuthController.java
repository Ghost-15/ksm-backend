package www.com.ksm_backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import www.com.ksm_backend.dto.*;
import www.com.ksm_backend.service.AuthService;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/backend")
@RequiredArgsConstructor
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
  @PostMapping("/changePswd")
  public void changePassword(@RequestBody PswdDTO pswdDTO, Principal connectedUser, HttpServletResponse response) {
    service.changePswd(pswdDTO, connectedUser, response);
  }
  @PostMapping("/linkToNewPswd")
  public void forgotPassword(@RequestBody AuthRequest request, HttpServletResponse response) {
    service.linkToNewPswd(request, response);
  }
  @GetMapping("/confirm/{code}")
  public void confirm(@PathVariable("code") String code, HttpServletResponse response) {
    service.confirm(code, response);
  }
  @PostMapping("/changePswdLink")
  public void changePswdLink(@RequestBody PswdDTO pswdDTO, HttpServletResponse response) {
    service.changePswdLink(pswdDTO, response);
  }
}
