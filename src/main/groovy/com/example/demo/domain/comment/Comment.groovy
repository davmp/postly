package com.example.demo.domain.comment

import com.example.demo.domain.user.User
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.hibernate.annotations.ManyToAny

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
    @ManyToAny
    @JoinColumn(name = "user_id")
    private User createdBy

    @Column(name = "created_at")
    private Date createdAt

    @Column(name = "updated_at")
    private Date updatedAt
}
