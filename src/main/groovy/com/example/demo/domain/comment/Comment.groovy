package com.example.demo.domain.comment

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Table(name = "comment")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class Comment {
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
