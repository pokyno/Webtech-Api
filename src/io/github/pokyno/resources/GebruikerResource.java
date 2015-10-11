package io.github.pokyno.resources;


import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;

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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.JsonNode;

import io.github.pokyno.model.Gebruiker;
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
	@Path("/adduser")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response addGebruikerJSON(JsonNode node){
		Model model = (Model) context.getAttribute("model");
		try{
			String achternaam = node.get("achternaam").asText();
			String tussenvoegsels = node.get("tussenvoegsels").asText();
			String voornaam = node.get("voornaam").asText();
			String nickname = node.get("nickname").asText();
			String wachtwoord = node.get("wachtwoord").asText();
			
			//controle of de juiste velden compleet zijn ingevuld
			if(achternaam.isEmpty()){
				return Response.status(400).build();
			}
			
			if(voornaam.isEmpty()){
				return Response.status(400).build();
			}
			
			if(nickname.isEmpty()){
				return Response.status(400).build();
			}
			
			if(wachtwoord.isEmpty()){
				return Response.status(400).build();
			}
			
			if(model.getGebruikerByNickname(nickname) != null){
				return Response.status(400).build();
			}
			
			if(tussenvoegsels == null){
				model.addGebruiker(new Gebruiker(achternaam,"",voornaam,nickname,wachtwoord));
			}else{
				model.addGebruiker(new Gebruiker(achternaam,tussenvoegsels,voornaam,nickname,wachtwoord));
			}
			URI uri = new URI("/Webtech_Api/api/gebruikers/"+nickname);
			
			return Response.created(uri).build();
			
		}catch(NullPointerException n){
			return Response.status(400).build();
		} catch (URISyntaxException e) {
			return Response.status(500).build();
		}
	}
	
	@POST
	@Path("/adduser")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response addGebruikerXML(String root){
		Model model = (Model) context.getAttribute("model");
		
		try{
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(new StringReader(root)));
			NodeList children = doc.getDocumentElement().getChildNodes();
			
			Gebruiker gebruiker = new Gebruiker();
			
			for(int i = 0; i < children.getLength(); i++){
				if(children.item(i).getNodeName().equals("achternaam")){
					gebruiker.setAchternaam(children.item(i).getTextContent());
				}
				else if(children.item(i).getNodeName().equals("voornaam")){
					gebruiker.setVoornaam(children.item(i).getTextContent());
				}
				else if(children.item(i).getNodeName().equals("tussenvoegsels")){
					gebruiker.setTussenvoegsels(children.item(i).getTextContent());
				}
				else if(children.item(i).getNodeName().equals("nickname")){
					gebruiker.setNickname(children.item(i).getTextContent());
				}
				else if(children.item(i).getNodeName().equals("wachtwoord")){
					gebruiker.setWachtwoord(children.item(i).getTextContent());
				}
			}
		
			//controle of de juiste velden compleet zijn ingevuld
			if(gebruiker.getAchternaam().isEmpty()){
				return Response.status(400).build();
			}
			
			if(gebruiker.getVoornaam().isEmpty()){
				return Response.status(400).build();
			}
			
			if(gebruiker.getNickname().isEmpty()){
				return Response.status(400).build();
			}
			
			if(gebruiker.getWachtwoord().isEmpty()){
				return Response.status(400).build();
			}
			
			if(model.getGebruikerByNickname(gebruiker.getNickname()) != null){
				return Response.status(400).build();
			}
			
			model.addGebruiker(gebruiker);
			
			URI uri = new URI("/Webtech_Api/api/gebruikers/"+gebruiker.getNickname());
			
			return Response.created(uri).build();
			
		}catch(NullPointerException n){
			return Response.status(400).build();
		}catch (URISyntaxException e) {
			return Response.status(400).build();
		} catch (SAXException e) {
			return Response.status(400).build();
		} catch (IOException e) {
			return Response.status(400).build();
		} catch (ParserConfigurationException e) {
			return Response.status(400).build();
		}
	}
	
}
