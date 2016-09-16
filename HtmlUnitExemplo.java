package logDownloader;

import java.io.File;
import java.net.Authenticator;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import Utils.CustomAuthenticator;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

//MAVEN DEPENDENCY
//<dependency>
//<groupId>net.sourceforge.htmlunit</groupId>
//<artifactId>htmlunit</artifactId>
//<version>2.23</version>
//</dependency>

public class HtmlUnitExemplo {
	public void htmlUnitProxyExemplo(String username, 
									 String password, 
									 String workstation, 
									 String domain, 
									 String myproxyserver, 
									 int myProxyPort,
									 String webPage
									 )throws Exception {
		
		//Workstation normalmente � "localhost"
		//Webpage = "http://uol.com.br" ou "http://192.168.0.1"
	    
	    try (final WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER, myproxyserver, myProxyPort)) {

	        //set proxy username and password 
	        final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();     
	        
	        //Autentica��o do proxy
	        credentialsProvider.addNTLMCredentials(username, password, null, -1, workstation, domain);
	        
	        //faz basic authentication se necess�rio
	        basicAuthentication(webClient, username, password);
	        
	        //Javascript desligado -- IMPORTANTE!!!!!!
	        // A maioria dos scripts javascript d�o erro pelo htmlunit
	        webClient.getOptions().setJavaScriptEnabled(false);
	        
	        //PAGINA 1 - SELE��O DE EQUIPE
	        System.out.println("PAGINA 01 \n---------");
	        
	        //Conex�o com a p�gina
	        final HtmlPage page = webClient.getPage(webPage);
	        //String pageText = page.asText();
	        //System.out.println(pageText);

	        //Como o formul�rio � �nico na p�gina, pegamos o form pelo ID atrav�s de get(0)
	        final HtmlForm form = page.getForms().get(0);
	        
	        //Pegamos os atributos dos radio buttons atrav�s do nome "equipe" setado no input e printamos o Value
	        List<HtmlRadioButtonInput> radioButtonsEquipes = form.getRadioButtonsByName("equipe");
	        for (HtmlRadioButtonInput radioName : radioButtonsEquipes){
	        	System.out.println(radioName.getValueAttribute().toString());
	        }

	        //Setamos um objeto HtmlRadioButtonInput atr�v�s do valor "Mackenzie"
	        final HtmlRadioButtonInput radioButton = form.getInputByValue("Mackenzie");
	        
	        //Simula��o de click do bot�o Mackenzie
	        radioButton.click();
	        final HtmlSubmitInput button = form.getInputByValue("Ver Tecnologias");

	        //Carrega outra p�gina atrav�s do clique do bot�o ou pode ser atrav�s do clique de um link
	        final HtmlPage page2 = button.click();
	        

	        System.out.println("==================================================================================================");
	        
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
	//Autentica��o para download de arquivo
	
	public void downloadFile(URL url, File downloadedFile) throws Exception{
	    Authenticator.setDefault(new CustomAuthenticator());
	    FileUtils.copyURLToFile(url, downloadedFile);
	    System.out.println("LOG COPIADO : " + downloadedFile.getAbsolutePath().toString());
	}
}