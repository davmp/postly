package com.example.demo.domain.forum

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Table(name = "forum")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class Forum {
    @Id
    @GeneratedValue
    private UUID id
    private String name
    private String description

    @Column(name = "created_by")
    private UUID createdBy

    @Column(name = "created_at")
    private Date createAt

    @Column(name = "updated_at")
    private Date updatedAt
}
