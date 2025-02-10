package com.postly.api.controller

import com.postly.api.domain.member.AuthResponseDto
import com.postly.api.domain.member.LoginRequestDto
import com.postly.api.domain.member.MemberUpdateRequestDto
import com.postly.api.domain.member.RegisterRequestDto
import com.postly.api.service.AuthService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/auth")
class AuthController {
    @Autowired
    private AuthService authService

    @Autowired
    private AuthenticationManager authenticationManager

    @PostMapping("/login")
    ResponseEntity login(HttpServletResponse response, @RequestBody @Valid LoginRequestDto data) {
        AuthResponseDto auth = this.authService.login(data)
        if (auth != null && auth.token() != null) {
            Cookie cookie = new Cookie("Authorization", URLEncoder.encode("Bearer ${auth.token()}", "UTF-8"))
            response.addCookie(cookie)
        }
        return ResponseEntity.ok(auth)
    }

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    ResponseEntity register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "picture", required = false) MultipartFile picture
    ) {
        return ResponseEntity.ok(this.authService.register(new RegisterRequestDto(
                username: username,
                password: password,
                picture: picture
        )))
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    ResponseEntity update(
            @RequestParam("id") String id,
            @RequestParam(value = "username", required = false) Optional<String> username,
            @RequestParam(value = "picture", required = false) @Valid @ModelAttribute Optional<MultipartFile> picture
    ) {
        if (username.isPresent()) {
            if (username.get().trim().size() != username.get().size()) {
                throw new BadRequestException("Username cannot contain white spaces or special characters")
            }

            if (username.get().size() < 3 || username.get.size() > 20) {
                throw new BadRequestException("Username must be between 3 and 20 characters")
            }
        }

        return ResponseEntity.ok(this.authService.update(new MemberUpdateRequestDto(
                id: UUID.fromString(id),
                username: username,
                picture: picture
        )))
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") String id) {
        this.authService.delete(UUID.fromString(id))
    }
}
