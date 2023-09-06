package com.example.foodsocialproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "VARCHAR(36)")
    private UUID id;

    @NotBlank(message = "Tên không được để trống!")
    @Column(name = "full_name", length = 127)
    private String fullName;

    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Email is not valid")
    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;

    @Column(name = "avatar_url", length = 255, nullable = true)
    private String avatarUrl;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private UserInfo userInfo;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserRela> followers;

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserRela> followings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Posts> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Likes> likes;



}
