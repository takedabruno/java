package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ParametrosUtil {
	String caminhoParametros = "./resources/parametrosGerarRelatorio.properties";
	
	public Properties getProp(String caminhoArquivo) {
		Properties property = new Properties();
		FileInputStream file = null;
		try {
			file = new FileInputStream(caminhoArquivo);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			property.load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//props.getProperty("nome da propriedade");
		return property;
	}
	
	public String getPropriedade (String propriedadeKey) {
		Properties propriedades = getProp(caminhoParametros);
		String valorPropriedade = propriedades.getProperty(propriedadeKey);
		
		return valorPropriedade;
	}
}
