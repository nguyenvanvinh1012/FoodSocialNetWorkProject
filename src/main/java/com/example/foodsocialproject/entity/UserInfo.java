package com.example.foodsocialproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "UserInfo")
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "study_at", length = 127)
    private String studyAt;

    @Column(name = "working_at", length = 127)
    private String workingAt;

    @Column(name = "favorites", length = 1023)
    private String favorites;

    @Column(name = "other_info", length = 1023)
    private String otherInfo;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;

}
