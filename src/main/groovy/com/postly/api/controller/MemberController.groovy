package com.postly.api.controller

import com.postly.api.domain.member.Member
import com.postly.api.domain.member.MemberRequestDto
import com.postly.api.domain.member.MemberUpdateRequestDto
import com.postly.api.service.MemberService
import jakarta.validation.Valid
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/member")
class MemberController {
    @Autowired
    private MemberService memberService

    @GetMapping
    ResponseEntity<List<Member>> getAll() {
        return ResponseEntity.ok(this.memberService.getAll())
    }

    @GetMapping("/{id}")
    ResponseEntity<Member> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.memberService.getById(UUID.fromString(id))
                .orElseThrow(() -> new BadRequestException("Member does not exist")))
    }

    @GetMapping("/username/{username}")
    ResponseEntity<Member> getByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(this.memberService.getByUsername(username)
                .orElseThrow(() -> new BadRequestException("Member does not exist")))
    }

    @PostMapping(consumes = "multipart/form-data")
    ResponseEntity<Member> create(
            @RequestParam("username") String username,
            @RequestParam(value = "picture", required = false) MultipartFile picture
    ) {
        MemberRequestDto data = new MemberRequestDto(username, picture)
        return ResponseEntity.ok(this.memberService.create(data))
    }

    @PutMapping(name = "/{id}", consumes = "multipart/form-data")
    ResponseEntity<Member> update(
            @RequestParam("id") String id,
            @RequestParam(value = "username", required = false) Optional<String> username,
            @RequestParam(value = "picture", required = false) @Valid @ModelAttribute Optional<MultipartFile> picture
    ) {
        MemberUpdateRequestDto data = new MemberUpdateRequestDto(UUID.fromString(id), username, picture)
        return ResponseEntity.ok(this.memberService.update(data))
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") String id) {
        this.memberService.delete(UUID.fromString(id))
    }
}
