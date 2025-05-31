//// entity/Post.java
//package com.example.find_people.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Post {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String title;
//
//    @Column(columnDefinition = "TEXT")
//    private String content;
//
//    private boolean isActive = true;
//
//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category category;
//}
