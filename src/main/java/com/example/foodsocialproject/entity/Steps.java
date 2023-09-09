package com.example.foodsocialproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "Steps")
@AllArgsConstructor
@NoArgsConstructor
public class Steps {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Min(1)
    @Column(name = "step_number")
    private int stepNumber;

    @Column(name = "step_description")
    private String stepDescription;

    @Column(name = "image")
    private String stepImg;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Posts recipe;
}