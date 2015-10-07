package io.github.pokyno.resources;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.pokyno.model.Model;
import io.github.pokyno.model.Movie;
import io.github.pokyno.model.Rating;

@Path("/movies")
public class MovieResource {
	private @Context ServletContext context;
	
	/**
	 * berekend de average scores van de movie en geeft die lijst mee
	 * @return de aangepaste lijst met movie objecten
	 */
	@GET 
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getMoviesAndCorrespondingRating(){
		Model model = (Model) context.getAttribute("model");
		Movie[] movies = model.getMovies();
		for(int i = 0; i < movies.length; i++){
			Rating[] ratings = model.getRatingsByMovie(movies[i]);
			double average = 0;
			for(int j = 0; j < ratings.length; j++){
				average += ratings[j].getRating();
			}
			movies[i].setAverageScore(average/ratings.length);
		}
		return Response.ok(movies).build();
	}
}
