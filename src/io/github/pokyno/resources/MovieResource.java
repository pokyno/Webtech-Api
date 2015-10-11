package io.github.pokyno.resources;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.pokyno.model.Model;
import io.github.pokyno.model.Movie;


@Path("/movies")
public class MovieResource {
	private @Context ServletContext context;
	
	/**
	 * berekend de average scores van de movie en geeft die lijst mee
	 * @return de aangepaste lijst met movie objecten
	 */
	@GET 
	@Path("/averageratings")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getMoviesAndCorrespondingRating(){
		Model model = (Model) context.getAttribute("model");
		Movie[] movies = model.getMovies();
		ArrayList<Movie> tempMovies = new ArrayList<>();
		model.updateRatings();
		for(int i = 0; i < movies.length; i++){
			if(movies[i].getAverageScore() != 0){
				tempMovies.add(movies[i]);
			}
		}
		Movie[] returnMovies = new Movie[tempMovies.size()];
		for(int i = 0; i < tempMovies.size(); i++){
			returnMovies[i] = tempMovies.get(i);
		}
		
		return Response.ok(returnMovies).build();
	}
	
	/**
	 * geeft alle movies ook die geen rating hebben
	 */
	@GET 
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getMovies(){
		Model model = (Model) context.getAttribute("model");
		Movie[] movies = model.getMovies();
		model.updateRatings();
		return Response.ok(movies).build();
	}
}
