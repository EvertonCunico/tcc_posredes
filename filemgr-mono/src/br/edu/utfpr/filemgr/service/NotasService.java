package br.edu.utfpr.filemgr.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/filemgr")
public class NotasService {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";

	@GET
	@Path("teste")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Response listarNotas() {
		return Response.ok().build();
	}
	
}
