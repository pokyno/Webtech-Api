package io.github.pokyno.resources;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.github.pokyno.model.Model;

@Path("/gebruikers")
public class GebruikerResource {
	private @Context ServletContext context;
	
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getgebruikers(){
		Model model = (Model) context.getAttribute("model");
		return Response.ok(model.getGebruikers()).build();
	}
}
