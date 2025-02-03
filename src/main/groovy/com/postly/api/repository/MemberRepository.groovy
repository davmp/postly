package com.postly.api.repository

import com.postly.api.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.NativeQuery

interface MemberRepository extends JpaRepository<Member, UUID> {
    @NativeQuery("select * from public.member where username = ?")
    Optional<Member> findByUsername(String username)
}