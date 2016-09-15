package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

public class ArquivosWriter {
	public void writer(String texto, String caminho, String nomeArquivo) {
		Writer arq = null;
		try {
			arq = new FileWriter(caminho + nomeArquivo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter gravarArq = new PrintWriter(arq);
		gravarArq.printf(texto);
		try {
			arq.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writer(String texto, File file) {
		Writer arq = null;
		try {
			arq = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter gravarArq = new PrintWriter(arq);
		gravarArq.printf(texto);
		try {
			arq.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writerAddLinhas(String linha, final String diretorio, final String nomeArquivo){

		try {
			final String diretorioNomeArquivoLog =  diretorio + nomeArquivo;

			final FileWriter writer = new FileWriter(new File(diretorioNomeArquivoLog),true);
			final BufferedWriter escritor = new BufferedWriter(writer);

			escritor.write(linha);
			escritor.newLine();

			escritor.close();
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro ao anexar a linha " + linha  + " no arquivo de criticas");
		}
	}
	
	public void writerAddLinhas(ArrayList<String> linhas, File arquivo){
		FileWriter writer;
		try {
			writer = new FileWriter(arquivo, false);
			final BufferedWriter escritor = new BufferedWriter(writer);
			
			for (String linha : linhas){
				try {
					escritor.write(linha);
					escritor.newLine();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			escritor.close();
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
