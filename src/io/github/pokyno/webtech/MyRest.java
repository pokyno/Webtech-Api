package io.github.pokyno.webtech;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.ResourceConfig;


@ApplicationPath("/api")
public class MyRest extends ResourceConfig{
	private @Context ServletContext context;
	
	public MyRest(){
		super();
		packages("io.github.pokyno.resources");
		register(com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider.class);	
	}
}
