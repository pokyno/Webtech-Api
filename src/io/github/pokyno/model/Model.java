package io.github.pokyno.model;

import java.util.ArrayList;
import java.util.HashMap;


public class Model {
	private ArrayList<Movie> movies = new ArrayList<Movie>();
	private ArrayList<Gebruiker> gebruikers = new ArrayList<Gebruiker>();
	private ArrayList<Rating> ratings = new ArrayList<Rating>();
	private HashMap<String, Gebruiker> authkeys = new HashMap<String, Gebruiker>();
	
	private int keyCode = 12345;
	
	public Model(){
		fillWithDummy();
	}
	
	public void fillWithDummy(){
		movies.add(new Movie(126, "dieFilm", "john snow", "een film waar dingen in gebeuren enzo", "2/15/04"));
		movies.add(new Movie(134, "dieFilm the sequel", "john snow", "een film waar dingen in gebeuren voor de andere film", "2/15/05"));
		movies.add(new Movie(87, "dieFilm the sequel prequel prequel", "john snow", "een film waar dingen in gebeuren na de andere film voor de andere film", "2/15/06"));
		movies.add(new Movie(20000, "fuck xml the movie", "grootste nachtmerrie", "xml iedereens grootste nachtmerrie waarom gebruiken we niet gewoon JSON voor alles...", "10/6/15"));
		
		gebruikers.add(new Gebruiker("berkel", "van", "pieter", "pjeter", "pizza"));
		gebruikers.add(new Gebruiker("visser", "", "thimo", "tito", "cola"));
		gebruikers.add(new Gebruiker("frielink", "", "harm", "dieGozer", "jeesoon"));
		
		ratings.add(new Rating(5.0, gebruikers.get(0), movies.get(0)));
		ratings.add(new Rating(3.0, gebruikers.get(0), movies.get(1)));
		ratings.add(new Rating(2.5, gebruikers.get(1), movies.get(0)));
		ratings.add(new Rating(1.0, gebruikers.get(2), movies.get(2)));
		ratings.add(new Rating(4.0, gebruikers.get(2), movies.get(2)));
		
		updateRatings();
	}
	
	public boolean correctRating(double rating){
		return (rating == 0.5 || rating == 1.0 || rating == 1.5 || rating == 2.0 ||rating == 2.5 ||rating == 3.0 || rating == 3.5 || rating == 4.0 || rating == 4.5 ||rating == 5.0);
	}
	
////////////////////////////movie	
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
	
	/**
	 * geeft een rating array die bij een bepaalde film horen
	 * @param movie 
	 * @return een rating array
	 */
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
	
	/**
	 * geeft een array met alle movies
	 * @return de movies array
	 */
	public Movie[] getMovies(){
		Movie[] movies = new Movie[this.movies.size()];
		for(int i = 0; i < this.movies.size(); i++){
			movies[i] = this.movies.get(i);
		}
		return movies;
	}
	
	/**
	 * ververst de gemiddelde ratings die bij een film horen
	 */
	public void updateRatings(){
		for(Movie m : movies){
			Rating[] ratings = getRatingsByMovie(m);
			double average = 0;
			for(int j = 0; j < ratings.length; j++){
				average += ratings[j].getRating();
			}
			average = average/ratings.length;
			if(correctRating(average)){
				m.setAverageScore(average);
			}else{
				average = Math.round(average*2)/2;
				m.setAverageScore(average);
			}
		}
	}
////////////////////////////gebruiker
	/**
	 * geef alle gebruikers terug
	 * @return de gebruikers
	 */
	public Gebruiker[] getGebruikers(){
		Gebruiker[] gebruikers = new Gebruiker[this.gebruikers.size()];
		for(int i = 0; i < this.gebruikers.size(); i++){
			gebruikers[i] = this.gebruikers.get(i);
		}
		return gebruikers;
	}
	/**
	 * geeft de gebruiker die bij die nick name hoort
	 * @param nickname de nickname van die persoon 
	 * @return de gebruiker
	 */
	public Gebruiker getGebruikerByNickname(String nickname){
		for(Gebruiker g : gebruikers){
			if(g.getNickname().equals(nickname)){
				return g;
			}
		}
		return null;
	}
	
	/**
	 * add een gebruiker aan de lijst met gebruikers
	 * @param gebruiker
	 */
	public void addGebruiker(Gebruiker gebruiker) {
		gebruikers.add(gebruiker);
	}
///////////////////////////auth
	/**
	 * maakt de koppeling tussen een key en een gebruiker
	 * @param gebruiker
	 */
	public void setKey(Gebruiker gebruiker){
		int key = keyCode;
		keyCode++;
		authkeys.put(key+"", gebruiker);
	}
	
	/**
	 * kijkt of de gebruiker al een key heeft
	 * @param gebruiker 
	 * @return true als de key al bestaat anders false
	 */
	public boolean hasKey(Gebruiker gebruiker){
		for(String key : authkeys.keySet()){
			if(authkeys.get(key).equals(gebruiker)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * kijkt of de key bestaat
	 * @param key
	 * @return true als de key bestaat anders false
	 */
	public boolean containsKey(String key){
		return authkeys.containsKey(key);
	}
	
	/**
	 * geeft de key die bij een bepalde gebruiker hoort
	 * @param gebruiker
	 * @return de key anders null
	 */
	public String getAuthKeyByUser(Gebruiker gebruiker){
		for(String key : authkeys.keySet()){
			if(authkeys.get(key).equals(gebruiker)){
				return key;
			}
		}
		return null;
	}
	
	/**
	 * geeft de gebruiker die bij een key hoort
	 * @param key
	 * @return de gebruiker als die bestaat anders null
	 */
	public Gebruiker getGebruikerByKey(String key){
		return authkeys.get(key);
	}
	
	/////////////////rating
	/**
	 * haalt de ratings op van een bepaald persoon
	 * @param gebruiker
	 * @return de ratings van die persoon
	 */
	public Rating[] getRatingsByGebruiker(Gebruiker gebruiker){
		ArrayList<Rating> ratingsByMovie = new ArrayList<Rating>();
		for(Rating r : ratings){
			if(r.getGebruiker().equals(gebruiker)){
				ratingsByMovie.add(r);
			}
		}
		Rating[] array = new Rating[ratingsByMovie.size()];
		for(int i = 0; i < ratingsByMovie.size(); i++){
			array[i] = ratingsByMovie.get(i);
		}
		return array;
	}
	
	/**
	 * probeert een rating te verweideren 
	 * @param rating de te verweideren rating
	 * @return als true is de rating verweiderd als false mislukt
	 */
	public boolean removeRating(Rating rating){
		for(Rating r : ratings){
			if(r.equals(rating)){
				ratings.remove(r);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * voegt een rating toe aan de lijst met ratings
	 * @param ratingToPost
	 */
	public void addRating(Rating ratingToPost) {
		ratings.add(ratingToPost);
	}
	
}
