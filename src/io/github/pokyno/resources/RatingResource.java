package io.github.pokyno.resources;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.JsonNode;

import io.github.pokyno.model.Gebruiker;
import io.github.pokyno.model.Model;
import io.github.pokyno.model.Movie;
import io.github.pokyno.model.Rating;

@Path("/ratings")
public class RatingResource {
	private @Context ServletContext context;
	
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getOwnRatings(@HeaderParam("auth-key") String key){
		
		Model model = (Model) context.getAttribute("model");
		if(key == null || key.isEmpty() || !model.containsKey(key)){
			return Response.status(401).build();
		}
		
		Gebruiker gebruiker = model.getGebruikerByKey(key);
		
		return Response.ok(model.getRatingsByGebruiker(gebruiker)).build();
		
	}
	//JSON///////////////////////////////////
	/*
	 * voegt een nieuwe rating toe aan een movie, als er al een rating voor die movie
	 * onder jouw naam staat wordt de rating niet nog een keer toegevoegt maar krijg
	 * je een 400 response
	 */
	@POST
	@Path("addrating/{movie}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postRating(@PathParam("movie") String moviename,@HeaderParam("auth-key") String key,JsonNode node){
		Model model = (Model) context.getAttribute("model");
		
		//getting the movie
		Movie movie = model.getMovieByName(moviename);
		
		//getting the user
		if(key == null || key.isEmpty() || !model.containsKey(key)){
			return Response.status(401).build();
		}
		Gebruiker gebruiker = model.getGebruikerByKey(key);
		
		//getting the right response
	
		Rating[] ratings = model.getRatingsByGebruiker(gebruiker);
		for(int i = 0; i < ratings.length; i++){
			if(ratings[i].getMovie().equals(movie)){
				return Response.status(400).build(); // want dan bestaat de rating al
			}
		}
		//toevogen van de rating
		try{
			
			double rating = Double.parseDouble(node.get("rating").asText());
			if(!model.correctRating(rating)){
				return Response.status(400).build();
			}
			Rating ratingToPost = new Rating(rating,gebruiker,movie);
			ratingToPost.setRating(rating);
			model.addRating(ratingToPost);
			model.updateRatings();
			URI uri = new URI("/Webtech_Api/api/ratings");
			return Response.created(uri).build();
		}catch(NumberFormatException nb){
			return Response.status(400).build();
		}catch(NullPointerException not){
			return Response.status(400).build();
		} catch (URISyntaxException e) {
			return Response.status(500).build();
		}
	}
	
