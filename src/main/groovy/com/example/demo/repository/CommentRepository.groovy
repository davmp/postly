package com.example.demo.repository

import com.example.demo.domain.comment.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository extends JpaRepository<Comment, UUID> {

}