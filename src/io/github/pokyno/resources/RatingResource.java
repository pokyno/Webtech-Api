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

@Path("/ratings")
public class RatingResource {
	private @Context ServletContext context;
	
	//TODO maak hier een post aan voor het posten van een rating
}
