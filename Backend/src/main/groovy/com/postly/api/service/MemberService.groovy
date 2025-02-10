package com.postly.api.service

import com.postly.api.domain.member.Member
import com.postly.api.domain.member.MemberResponseDto
import com.postly.api.repository.MemberRepository
import jakarta.validation.Valid
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MemberService implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository

    @Override
    Member loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository
                .findByUsername(username)
                .orElseThrow()
    }

    List<MemberResponseDto> getAll() {
        return this.memberRepository.findAll()
                .collect { member ->
                    new MemberResponseDto(
                            id: member.getId(),
                            username: member.getUsername(),
                            profilePictureUrl: member.getProfilePictureUrl(),
                            createdAt: member.getCreatedAt()
                    )
                }
    }

    MemberResponseDto getById(@Valid UUID id) {
        return this.memberRepository.findById(id)
                .map { member ->
                    new MemberResponseDto(
                            id: member.getId(),
                            username: member.getUsername(),
                            profilePictureUrl: member.getProfilePictureUrl(),
                            createdAt: member.getCreatedAt()
                    )
                }
                .orElseThrow(() -> new BadRequestException("Member does not exist"))
    }

    MemberResponseDto getByUsername(String username) {
        Member member = this.loadUserByUsername(username)

        return new MemberResponseDto(
                id: member.getId(),
                username: member.getUsername(),
                profilePictureUrl: member.getProfilePictureUrl(),
                createdAt: member.getCreatedAt()
        )
    }
}
