package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class ArquivosUtils {
	/**
	 * <p>Copia uma lista de arquivos ArrayList<File> para um determinado diretório</p>
	 * 
	 * @param arquivosLista : preferencialmente originado do método "listagemArquivosDiretorio"
	 * @param diretorio : String Exemplo: "C:\\pasta\\subpasta1\\subpasta2\\"
	 * @throws ParseException
	 */
	public void copiaListaArquivos(ArrayList<File> arquivosLista, String diretorio) throws ParseException {	
		System.out.println("\n----------------------------------------\n");
		System.out.println("INICIANDO CÓPIA DE ARQUIVOS\n");
		System.out.println("----------------------------------------\n");
		
		for (File arquivo : arquivosLista){
			try {
				String nomeArquivo = arquivo.getName();
				this.copyFile(arquivo, new File(diretorio + nomeArquivo));
				System.out.println("Copiando para: " + diretorio + nomeArquivo);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		System.out.println("\n----------------------------------------\n");
		System.out.println("CÓPIA DE ARQUIVOS FINALIZADA \n");
		System.out.println("----------------------------------------\n");
		System.out.println("\n\n\n");
	}
	
	
	/**
	 * <p>
	 * Retona uma ArrayList<File> de um determinado caminho de diretório retornando
	 * os arquivos de subdiretórios também.
	 * </p>
	 * 
	 * @param diretorio : String Caminho de diretório. Ex.: "C:\\pasta\\subpasta1\\subpasta2\\"
	 * @param ListarSubdiretorios : Boolean true/false para listagem de subdiretórios.
	 * @return
	 */
	public ArrayList<File> listagemArquivosDiretorio(String diretorio, Boolean ListarSubdiretorios){
		File[]          arquivosPasta = new File(diretorio).listFiles();
		ArrayList<File> arquivosLista = new ArrayList<File>();

		if (arquivosPasta == null || arquivosPasta.length == 0) {
			System.out.println("NAO FORAM ENCONTRADOS ARQUIVOS NO DIRETORIO: " + diretorio);
		} else {
			    for (File file : arquivosPasta) {
			        if (file.isFile()) {
			        	System.out.println(file.getAbsolutePath());
			            arquivosLista.add(file);
			        } else if (file.isDirectory()) {
			        	if (ListarSubdiretorios == true){
			        		listagemArquivosSubdiretorios(file.getAbsolutePath(), arquivosLista);	
			        	}
			        }
			    }
		}
		System.out.println("----------------------------------------------------------------------------------------------------\n\n\n");
		return arquivosLista;
	}
	
	
	
	
	/**
	 * <p>Complemento do método listagemArquivosDiretorio.</p> 
	 * 
	 * <p>
	 * Quando identificado que um elemento da subpasta é uma pasta, 
	 * é gerado uma listagem desta e adicionado ao ArrayList "listagemArquivos"
	 * e retorna para o método listagemArquivosDiretorio.
	 * </p>
	 * 
	 * @param caminhoDiretorio : String Exemplo: "C:\\pasta\\subpasta1\\subpasta2\\"
	 * @param listagemArquivos : ArrayList<File> arquivosLista vinda do método listagemArquivosDiretorio
	 */
	private void listagemArquivosSubdiretorios(String caminhoDiretorio, ArrayList<File> listagemArquivos) {
		File diretorio = new File(caminhoDiretorio);

	    // get all the files from a directory
	    File[] fileList = diretorio.listFiles();
	    
	    for (File file : fileList) {
	        if (file.isFile()) {
	        	System.out.println(file.getAbsolutePath());
	        	listagemArquivos.add(file);
	        } else if (file.isDirectory()) {
	        	listagemArquivosSubdiretorios(file.getAbsolutePath(), listagemArquivos);
	        }
	    }
	}
	
	/** 
	 * <p> Filtra lista de arquivos a partir do prefixo e da extensao de arquivo.<p>
	 * 
	 * @param arquivosListagem
	 * @param arquivoPrefixo
	 * @param arquivoExtensao
	 * @return
	 */
	public ArrayList<File> filtroListagemArquivosNome(ArrayList<File> arquivosListagem, String arquivoPrefixo, String arquivoExtensao) {
		System.out.println("Aplicando filtro de arquivos: " + arquivoPrefixo);
		System.out.println("Aplicando filtro de extensao: " + arquivoExtensao);
		System.out.println("----------------------------------------------------------------------------------------------------\n\n\n");
		ArrayList<File> arquivosListagemFiltrada = new ArrayList<File>();
		for (File arquivos : arquivosListagem){
			if (arquivos.getName().startsWith(arquivoPrefixo) && arquivos.getName().endsWith(arquivoExtensao)){
				arquivosListagemFiltrada.add(arquivos);
			}
		}
		return arquivosListagemFiltrada;
	}
	
	/**
	 * <p>Filtra arquivos através da propriedade lastModified</p>
	 * 
	 * @param arquivosListagem
	 * @param periodoRelatorio : período em dias (int) em que o filtro é aplicado sobre a lista de arquivos. Ex.: Para até 45 dias atrás, coloque 45.
	 * @return
	 * @throws Exception
	 */
	public ArrayList<File> filtroListagemArquivosData(ArrayList<File> arquivosListagem, int dias) throws Exception {
		int periodoRelatorio = -dias;
		ArrayList<File> arquivosListagemFiltrada = new ArrayList<File>();
		
		Date     hoje     = DateUtils.getData(DateUtils.getDataAtualString("dd/MM/yyyy"));
		Date     periodo  = new Date();
		periodo = DateUtils.maisN(hoje, periodoRelatorio, Calendar.DAY_OF_MONTH);
		String periodoStr = DateUtils.getData(periodo);
		Long periodoLong = DateUtils.formataDataMillis(periodoStr);
		
		System.out.println("Filtrando arquivos a partir de " + periodoStr + "\n");
		
		for (File arquivo : arquivosListagem){
			if (arquivo.lastModified() >= periodoLong){
				System.out.println(arquivo.getAbsolutePath());
				arquivosListagemFiltrada.add(arquivo);
			}
		}
		
		System.out.println("----------------------------------------------------------------------------------------------------\n\n\n");
		return arquivosListagemFiltrada;
	}
	
	public void copyFile(File source, File destination) throws IOException {
		File diretorio = new File(destination.getParent()); 
		if (!diretorio.exists()){
			System.out.println("Diretorio não existe!!!");
			new File(destination.getParent()).mkdir();
			System.out.println("Diretório criado : " + destination.getParent());
		}
		
		if (destination.exists()){
			destination.delete();
		}
		
		FileChannel sourceChannel = null;
		FileChannel destinationChannel = null;
		try {
			sourceChannel = new FileInputStream(source).getChannel();
			destinationChannel = new FileOutputStream(destination).getChannel();
			sourceChannel.transferTo(0, sourceChannel.size(),
					destinationChannel);
		} finally {
			if (sourceChannel != null && sourceChannel.isOpen())
				sourceChannel.close();
			if (destinationChannel != null && destinationChannel.isOpen())
				destinationChannel.close();
		}
	}
}
