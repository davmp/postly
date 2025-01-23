package com.example.demo.repository

import com.example.demo.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository extends JpaRepository<User, UUID> {

}