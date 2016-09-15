package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

/**
 * Classe utilit�ria de formatos.
 */
public class FormatoUtil {

	private FormatoUtil() {
	}

	/**
	 * @param texto
	 * @return
	 * @throws FormatException
	 */
	public static Date strindDataSemBarraFormataDate(final String texto) throws Exception {
		return DateUtils.getData(texto.replaceAll("(\\d{2})(\\d{2})(\\d{4})", "$1/$2/$3"), DateUtils.FORMATO_DATA, false);
	}

	/**
	 * @param texto
	 * @return
	 */
	public static String strindFormatarHora(final String texto) {
		return texto.replaceAll("(\\d{2})(\\d{2})(\\d{2})", "$1:$2:$3");
	}

	/**
	 * @param valor
	 * @return
	 */
	public static String numeroDecimalFormatado(final Integer valor) {
		return numeroDecimalFormatado(valor, "00");
	}

	/**
	 * @param valor
	 * @param formato
	 * @return
	 */
	public static String numeroDecimalFormatado(final Integer valor, final String formato) {
		DecimalFormat format = new DecimalFormat(formato);
		return format.format(valor);
	}

	/**
	 * @param mascara
	 * @param texto
	 * @return
	 */
	public static String formataString(final String mascara, final String texto) {
		String retorno = "";
		MaskFormatter format;
		try {
			format = new MaskFormatter(mascara);
			format.setValidCharacters("0123456789");

			JFormattedTextField text = new JFormattedTextField();
			text.setText(texto);

			retorno = text.getText();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * @param valor
	 * @return
	 */
	public static boolean verificacaoStringNumerica(final String valor) {
		return Pattern.matches("[-]?\\d*[.]?\\d+", valor.replace(",", "."));
	}

	/**
	 * Concatenar Strings com o separador passado, pulando as strings vazias
	 * 
	 * @param separador
	 *            Separador a ser adicionado entre os valores passados
	 * @param values
	 *            Valores a serem concatenados
	 * @return String concatenada
	 */
	public static String concat(String separador, String... values) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			if (values[i] != null && !values[i].equalsIgnoreCase("")) {
				str.append(values[i]);
				if (values.length > (i + 1)) {
					// Verifica se o próximo é o ultimo, e se está em branco
					boolean proximoEhUltimo = values.length == i + 2;
					boolean ultimoEhBranco = values[i + 1].equalsIgnoreCase("");
					if (!(proximoEhUltimo && ultimoEhBranco)) {
						str.append(separador);
					}
				}
			}

		}

		return str.toString();
	}

	/**
	 * Concatenar Strings com ponto "."
	 * 
	 * @param values
	 *            Valores a serem concatenados
	 * @return String concatenada
	 */
	public static String concatPoint(String... values) {
		return concat(".", values);
	}

	/**
	 * Converter o InputStream para Array de Byte
	 * 
	 * @param input
	 *            InputStream
	 * @return byte[] Array de Byte
	 * @throws IOException
	 */
	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buf = new byte[4096];
		int len = 0;
		while ((len = input.read(buf)) > -1) {
			output.write(buf, 0, len);
		}
		return output.toByteArray();
	}

	/**
	 * Converter Double para String no formato #.##0,00
	 * 
	 * @param valor
	 *            a ser formatado
	 * @return String formatada #.##0,00
	 */
	public static String formatDoubleDecimal(final Double valor) {
		DecimalFormat df = new DecimalFormat("#,##0.00");
		return df.format(valor);
	}

	/**
	 * Formata o caminho do hibernate para o mesmo do alias do Criteria
	 * 
	 * @param propertyName
	 * @return
	 */
	public static String formatPropertyAlias(final String propertyName) {
		if (!propertyName.contains(".")) {
			return propertyName;
		}

		String campo = propertyName.substring(propertyName.lastIndexOf('.'));
		String caminho = propertyName.substring(0, propertyName.lastIndexOf(campo)).replace(".", "_");
		return caminho.concat(campo);
	}

	/**
	 * Formata o caminho do hibernate para o mesmo do alias do Criteria
	 * 
	 * @param propertyName
	 * @return
	 */
	public static String getEntity(final String propertyName) {
		return propertyName.substring(0, propertyName.lastIndexOf("."));
	}

	/**
	 * Formata o número Double para uma String do valor com duas casas decimais
	 * 
	 * @param valor
	 * @return
	 */
	public static String numeroComDuasCasasDecimais(Double valor) {
		return numeroCasasDecimais(valor, 2);
	}

	/**
	 * Formata o número Double para uma String do valor com a quantidade de casas decimais informada
	 * 
	 * @param valor
	 * @param casasDecimais
	 * @return
	 */
	public static String numeroCasasDecimais(Double valor, int casasDecimais) {
		DecimalFormatSymbols ds = new DecimalFormatSymbols();
		ds.setDecimalSeparator(',');
		ds.setGroupingSeparator('.');

		DecimalFormat format = new DecimalFormat();
		format.setDecimalFormatSymbols(ds);
		format.setMaximumFractionDigits(casasDecimais);
		format.setMinimumFractionDigits(1);

		return format.format(valor);
	}

	/**
	 * Método retorna o valor decimal de um número, ignorando o número inteiro
	 * 
	 * @param valor
	 * @return
	 */
	public static double getValorDecimal(Double valor) {
		return valor % 1;
	}

	/**
	 * Verificar se o valor passado é um número inteiro ou fracionado
	 * 
	 * @param valor
	 * @return
	 */
	public static boolean isNumeroInteiro(Double valor) {
		return getValorDecimal(valor) == 0;
	}
	
	/**
	 * Converter lista de sortBy passada para um Array <br>
	 * OBS: Esta lista deve ser passada no campo SortBy de uma DataTable ou de Uma coluna <br>
	 * <b>EXEMPLO: sortBy="#{beanVar.campo1};#{beanVar.campo2};#{beanVar.campo3}" </b><br>
	 * @param sortField
	 * @return
	 */
	public static String[] getArraySortFields(String sortField) {
		String[] fields = sortField.split(";");
		String[] retorno = new String[fields.length];
		retorno[0] = fields[0].replace("}", "");
		if (fields.length > 1) {
			for (int i = 1; i < fields.length; i++) {
				String str = fields[i];
				if (str.indexOf(".") > -1) {
					str = str.replace("}", "").replace("#{", "");
					str = str.substring(str.indexOf(".") + 1);
				}
				retorno[i] = str;
			}
		}
		return retorno;
	}
	
	/**
	 * Resgatar um número double formatado sem separação e com casas decimais
	 * @param value
	 * @return
	 */
	public static Double getDoubleCasasDecimais(Double value, int casasDecimais) {
		DecimalFormat df = new DecimalFormat("###0.00");
		df.setMaximumFractionDigits(casasDecimais);
		return new Double(df.format(value).replace(",", "."));
	}
}