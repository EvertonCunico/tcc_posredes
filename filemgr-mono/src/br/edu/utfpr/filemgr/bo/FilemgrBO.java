package br.edu.utfpr.filemgr.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

public class FilemgrBO {

	String caminhopasta = "C:/";

	public FilemgrBO(String caminhopasta) {
		this.caminhopasta = caminhopasta;
	}

	public String getCaminhopasta() {
		return caminhopasta;
	}

	public void setCaminhopasta(String caminhopasta) {
		this.caminhopasta = caminhopasta;
	}

	public Response download(String nomearquivo) throws Exception {
		File file;
		try {
			file = new File(this.getCaminhopasta() + nomearquivo);
		} catch (Exception e) {
			throw new Exception("Falha ao buscar diretório!");
		}
		if (file.exists()) {
			return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
					.header("content-disposition","attachment; filename = " + nomearquivo)
					.build();
		} else {
			return Response.noContent().build();
		}
	}

	public Response upload(InputStream uploadedInputStream, FormDataContentDisposition fileDetail) {
		String uploadedFileLocation = this.getCaminhopasta() + fileDetail.getFileName();

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
