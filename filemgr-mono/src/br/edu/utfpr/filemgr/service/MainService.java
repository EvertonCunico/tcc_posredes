package br.edu.utfpr.filemgr.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
		this.caminhoPasta = "D:/";
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
	@Path("download/nomearquivo={nomearquivo}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@ApiOperation("download")
	public Response download(@PathParam("nomearquivo") String nomearquivo) throws Exception {
		return this.getFilemgrbo().download(nomearquivo);
	}

	@POST
	@Consumes({ MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_OCTET_STREAM })
	@Produces(MediaType.APPLICATION_JSON)
	@Path("upload/nomearquivo={nomearquivo}/status={status}")
	@ApiOperation("upload")
	public Response upload(byte[] database, @PathParam("nomearquivo") String filename, @PathParam("status") String status) {
		return this.getFilemgrbo().upload(database, filename, status);
	}

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		String uploadedFileLocation = "D:/uploaded/" + fileDetail.getFileName();

		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);

		String output = "File uploaded to: " + uploadedFileLocation;

		return Response.status(200).entity(output).build();

	}

	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