	/*
	 * verandert de rating die bij een bepaalde movie hoort door een bepaald persoon aangemaakt
	 */
	@PUT
	@Path("changerating/{movie}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response changeRating(@PathParam("movie") String moviename,@HeaderParam("auth-key") String key,JsonNode node){
		Model model = (Model) context.getAttribute("model");
		
		//getting the movie
		Movie movie = model.getMovieByName(moviename);
		
		//getting the user
		if(key == null || key.isEmpty() || !model.containsKey(key)){
			return Response.status(401).build();
		}
		Gebruiker gebruiker = model.getGebruikerByKey(key);
		
		//getting the right response
		Rating ratingToEdit = null;
		Rating[] ratings = model.getRatingsByGebruiker(gebruiker);
		for(int i = 0; i < ratings.length; i++){
			if(ratings[i].getMovie().equals(movie)){
				ratingToEdit = ratings[i];
				break;
			}
		}
		//aanpassen van de rating
		try{
			double rating = Double.parseDouble(node.get("rating").asText());
			if(!model.correctRating(rating)){
				return Response.status(400).build();
			}
		
			ratingToEdit.setRating(rating);
			model.updateRatings();
			return Response.ok().entity(ratingToEdit).build();
		}catch(NumberFormatException nb){
			return Response.status(400).build();
		}catch(NullPointerException not){
			return Response.status(400).build();
		}
	}
	////Both////////////////////////////////////////////////////////////////////
	/*
	 * verweidert een rating bij een bepaalde film(door de current user gemaakt)
	 */
	@DELETE
	@Path("deleterating/{movie}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response deleteRatingJSON(@PathParam("movie") String moviename,@HeaderParam("auth-key") String key){
		Model model = (Model) context.getAttribute("model");
		
		//getting the movie
		Movie movie = model.getMovieByName(moviename);
		
		//getting the user
		if(key == null || key.isEmpty() || !model.containsKey(key)){
			return Response.status(401).build();
		}
		Gebruiker gebruiker = model.getGebruikerByKey(key);
		
		//getting the right response
		Rating ratingToDelete = null;
		Rating[] ratings = model.getRatingsByGebruiker(gebruiker);
		for(int i = 0; i < ratings.length; i++){
			if(ratings[i].getMovie().equals(movie)){
				ratingToDelete = ratings[i];
				break;
			}
		}
		if(ratingToDelete == null){
			return Response.status(400).build();
		}
		//deleten van de rating
		boolean complete = model.removeRating(ratingToDelete);
		
		if(complete){
			return Response.ok(ratingToDelete).build();
		}else{
			return Response.status(500).build();
		}
	}
	//XML/////////////////////////////////////////////////////////////////////////
	/*
	 * voegt een nieuwe rating toe aan een movie, als er al een rating voor die movie
	 * onder jouw naam staat wordt de rating niet nog een keer toegevoegt maar krijg
	 * je een 400 response
	 */
	@POST
	@Path("addrating/{movie}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response postRatingXML(@PathParam("movie") String moviename,@HeaderParam("auth-key") String key, String root){
		Model model = (Model) context.getAttribute("model");
		
		//getting the movie
		Movie movie = model.getMovieByName(moviename);
		
		//getting the user
		if(key == null || key.isEmpty() || !model.containsKey(key)){
			return Response.status(401).build();
		}
		Gebruiker gebruiker = model.getGebruikerByKey(key);
		
		//getting the right response
	
		Rating[] ratings = model.getRatingsByGebruiker(gebruiker);
		for(int i = 0; i < ratings.length; i++){
			if(ratings[i].getMovie().equals(movie)){
				return Response.status(400).build(); // want dan bestaat de rating al
			}
		}
		//toevogen van de rating
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(new StringReader(root)));
			String newRating = doc.getDocumentElement().getTextContent();
			
			double rating = Double.parseDouble(newRating);
			if(!model.correctRating(rating)){
				return Response.status(400).build();
			}
			Rating ratingToPost = new Rating(rating,gebruiker,movie);
			ratingToPost.setRating(rating);
			model.addRating(ratingToPost);
			model.updateRatings();
			URI uri = new URI("/Webtech_Api/api/ratings");
			return Response.created(uri).build();
		}catch(NumberFormatException nb){
			return Response.status(400).build();
		}catch(NullPointerException not){
			return Response.status(400).build();
		} catch (URISyntaxException e) {
			return Response.status(500).build();
		} catch (SAXException e) {
			return Response.status(500).build();
		} catch (IOException e) {
			return Response.status(500).build();
		} catch (ParserConfigurationException e) {
			return Response.status(500).build();
		}
	}
	/*
	 * verandert de rating die bij een bepaalde movie hoort door een bepaald persoon aangemaakt
	 */
	@PUT
	@Path("changerating/{movie}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response changeRatingXML(@PathParam("movie") String moviename,@HeaderParam("auth-key") String key,String root){
		Model model = (Model) context.getAttribute("model");
		
		//getting the movie
		Movie movie = model.getMovieByName(moviename);
		
		//getting the user
		if(key == null || key.isEmpty() || !model.containsKey(key)){
			return Response.status(401).build();
		}
		Gebruiker gebruiker = model.getGebruikerByKey(key);
		
		//getting the right response
		Rating ratingToEdit = null;
		Rating[] ratings = model.getRatingsByGebruiker(gebruiker);
		for(int i = 0; i < ratings.length; i++){
			if(ratings[i].getMovie().equals(movie)){
				ratingToEdit = ratings[i];
				break;
			}
		}
		//aanpassen van de rating
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(new StringReader(root)));
			String newRating = doc.getDocumentElement().getTextContent();
			double rating = Double.parseDouble(newRating);
			if(!model.correctRating(rating)){
				return Response.status(400).build();
			}
		
			ratingToEdit.setRating(rating);
			model.updateRatings();
			return Response.ok().entity(ratingToEdit).build();
		}catch(NumberFormatException nb){
			return Response.status(400).build();
		}catch(NullPointerException not){
			return Response.status(400).build();
		} catch (SAXException e) {
			return Response.status(500).build();
		} catch (IOException e) {
			return Response.status(500).build();
		} catch (ParserConfigurationException e) {
			return Response.status(500).build();
		}
	}
	
}
