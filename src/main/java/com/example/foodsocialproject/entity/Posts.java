package com.example.foodsocialproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @Column(name = "image")
    private String image;

    @Column(name = "food_name", length = 3000)
    private String foodName;

    @Column(name = "description ", length = 3000)
    private String description ;

    @Column(name = "serving_size")
    private String serving_size;

    @Column(name = "cooking_time")
    private String cookingTime;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Likes> likes;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Ingredients> ingredients;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Steps> steps;

    @Transient
    public String getPostImagePath() {
        if (id == null||image==null) {
            return null;
        }
        return "/post-images/" + id + "/" + image;
    }
    @Override
    public String toString() {
        return "Posts{" +
                "id=" + id +
                ", user=" + user +
                ", image='" + image + '\'' +
                ", foodName='" + foodName + '\'' +
                ", description='" + description + '\'' +
                ", serving_size='" + serving_size + '\'' +
                ", cookingTime='" + cookingTime + '\'' +
                ", likeCount=" + likeCount +
                ", likes=" + likes +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }


}
