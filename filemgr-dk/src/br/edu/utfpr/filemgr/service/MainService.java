package br.edu.utfpr.filemgr.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.edu.utfpr.filemgr.bo.FilemgrBO;

@Path("/filemgr")
public class MainService {

	private String caminhoPasta;

	FilemgrBO filemgrbo;

	public MainService() {
		this.caminhoPasta = "N:/arquivos/";
		this.filemgrbo = new FilemgrBO(this.caminhoPasta);
	}

	public FilemgrBO getFilemgrbo() {
		if (filemgrbo == null) {
			filemgrbo = new FilemgrBO(this.caminhoPasta);
		}
		return filemgrbo;
	}

	public void setFilemgrbo(FilemgrBO filemgrbo) {
		this.filemgrbo = filemgrbo;
	}

	public String getCaminhoPasta() {
		return caminhoPasta;
	}

	public void setCaminhoPasta(String caminhoPasta) {
		this.caminhoPasta = caminhoPasta;
	}

	//private static final String CHARSET_UTF8 = ";charset=utf-8";

	@GET
	@Path("teste")
	@Produces(MediaType.APPLICATION_JSON)
	public Response teste() {
		return Response.ok("teste").build();
	}

}
