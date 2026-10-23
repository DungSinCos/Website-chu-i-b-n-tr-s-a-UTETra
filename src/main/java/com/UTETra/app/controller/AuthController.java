package com.UTETra.app.controller;

import com.UTETra.app.dto.AuthResponse;
import com.UTETra.app.security.JwtAuthenticationFilter;
import com.UTETra.app.security.JwtService;
import com.UTETra.app.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;

@Controller
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Receives the credentials posted by login.jsp (form-encoded), validates them and,
     * on success, stores the JWT in an HttpOnly cookie that acts as the session.
     */
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AuthResponse> doLogin(@RequestParam(required = false) String email,
                                                @RequestParam(required = false) String password,
                                                @RequestParam(name = "remember-me", defaultValue = "false") boolean rememberMe,
                                                HttpServletRequest request) {
        String token = authService.login(email, password);

        ResponseCookie cookie = ResponseCookie.from(JwtAuthenticationFilter.COOKIE_NAME, token)
                .httpOnly(true)
                .secure(request.isSecure())
                .sameSite("Lax")
                .path("/")
                // Remember me -> persistent cookie for the token lifetime; otherwise a browser-session cookie.
                .maxAge(rememberMe ? Duration.ofMillis(jwtService.getExpirationMs()) : Duration.ofSeconds(-1))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(AuthResponse.ok(request.getContextPath() + "/"));
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /** Wrong email/password -> 401; locked/inactive account -> 403. Both carry a message for the pop-up. */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<AuthResponse> handleAuthError(AuthenticationException ex) {
        HttpStatus status = ex instanceof BadCredentialsException ? HttpStatus.UNAUTHORIZED : HttpStatus.FORBIDDEN;
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(AuthResponse.error(ex.getMessage()));
    }
}
