package br.edu.utfpr.filemgr.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

@Path("/filemgr")
public class MainService {

	private static final String CHARSET_UTF8 = ";charset=utf-8";

	@GET
	@Path("teste")
	@Produces(MediaType.TEXT_PLAIN)
	public Response teste() {
		return Response.ok("Teste OK").build();
	}

	@GET
	@Path("download/{nomearquivo}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response download(@PathParam("nomearquivo") String nomearquivo) {
		StreamingOutput fileStream =  new StreamingOutput()
		{
			@Override
			public void write(java.io.OutputStream output) throws IOException, WebApplicationException
			{
				try
				{
					java.nio.file.Path path = Paths.get("N:/Servidor/" + nomearquivo);
					byte[] data = Files.readAllBytes(path);
					output.write(data);
					output.flush();
				}
				catch (Exception e)
				{
					throw new WebApplicationException("Arquivo não encontrado!");
				}
			}
		};
		return Response
				.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
				.header("content-disposition","attachment; filename = " + nomearquivo)
				.build();
	}
}
