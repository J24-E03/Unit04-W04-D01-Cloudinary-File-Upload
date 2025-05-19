package com.dci.full_mvc.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageMetaData {

    private String imageUrl;

    @Id
    private String publicId;


    @OneToOne(mappedBy = "poster")
    private Movie movie;


}
