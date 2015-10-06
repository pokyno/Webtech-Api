package io.github.pokyno.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement
public class Rating {
	private double rating; // 0.5 t/m 5
	private Gebruiker gebruiker;
	private Movie movie;
	
	public Rating(){
	}

	/**
	 * @param rating
	 * @param gebruiker
	 * @param movie
	 */
	public Rating(double rating, Gebruiker gebruiker, Movie movie) {
		this.rating = rating;
		this.gebruiker = gebruiker;
		this.movie = movie;
	}

	/**
	 * @return the rating
	 */
	@XmlElement
	public double getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}

	/**
	 * @return the gebruiker
	 */
	@XmlTransient
	@JsonIgnore
	public Gebruiker getGebruiker() {
		return gebruiker;
	}

	/**
	 * @param gebruiker the gebruiker to set
	 */
	public void setGebruiker(Gebruiker gebruiker) {
		this.gebruiker = gebruiker;
	}

	/**
	 * @return the movie
	 */
	@XmlElement
	public Movie getMovie() {
		return movie;
	}

	/**
	 * @param movie the movie to set
	 */
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	
	
	
}
