package com.example.demo.repository

import com.example.demo.domain.forum.Forum
import org.springframework.data.jpa.repository.JpaRepository

interface ForumRepository extends JpaRepository<Forum, UUID> {
}
