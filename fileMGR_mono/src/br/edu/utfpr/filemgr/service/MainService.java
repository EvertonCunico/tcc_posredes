package br.edu.utfpr.filemgr.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("main")
public class MainService {
	
	@GET
	@Path("check")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response check() throws Exception {
		return Response.ok("Teste").build();
	}

}
