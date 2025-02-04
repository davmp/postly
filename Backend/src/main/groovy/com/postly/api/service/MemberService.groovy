package com.postly.api.service

import com.postly.api.domain.member.Member
import com.postly.api.domain.member.MemberRequestDto
import com.postly.api.domain.member.MemberUpdateRequestDto
import com.postly.api.repository.MemberRepository
import jakarta.validation.Valid
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MemberService {
    @Autowired
    private MediaService mediaService

    @Autowired
    private MemberRepository memberRepository

    List<Member> getAll() {
        return this.memberRepository.findAll()
    }

    Optional<Member> getById(@Valid UUID id) {
        return this.memberRepository.findById(id)
    }

    Optional<Member> getByUsername(String username) {
        return this.memberRepository.findByUsername(username)
    }

    Member create(@Valid MemberRequestDto data) {
        if (this.getByUsername(data.username()).isPresent()) {
            throw new BadRequestException("Username already exists")
        }

        String imageUrl = null

        if (data.picture() != null) {
            imageUrl = this.mediaService.uploadFile(data.picture())
        }

        Member newMember = new Member()
        newMember.setUsername(data.username().trim())
        newMember.setProfilePictureUrl(imageUrl)
        newMember.setCreatedAt(new Date())
        newMember.setUpdatedAt(new Date())

        this.memberRepository.save(newMember)

        return newMember
    }

    Member update(@Valid MemberUpdateRequestDto data) {
        if (data.username().isPresent() && this.getByUsername(data.username().get()).isPresent()) {
            throw new BadRequestException("Username already exists")
        }

        Member member = this.getById(data.id())
                .orElseThrow(() -> new BadRequestException("Member does not exist"))

        if (data.picture().isPresent()) {
            if (!member.getProfilePictureUrl().trim().isEmpty()) {
                this.mediaService.deleteFile(member.getProfilePictureUrl())
            }

            member.setProfilePictureUrl(this.mediaService.uploadFile(data.picture().get()))
        }

        if (data.username().isPresent()) {
            member.setUsername(data.username().get().trim())
        }

        this.memberRepository.save(member)
        return member
    }

    void delete(UUID id) {
        Member member = this.getById(id).orElseThrow(() -> new BadRequestException("Member does not exist"))

        if (member.getProfilePictureUrl().trim().isEmpty()) {
            this.mediaService.deleteFile(member.getProfilePictureUrl())
        }

        this.memberRepository.delete(member)
    }
}
