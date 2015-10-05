package io.github.pokyno.model;

import java.util.ArrayList;
import java.util.Date;


public class Model {
	private ArrayList<Movie> movies = new ArrayList<Movie>();
	
	public Model(){
		fillWithDummy();
	}
	
	public void fillWithDummy(){
		movies.add(new Movie(126, "dieFilm", "john snow", "een film waar dingen in gebeuren enzo", new Date()));
		movies.add(new Movie(134, "dieFilm the sequel", "john snow", "een film waar dingen in gebeuren voor de andere film", new Date(4,4,4)));
		movies.add(new Movie(87, "dieFilm the sequel prequel prequel", "john snow", "een film waar dingen in gebeuren na de andere film voor de andere film", new Date(5,5,5)));
		movies.add(new Movie(20000, "fuck/xml/the/movie", "grootste nachtmerrie", "xml iedereens grootste nachtmerrie waarom gebruiken we niet gewoon JSON voor alles...", new Date(13,12,11)));
	}
	
	public Movie getMovie(int id){
		return movies.get(id);
	}
}
