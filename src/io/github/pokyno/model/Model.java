package io.github.pokyno.model;

import java.util.ArrayList;


public class Model {
	private ArrayList<Movie> movies = new ArrayList<Movie>();
	private ArrayList<Gebruiker> gebruikers = new ArrayList<Gebruiker>();
	private ArrayList<Rating> ratings = new ArrayList<Rating>();
	
	public Model(){
		fillWithDummy();
	}
	
	public void fillWithDummy(){
		movies.add(new Movie(126, "dieFilm", "john snow", "een film waar dingen in gebeuren enzo", "2/15/04"));
		movies.add(new Movie(134, "dieFilm the sequel", "john snow", "een film waar dingen in gebeuren voor de andere film", "2/15/05"));
		movies.add(new Movie(87, "dieFilm the sequel prequel prequel", "john snow", "een film waar dingen in gebeuren na de andere film voor de andere film", "2/15/06"));
		movies.add(new Movie(20000, "fuck/xml/the/movie", "grootste nachtmerrie", "xml iedereens grootste nachtmerrie waarom gebruiken we niet gewoon JSON voor alles...", "10/6/15"));
		
		gebruikers.add(new Gebruiker("berkel", "van", "pieter", "pjeter", "pizza"));
		gebruikers.add(new Gebruiker("visser", "", "thimo", "tito", "cola"));
		gebruikers.add(new Gebruiker("frielink", "", "harm", "dieGozer", "jeesoon"));
		
		ratings.add(new Rating(5.0, gebruikers.get(0), movies.get(0)));
		ratings.add(new Rating(3.0, gebruikers.get(0), movies.get(1)));
		ratings.add(new Rating(2.5, gebruikers.get(1), movies.get(0)));
		ratings.add(new Rating(1.0, gebruikers.get(2), movies.get(2)));
		ratings.add(new Rating(4.0, gebruikers.get(2), movies.get(3)));
	}
	
	/**
	 * returns the movie corresponding to the given name/title
	 * @param name the name/title of the movie
	 * @return the movie if found otherwise null
	 */
	public Movie getMovieByName(String name){
		for(Movie m : movies){
			if(m.getTitel().equals(name)){
				return m;
			}
		}
		return null;
	}
	
	public Rating[] getRatingsByMovie(Movie movie){
		ArrayList<Rating> ratingsByMovie = new ArrayList<Rating>();
		for(Rating r : ratings){
			if(r.getMovie().equals(movie)){
				ratingsByMovie.add(r);
			}
		}
		
		Rating[] array = new Rating[ratingsByMovie.size()];
		for(int i = 0; i < ratingsByMovie.size(); i++){
			array[i] = ratingsByMovie.get(i);
		}
		return array;
	}
	
	public Movie[] getMovies(){
		Movie[] movies = new Movie[this.movies.size()];
		for(int i = 0; i < this.movies.size(); i++){
			movies[i] = this.movies.get(i);
		}
		return movies;
	}
	
	public Gebruiker[] getGebruikers(){
		Gebruiker[] gebruikers = new Gebruiker[this.gebruikers.size()];
		for(int i = 0; i < this.gebruikers.size(); i++){
			gebruikers[i] = this.gebruikers.get(i);
		}
		return gebruikers;
	}
	
	public Gebruiker getGebruikerByNickname(String nickname){
		for(Gebruiker g : gebruikers){
			if(g.getNickname().equals(nickname)){
				return g;
			}
		}
		return null;
	}
	
}
