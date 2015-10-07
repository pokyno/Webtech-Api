package io.github.pokyno.resources;

import javax.servlet.ServletContext;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import io.github.pokyno.model.Gebruiker;
import io.github.pokyno.model.Model;

@Path("/auth")
public class AuthResource {
	@Context ServletContext context;
	
	@POST
	public Response validateUser(@HeaderParam("username") String username, @HeaderParam("password") String password){
		Model model = (Model) context.getAttribute("model");
		Gebruiker gebruiker = model.getGebruikerByNickname(username);
		if(gebruiker == null){
			return Response.status(412).build();
		}else if(!gebruiker.getWachtwoord().equals(password)){
			return Response.status(412).build();
		}
		return Response.ok(username + " " + password).build();
	}
}
