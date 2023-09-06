package com.example.foodsocialproject.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "UserRela")
@AllArgsConstructor
@NoArgsConstructor
public class UserRela {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "VARCHAR(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonProperty("follower")
    @JoinColumn(name = "follower_id", referencedColumnName = "id")
    private Users follower;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonProperty("following")
    @JoinColumn(name = "following_id", referencedColumnName = "id")
    private Users following;

}




