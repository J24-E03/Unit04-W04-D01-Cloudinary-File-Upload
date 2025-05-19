package com.dci.full_mvc.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dci.full_mvc.exceptions.ResourceNotFound;
import com.dci.full_mvc.model.Director;
import com.dci.full_mvc.model.Genre;
import com.dci.full_mvc.model.ImageMetaData;
import com.dci.full_mvc.model.Movie;
import com.dci.full_mvc.repository.DirectorRepository;
import com.dci.full_mvc.repository.GenreRepository;
import com.dci.full_mvc.repository.MovieRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final ImageUploadService imageUploadService;
    private final Cloudinary cloudinary;


    public Movie createMovie(Movie movie, List<Long> genreIds, MultipartFile posterImage){

//        validation for the director
        Director director = directorRepository.findById(movie.getDirector().getDirectorId())
                .orElseThrow(()->new RuntimeException("Director with Id not found"));

        movie.setDirector(director);


//        setting the genres:
        List<Genre> genres = genreRepository.findAllById(genreIds);
        movie.setGenres(genres);

        Map<String,String> uploadedData =  imageUploadService.uploadImage(posterImage);

        ImageMetaData imageMetaData = new ImageMetaData();
        imageMetaData.setImageUrl(uploadedData.get("url"));
        imageMetaData.setPublicId(uploadedData.get("publicId"));

        movie.setPoster(imageMetaData);



        Movie createdMovie = movieRepository.save(movie);


        return createdMovie;
    }



    public List<Movie> findAll(){
        return movieRepository.findAll();
    }


    public Movie findById(Long id){
        Movie foundMovie = movieRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("Movie not found"));

        return foundMovie;
    }


    public Movie updateMovie(Movie movie){
        System.out.println(movie);
        //        validation for the director
        Director director = directorRepository.findById(movie.getDirector().getDirectorId())
                .orElseThrow(()->new ResourceNotFound("Director with Id not found"));

        movie.setDirector(director);


        return movieRepository.save(movie);
    }

    @PostConstruct
    public void init(){

        System.out.println("in post contruct");

        try {
            cloudinary.uploader().destroy("sacpblrsi6zo731koaix", ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
