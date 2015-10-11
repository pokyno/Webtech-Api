package io.github.pokyno.model;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
@XmlRootElement
public class Gebruiker {
	private String achternaam, tussenvoegsels, voornaam, nickname, wachtwoord;
	
	/**
	 * empty constructor
	 */
	public Gebruiker(){
	}

	/**
	 * @param achternaam
	 * @param tussenvoegsels
	 * @param voornaam
	 * @param nickname
	 * @param wachtwoord
	 */
	public Gebruiker(String achternaam, String tussenvoegsels, String voornaam, String nickname, String wachtwoord) {
		this.achternaam = achternaam;
		this.tussenvoegsels = tussenvoegsels;
		this.voornaam = voornaam;
		this.nickname = nickname;
		this.wachtwoord = wachtwoord;
	}

	/**
	 * @return the achternaam
	 */
	@XmlElement
	public String getAchternaam() {
		return achternaam;
	}

	/**
	 * @param achternaam the achternaam to set
	 */
	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	/**
	 * @return the tussenvoegsels
	 */
	@XmlElement
	public String getTussenvoegsels() {
		return tussenvoegsels;
	}

	/**
	 * @param tussenvoegsels the tussenvoegsels to set
	 */
	public void setTussenvoegsels(String tussenvoegsels) {
		this.tussenvoegsels = tussenvoegsels;
	}

	/**
	 * @return the voornaam
	 */
	@XmlElement
	public String getVoornaam() {
		return voornaam;
	}

	/**
	 * @param voornaam the voornaam to set
	 */
	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	/**
	 * @return the nickname
	 */
	@XmlElement
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the wachtwoord
	 */
	@XmlTransient
	@JsonIgnore
	public String getWachtwoord() {
		return wachtwoord;
	}

	/**
	 * @param wachtwoord the wachtwoord to set
	 */
	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}
	
	@Override
	public String toString(){
		return voornaam +" "+achternaam+" "+nickname+" "+wachtwoord;
	}
	
	
}
