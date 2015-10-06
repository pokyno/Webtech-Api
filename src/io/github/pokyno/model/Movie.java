package io.github.pokyno.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
@XmlRootElement
public class Movie {
	private static int counterId = 0, counterImdb = 0;
	private int id, imdb, duur;
	private String titel, regiseur, beschrijving, date;
	private double averageScore = 0; //0 betekend nog niet gerate
	
	public Movie(){
		
	}
	
	
	/**
	 * @param id
	 * @param imdb
	 * @param duur
	 * @param titel
	 * @param regiseur
	 * @param beschrijving
	 * @param date
	 */
	public Movie(int duur, String titel, String regiseur, String beschrijving, String date) {
		this.id = Movie.counterId;
		this.imdb = Movie.counterImdb;
		Movie.counterId++;
		Movie.counterImdb++;
		
		this.duur = duur;
		this.titel = titel;
		this.regiseur = regiseur;
		this.beschrijving = beschrijving;
		this.date = date;
	}


	/**
	 * @return the id
	 */
	@XmlTransient
	@JsonIgnore
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the imdb
	 */
	@XmlElement
	public int getImdb() {
		return imdb;
	}
	/**
	 * @param imdb the imdb to set
	 */
	public void setImdb(int imdb) {
		this.imdb = imdb;
	}
	/**
	 * @return the duur
	 */
	@XmlElement
	public int getDuur() {
		return duur;
	}
	/**
	 * @param duur the duur to set
	 */
	public void setDuur(int duur) {
		this.duur = duur;
	}
	/**
	 * @return the titel
	 */
	@XmlElement
	public String getTitel() {
		return titel;
	}
	/**
	 * @param titel the titel to set
	 */
	public void setTitel(String titel) {
		this.titel = titel;
	}
	/**
	 * @return the regiseur
	 */
	@XmlElement
	public String getRegiseur() {
		return regiseur;
	}
	/**
	 * @param regiseur the regiseur to set
	 */
	public void setRegiseur(String regiseur) {
		this.regiseur = regiseur;
	}
	/**
	 * @return the beschrijving
	 */
	@XmlElement
	public String getBeschrijving() {
		return beschrijving;
	}
	/**
	 * @param beschrijving the beschrijving to set
	 */
	public void setBeschrijving(String beschrijving) {
		this.beschrijving = beschrijving;
	}
	/**
	 * @return the date
	 */
	@XmlElement
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	
	public void setAverageScore(double newAverage){
		this.averageScore = newAverage;
	}
	
	public double getAverageScore(){
		return averageScore;
	}
	
	@Override
	public String toString() {
		return titel;
	}
	
	
}
