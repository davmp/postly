package com.postly.api.repository

import com.postly.api.domain.comment.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository extends JpaRepository<Comment, UUID> {

}