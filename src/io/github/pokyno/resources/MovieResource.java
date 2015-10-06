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

@Path("/movies")
public class MovieResource {
	private @Context ServletContext context;
	
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getMovie(@PathParam("id") int id){
		Model model = (Model) context.getAttribute("model");
		return Response.ok(model.getMovie(id)).build();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getMovies(){
		Model model = (Model) context.getAttribute("model");
		return Response.ok(model.getMovies()).build();
	}
	
}
