package br.edu.utfpr.filemgr.service;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import br.edu.utfpr.filemgr.bo.FilemgrBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("FileMGR")
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
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation("Teste de conexão com API")
	public Response teste() {
		return Response.ok("Teste OK").build();
	}

	@GET
	@Path("download")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@ApiOperation("download")
	public Response download(@QueryParam("nomearquivo") String nomearquivo) throws Exception {
		return this.getFilemgrbo().download(nomearquivo);
	}

	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		return this.getFilemgrbo().upload(uploadedInputStream, fileDetail);

	}

}
