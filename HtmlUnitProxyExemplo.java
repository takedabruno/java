package logDownloader;

import java.io.File;
import java.net.Authenticator;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import Utils.CustomAuthenticator;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
//MAVEN DEPENDENCY
//<dependency>
//<groupId>net.sourceforge.htmlunit</groupId>
//<artifactId>htmlunit</artifactId>
//<version>2.23</version>
//</dependency>

public class HtmlUnitProxyExemplo {
	public void homePage() throws Exception {
	    try (final WebClient webClient = new WebClient()) {
	        final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
	        final String pageAsXml = page.asXml();
	        final String pageAsText = page.asText();
	    }
	}
	
	public void basicAuthentication(WebClient webClient, String username, String password) {
        //BASIC AUTHENTICATION DA LI1630
        DefaultCredentialsProvider basicAuth = new DefaultCredentialsProvider();
        basicAuth.addCredentials(username, password);
        webClient.setCredentialsProvider(basicAuth);
	}
	
	
	public String dataString() {
		Date data = new Date();
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy.MM.dd");
		return formatador.format( data );
	}
	
	
	//---------------------------------------------------------------------------------------------
	//Autenticação para download de arquivo
	
	public void downloadFile(URL url, File downloadedFile) throws Exception{
	    Authenticator.setDefault(new CustomAuthenticator());
	    FileUtils.copyURLToFile(url, downloadedFile);
	    System.out.println("LOG COPIADO : " + downloadedFile.getAbsolutePath().toString());
	}
}