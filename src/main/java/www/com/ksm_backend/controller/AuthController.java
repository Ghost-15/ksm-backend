package www.com.ksm_backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import www.com.ksm_backend.dto.AuthRequest;
import www.com.ksm_backend.dto.AuthResponse;
import www.com.ksm_backend.dto.PswdDTO;
import www.com.ksm_backend.service.AuthService;

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
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
    service.refreshToken(request, response);
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
