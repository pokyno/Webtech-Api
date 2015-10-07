package io.github.pokyno.resources;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	public Response getGebruikers(){
		Model model = (Model) context.getAttribute("model");
		return Response.ok(model.getGebruikers()).build();
	}
	
	@GET
	@Path("{id}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getSpecifiekeGebruiker(@PathParam("id") int id){
		Model model = (Model) context.getAttribute("model");
		return Response.ok(model.getGebruikers()).build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_XML})
	public Response addGebruiker(){
		//TODO nog afmaken
		return null;
	}
}
