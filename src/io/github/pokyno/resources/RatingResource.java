package io.github.pokyno.resources;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.apple.eawt.Application;

import io.github.pokyno.model.Gebruiker;
import io.github.pokyno.model.Model;
import io.github.pokyno.model.Movie;
import io.github.pokyno.model.Rating;

@Path("/ratings")
public class RatingResource {
	private @Context ServletContext context;
	
	//TODO maak hier een post aan voor het posten van een rating
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getOwnRatings(@HeaderParam("auth-key") String key){
		
		Model model = (Model) context.getAttribute("model");
		if(key.isEmpty() || key == null || !model.containsKey(key)){
			return Response.status(401).build();
		}
		
		Gebruiker gebruiker = model.getGebruikerByKey(key);
		
		return Response.ok(model.getRatingsByGebruiker(gebruiker)).build();
		
	}
	
}
