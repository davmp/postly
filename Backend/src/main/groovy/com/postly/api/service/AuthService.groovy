package com.postly.api.service

import com.postly.api.domain.member.*
import com.postly.api.repository.MemberRepository
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService {
    @Autowired
    private MemberRepository memberRepository

    @Autowired
    private MediaService mediaService

    @Autowired
    private SecurityService securityService

    @Autowired
    private AuthenticationManager authenticationManager

    AuthResponseDto login(LoginRequestDto data) {
        var auth = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.username(), data.password()))

        return new AuthResponseDto(
                username: data.username(),
                token: this.securityService.generateToken((Member) auth.getPrincipal()),
                profilePictureUrl: auth.getPrincipal().getProfilePictureUrl()
        )
    }

    AuthResponseDto register(RegisterRequestDto data) {
        if (this.memberRepository.findByUsername(data.username()).isPresent()) {
            throw new BadRequestException("Username already exists")
        }

        String imgUrl = ""

        if (data.picture() != null) {
            imgUrl = this.mediaService.uploadFile(data.picture())
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password())

        Member member = new Member()
        member.setUsername(data.username())
        member.setPassword(encryptedPassword)
        member.setRole(MemberRole.USER.get())
        member.setProfilePictureUrl(imgUrl)
        member.setUpdatedAt(new Date())
        member.setCreatedAt(new Date())

        this.memberRepository.save(member)

        return new AuthResponseDto(
                username: member.getUsername(),
                token: this.securityService.generateToken(member),
                profilePictureUrl: member.getProfilePictureUrl()
        )
    }
}
