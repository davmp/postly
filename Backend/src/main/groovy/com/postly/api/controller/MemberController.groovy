package com.postly.api.controller

import com.postly.api.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/member")
class MemberController {
    @Autowired
    private MemberService memberService

    @GetMapping
    ResponseEntity getAll() {
        return ResponseEntity.ok(this.memberService.getAll())
    }

    @GetMapping("/{id}")
    ResponseEntity getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.memberService.getById(UUID.fromString(id)))
    }

    @GetMapping("/username/{username}")
    ResponseEntity getByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(this.memberService.getByUsername(username))
    }
}
