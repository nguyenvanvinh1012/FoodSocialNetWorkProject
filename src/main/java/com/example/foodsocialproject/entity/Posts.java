package com.example.foodsocialproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "Posts")
@AllArgsConstructor
@NoArgsConstructor
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @Column(name = "image")
    private String image;

    @Column(name = "food_name", length = 3000)
    private String foodName;

    @Column(name = "description ", length = 3000)
    private String description ;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "cooking_time")
    private String cookingTime;

    @Column(name = "ingredients", length = 3000)
    private String ingredients ;

    @Column(name = "like_count")
    private int likeCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Likes> likes;


}
