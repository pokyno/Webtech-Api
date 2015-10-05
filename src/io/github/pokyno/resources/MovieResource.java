package io.github.pokyno.resources;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import io.github.pokyno.model.Model;

@Path("/movies")
public class MovieResource {
	private @Context ServletContext context;
	private Model model = (Model) context.getAttribute("model");
	
	@GET
	@Path("{id}")
	@Produces({MediaType.TEXT_PLAIN})
	public String getCustomer(@PathParam("id") int id){
		return model.getMovie(id).toString();
	}
}
