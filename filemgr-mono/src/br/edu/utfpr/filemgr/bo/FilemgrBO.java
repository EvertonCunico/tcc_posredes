package br.edu.utfpr.filemgr.bo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

	public Response upload(byte[] arquivo, String filename, String status) {

		if (status.equalsIgnoreCase("F")) {
			new File(this.getCaminhopasta() + filename + ".temp").renameTo(new File(this.getCaminhopasta() + filename));
			return Response.ok().build();
		}

		OutputStream outputStream = null;
		try {

			int initRemove = 0;
			int fimRemove = 0;
			InputStreamReader entradaFormatada = new InputStreamReader(new ByteArrayInputStream(arquivo));
			BufferedReader entradaString = new BufferedReader(entradaFormatada);
			String linha = entradaString.readLine();
			boolean fimInit = false;
			while (linha != null) {
				if (!fimInit) {
					fimInit = linha.equalsIgnoreCase("Content-Transfer-Encoding: binary");
					initRemove += linha.getBytes().length + 2;
				}
				fimRemove = linha.getBytes().length + 4;
				linha = entradaString.readLine();
			}

			byte[] filteredByteArray = Arrays.copyOfRange(arquivo, initRemove + 2, arquivo.length - fimRemove);

			outputStream = new FileOutputStream(new File(this.getCaminhopasta() + filename + ".temp"), status.equalsIgnoreCase("I") ? false : true);
			outputStream.write(filteredByteArray);

			return Response.ok().build();

		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Não foi possível salvar arquivo! Motivo: " + e.getMessage()).build();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception e) {
				}
			}
		}
	}

}
