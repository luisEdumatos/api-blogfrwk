package com.blogfrwk.apiblogfrwk.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column
    private String mainImage;

    @Column
    private String mainLink;

    @Column
    private String creationDate;

    @Column
    private String ownerName;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.REMOVE
    )
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();
}
