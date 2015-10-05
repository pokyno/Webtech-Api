package io.github.pokyno.webtech;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.ResourceConfig;

import io.github.pokyno.model.Model;

@ApplicationPath("api")
public class MyRest extends ResourceConfig{
	private @Context ServletContext context;
	
	public MyRest(){
		context.setAttribute("model", new Model());
		packages("io.github.pokyno.resources");
	}
}
