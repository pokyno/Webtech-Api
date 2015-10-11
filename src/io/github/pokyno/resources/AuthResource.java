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
		//haal de gebruiker op 
		Gebruiker gebruiker = model.getGebruikerByNickname(username);
		//kijken of die wel bestaat en of het wachtwoord erbij klopt
		if(gebruiker == null){
			return Response.status(412).build();
		}else if(!gebruiker.getWachtwoord().equals(password)){
			return Response.status(412).build();
		}
		//kijkt dan of de gebruiker al een key heeft zo nee maakt hij er een aan
		if(!model.hasKey(gebruiker)){
			model.setKey(gebruiker);
			return Response.ok().header("auth-key", model.getAuthKeyByUser(gebruiker)).build();
		}else{
			return Response.ok().header("auth-key", model.getAuthKeyByUser(gebruiker)).build();
		}
	}
}
