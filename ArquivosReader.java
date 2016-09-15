package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ArquivosReader {
	
	/**
	 * <p>Faz a leitura de um arquivo e retorna todas as linhas em uma ArrayList<String><p>
	 * 
	 * @param caminho
	 * @param arquivo
	 * @return
	 */
	public ArrayList<String> arquivoLinhas(File arquivo) {
		String linha = "";
		ArrayList<String> arquivoLinhas = new ArrayList<String>();
		FileReader arq;
		try {
			arq = new FileReader(arquivo);
			BufferedReader lerArq = new BufferedReader(arq);
			try {
				while (linha != null) {
					linha = lerArq.readLine();
					arquivoLinhas.add(linha);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			arq.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Arquivo " + arquivo.getAbsolutePath() + " não existe: " + e);
		}
		return arquivoLinhas;
	}
	
}
