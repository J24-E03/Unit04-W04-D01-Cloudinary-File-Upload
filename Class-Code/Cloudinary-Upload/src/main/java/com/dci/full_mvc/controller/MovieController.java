package com.dci.full_mvc.controller;

import com.dci.full_mvc.model.Director;
import com.dci.full_mvc.model.Genre;
import com.dci.full_mvc.model.Movie;
import com.dci.full_mvc.repository.DirectorRepository;
import com.dci.full_mvc.repository.GenreRepository;
import com.dci.full_mvc.repository.MovieRepository;
import com.dci.full_mvc.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;
    private final MovieService movieService;


    @GetMapping
    public String allMovies(Model model){

        model.addAttribute("movies",movieRepository.findAll());
        return "movies/movies-list";
    }

    @GetMapping("/{id}")
    public String getMovieById(@PathVariable Long id,Model model){
        model.addAttribute("movie",movieService.findById(id));
        return "movies/movie-details";
    }


    @GetMapping("/new")
    public String createMovie(Model model){

        model.addAttribute("movie", new Movie());
        model.addAttribute("directors",directorRepository.findAll());
        model.addAttribute("genres",genreRepository.findAll());

        return "movies/movie-form";
    }

    /*
    1. Add validation to your model
    2. Add the @Valid on the posting to the model coming in to validate that it passes the model validation
    3. Add the BindingResult right after the model to capture the errors
    4. Check if there are errors with bindingResult.hasErrors() and if there are render the same page with the same attributes
    * */

    @PostMapping("/create")
    public String createNewMovie(@Valid @ModelAttribute Movie movie, BindingResult bindingResult, @RequestParam List<Long> genreIds,Model model){


        if (bindingResult.hasErrors()) {
            model.addAttribute("directors", directorRepository.findAll());
            model.addAttribute("genres", genreRepository.findAll());
            return "movies/movie-form";
        }

        Movie createdMovie = movieService.createMovie(movie,genreIds);
        return "redirect:/movies/" + createdMovie.getId();
    }

    @GetMapping("/update/{id}")
    public String updateMovieForm(@PathVariable Long id, Model model){

        model.addAttribute("movie",movieService.findById(id));
        model.addAttribute("directors",directorRepository.findAll());

        return "movies/movie-form";
    }

    @PostMapping("/update/{id}")
    public String updateMovie(@ModelAttribute Movie movie){
       movieService.updateMovie(movie);
        return "redirect:/movies/" + movie.getId();
    }

//
    @PostMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id){
        movieRepository.deleteById(id);

        return "redirect:/movies";
    }

    @PostMapping("/omar")
    public String omarRoute(@RequestParam String name, @RequestParam String occupation){
        System.out.println(name);
        System.out.println(occupation);

        return "redirect:/movies";
    }
}
