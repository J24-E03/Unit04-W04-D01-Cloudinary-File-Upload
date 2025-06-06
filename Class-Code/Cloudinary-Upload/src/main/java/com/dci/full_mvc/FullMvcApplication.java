package com.dci.full_mvc;

import com.dci.full_mvc.model.Director;
import com.dci.full_mvc.model.Movie;
import com.dci.full_mvc.repository.DirectorRepository;
import com.dci.full_mvc.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
@RequiredArgsConstructor
public class FullMvcApplication implements CommandLineRunner {

	private final MovieRepository movieRepository;
	private final DirectorRepository directorRepository;



    public static void main(String[] args) {
		SpringApplication.run(FullMvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		Director hasan = new Director(null,"Hasan","Rezai", LocalDate.now(),"German",true,null);
//
//		Director savedHasan = directorRepository.save(hasan);
//
//
//		Movie hasanMovie = new Movie(null,"Hasan Movie",2025,120,"Adventure","English",true,savedHasan);
//
//
//		movieRepository.save(hasanMovie);


//		System.out.println(movieRepository.findAll());

		System.out.println(LocalDate.now().getYear());
	}


}
