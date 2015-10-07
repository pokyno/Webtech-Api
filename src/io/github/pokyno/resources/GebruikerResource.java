package io.github.pokyno.resources;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
	public Response getGebruikers(@HeaderParam("auth-key") String key){
		Model model = (Model) context.getAttribute("model");
		if(key == null || !model.containsKey(key)){
			return Response.status(401).build();
		}
		return Response.ok(model.getGebruikers()).build();
	}
	
	@GET
	@Path("{nickname}")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response getSpecifiekeGebruiker(@PathParam("nickname") String nickname,@HeaderParam("auth-key") String key){
		Model model = (Model) context.getAttribute("model");
		if(key.isEmpty() || key == null || !model.containsKey(key)){
			return Response.status(401).build();
		}
		
		if(model.getGebruikerByNickname(nickname) == null){
			return Response.status(400).build();
		}
		
		return Response.ok(model.getGebruikerByNickname(nickname)).build();
	}
	
	
	
	@POST
	@Consumes({MediaType.APPLICATION_XML})
	public Response addGebruiker(){
		//TODO nog afmaken
		return null;
	}
}
