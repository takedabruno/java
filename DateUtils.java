package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

//import br.com.prototipovt.enums.DiasDaSemana;

/**
 * Classe utilizada para formatar data.
 * 
 */
public class DateUtils {

	/**
	 * Formato de data completa dd/MM/yyyy HH:mm:ss.SSS.
	 */
	public static final String FORMATO_DATA_HORA_NANOS = "dd/MM/yyyy HH:mm:ss.SSS";

	/**
	 * Formato de data dd/MM/yyyy HH:mm.
	 */
	public static final String FORMATO_DATA_HORA = "dd/MM/yyyy HH:mm";

	/**
	 * Formato de data dd/MM/yyyy.
	 */
	public static final String FORMATO_DATA = "dd/MM/yyyy";

	/**
	 * Formato de data dd/MM/yyyy HH:mm:ss.
	 */
	public static final String FORMATO_DATA_HORA_MINUTO_SEGUNDO = "dd/MM/yyyy HH:mm:ss";

	/**
	 * Formato de data yyyy-MM-dd-HHmmss.
	 */
	public static final String FORMATO_DATA_HORA_MINUTO_SEGUNDO_AMERICANO = "yyyy-MM-dd-HHmmss";

	/**
	 * Formato de data yyyy-MM-dd.
	 */
	public static final String FORMATO_DATA_AMERICANO = "yyyy-MM-dd";

	/**
	 * Formato HH:mm.
	 */
	public static final String FORMATO_HORA_MINUTO = "HH:mm";

	/**
	 * Recebe uma data no formato dd/MM/yyyy e retorna um long que representa a data
	 * 
	 * @param strData
	 *            - Formato dd/MM/yyyy
	 * @throws FormatException
	 *             - Lança exception para qualquer data invalida, como por exemplo 30/02/2010 ou 01/13/2010
	 */
	public static long getDataLong(final String strData) throws Exception {
		return getData(strData, FORMATO_DATA, false).getTime();
	}

	/**
	 * Recebe uma data no formato dd/MM/yyyy e retorna um Date que representa a data
	 * 
	 * @param strData
	 *            - Formato dd/MM/yyyy
	 * @throws FormatException
	 *             - Lança exception para qualquer data invalida, como por exemplo 30/02/2010 ou 01/13/2010
	 */
	public static Date getData(final String strData) throws Exception {
		return getData(strData, FORMATO_DATA, false);
	}

	/**
	 * Recebe uma data no formato dd/MM/yyyy e retorna um long que representa a data, que de acordo com o parâmetro inicioDia retorna a hora da data como 00:00:00 ou 23:59:59.999
	 * 
	 * @param strData
	 * @param inicioDia
	 * @return
	 * @throws ParseException
	 */
	public static long getData(final String strData, final boolean inicioDia) throws Exception {
		StringBuilder data = new StringBuilder(strData);
		if (inicioDia) {
			data.append(" 00:00:00.000");
		} else {
			data.append(" 23:59:59.999");
		}
		return getData(data.toString(), FORMATO_DATA_HORA_NANOS, false).getTime();
	}

	/**
	 * Recebe uma data no formato dd/MM/yyyy e uma String com a hora e retorna a data formatada
	 * 
	 * @param data
	 * @param hora
	 * @return
	 * @throws ParseException
	 */
	public static Date getData(final Date data, final String hora) throws Exception {
		SimpleDateFormat sd = new SimpleDateFormat(FORMATO_DATA);

		StringBuilder strData = new StringBuilder(sd.format(data));
		strData.append(" ");
		strData.append(hora);

		return getData(strData.toString(), FORMATO_DATA_HORA_MINUTO_SEGUNDO, false);
	}

	/**
	 * Recebe um long no formato yyyyMM, e converte em uma data Ex: 201111, retorna 30/11/2011 23:59:59.999
	 * 
	 * @param data
	 * @return
	 * @throws FormatException
	 */
	public static Date getDataUltimoDiaMesPorAnoMes(final long data) throws Exception {
		Date date = getData(String.valueOf(data), "yyyyMM", false);
		date = maisN(date, 1, Calendar.MONTH);
		return menosNDias(date, 1);
	}
	
	/**
	 * Retornar o �ltimo dia do m�s com base na data passada<br>
	 * Exemplo: Passado 23/11/2012 resposta = 30/11/2012 23:59:59
	 * @param data
	 * @return Date
	 */
	public static Date getDataUltimoDiaMes(final Date data) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return getFullTime(cal.getTime());
	}
	
	/**
	 * Retornar o primeiro dia do m�s com base na data passada
	 * Exemplo: Passado 23/11/2012: resposta = 01/11/2012 00:00:00
	 * @param data
	 * @return
	 */
	public static Date getDataPrimeiroDiaMes(final Date data) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return getEmptyTime(cal.getTime());
	}
	
	/**
	 * Recebe um long no formato yyyyMM, e converte em uma data Ex: 201111, retorna 01/11/2011 00:00:00.000
	 * 
	 * @param data
	 * @return
	 * @throws FormatException
	 */
	public static Date getDataPrimeiroDiaMesPorAnoMes(final long data) throws Exception {
		Date date = getData(String.valueOf(data), "yyyyMM", false);
		return date;
	}

	/**
	 * @param data
	 * @return
	 * @throws FormatException
	 */
	public static String getDataPrimeiroDiaMesPorAnoMesString(final long data) throws Exception {
		return getData(getDataPrimeiroDiaMesPorAnoMes(data));
	}

	/**
	 * @param data
	 * @return
	 * @throws FormatException
	 */
	public static String getDataUltimoDiaMesPorAnoMesString(final long data) throws Exception {
		return getData(getDataUltimoDiaMesPorAnoMes(data));
	}

	/**
	 * um objeto data e altera a data deste dia para o início ou o final do dia como 00:00:00 ou 23:59:59.999
	 * 
	 * @param strData
	 * @param inicioDia
	 * @return
	 * @throws ParseException
	 */
	public static long getData(final Date data, final boolean inicioDia) throws Exception {
		return getData(getData(data), inicioDia);
	}

	/**
	 * Recebe uma data, o formato da data, e se é leniente ou não e retorna um Objeto Date
	 * 
	 * @throws FormatException
	 *             - Lança exception para qualquer data invalida
	 */
	public static Date getData(final String strData, final String formato, final boolean lenient) throws Exception {
		Date data = null;
		try {
			SimpleDateFormat sd = new SimpleDateFormat(formato);
			sd.setLenient(lenient);
			data = sd.parse(strData);
		} catch (ParseException e) {
			// e.printStackTrace();
			throw e;
		}
		return data;
	}

	/**
	 * @param data
	 * @return
	 * @throws FormatException
	 */
	public static String getDataHoraMinutoSegundo(final long data) throws Exception {
		return getData(new Date(data), FORMATO_DATA_HORA_MINUTO_SEGUNDO, true);
	}

	/**
	 * Recebe uma data, o formato da data, e se é leniente ou não e retorna uma String
	 * 
	 * @throws FormatException
	 *             - Lança exception para qualquer data invalida
	 */
	public static String getData(final Date data, final String formato, final boolean lenient) throws Exception {
		String retorno = "";
		if (data != null) {
			try {
				SimpleDateFormat sd = new SimpleDateFormat(formato);
				sd.setLenient(lenient);
				retorno = sd.format(data);
			} catch (Exception e) {
				throw e;
			}
		}
		return retorno;
	}

	/**
	 * Recebe uma data, o formato da data, e se é leniente ou não e retorna uma String
	 * Método criado apenas para utilização da EL, pois a mesma apresentou problemas com o polimorfismo
	 * @throws FormatException
	 *             - Lança exception para qualquer data invalida
	 */
	public static String getDataWrapper(final Date data, final String formato) throws Exception {
		return getData(data, formato, false);
	}
	
	/**
	 * Recebe uma data, o formato da data, e se é leniente ou não e retorna uma String
	 * 
	 *             - Lança exception para qualquer data invalida
	 */
	public static String getDataAtualString(final String formato) {
		String retorno = "";
		try {
			SimpleDateFormat sd = new SimpleDateFormat(formato);
			retorno = sd.format(new Date());
		} catch (Exception e) {
			// e.printStackTrace();
			return "";
		}
		return retorno;
	}

	/**
	 * Recebe um objeto Date e retorna uma data no formato padrão de data. Este método considera a data como leniente.
	 */
	public static String getData(final Date data) {
		try {
			if (data != null) {
				return getData(data, FORMATO_DATA, true);
			} else {
				return "";
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	/**
	 * Retorna uma lista decrescente contendo todos os anos compreendidos entre o parâmetro <i>anoInicio</i> e a data atual
	 * 
	 * @param anoInicio
	 * @return Um objeto List populado com Integer
	 */
	public static final List<Integer> getListaAno(final int anoInicio) {
		List<Integer> retorno = new ArrayList<Integer>();
		Calendar cal = Calendar.getInstance();
		int ano = cal.get(Calendar.YEAR);
		for (int i = ano; i >= anoInicio; i--) {
			retorno.add(i);
		}
		return retorno;
	}

	/**
	 * Retorna uma Data somada com os dias passados como parâmetro. Ex: param data 01/01/2009, param dias 5, resultado 06/01/2009.
	 * 
	 * @param data
	 * @param dias
	 * @return
	 */
	public static final Date maisNDias(final Date data, final int dias) {
		// Refactor para utilizar o metodo maisN
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.DAY_OF_MONTH, dias);
		return cal.getTime();
	}

	/**
	 * Retorna uma Data subtraida com os dias passados como parâmetro. Ex: param data 06/01/2009, param dias 5, resultado 01/01/2009.
	 * 
	 * @param data
	 * @param dias
	 * @return
	 */
	public static final Date menosNDias(final Date data, final int dias) {
		return maisNDias(data, -dias);
	}

	/**
	 * Retorna uma Data somada com os milisegundos passados como parâmetro.
	 * 
	 * @param data
	 * @param milis
	 * @return
	 */
	public static final Date maisNMilis(final Date data, final int milis) {
		// Refactor para utilizar o metodo maisN
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.MILLISECOND, milis);
		return cal.getTime();
	}

	/**
	 * Retorna uma Data subtraida com dos milisegundos passados como parâmetro.
	 * 
	 * @param data
	 * @param milis
	 * @return
	 */
	public static final Date menosNMilis(final Date data, final int milis) {
		return maisNMilis(data, -milis);
	}

	/**
	 * Retorna uma Data somada com os minutos passados como parâmetro.
	 * 
	 * @param data
	 * @param minutos
	 * @return
	 */
	public static final Date maisNMinutos(final Date data, final int minutos) {
		// Refactor para utilizar o metodo maisN
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(Calendar.MINUTE, minutos);
		return cal.getTime();
	}

	/**
	 * Retorna uma Data subtraida com dos minutos passados como parâmetro.
	 * 
	 * @param data
	 * @param minutos
	 * @return
	 */
	public static final Date menosNMinutos(final Date data, final int minutos) {
		return maisNMilis(data, -minutos);
	}

	/**
	 * Retorna uma String que representando os milisegundos da data, ou null se a data for nula
	 * 
	 * @param data
	 * @return
	 */
	public static final String getTimeString(final Date data) {
		if (data != null) {
			return String.valueOf(data.getTime());
		} else {
			return null;
		}
	}

	/**
	 * Retorna uma Data somada do valor passado como par�metro, de acordo com par�metro calendarParam. Ex: param data 01/01/2009, param valor 5, calendarParam = Calendar.YEAR,
	 * resultado 01/01/2014
	 * 
	 * @param data
	 * @param valor
	 * @param calendarParam
	 * @return Date
	 */
	public static final Date maisN(final Date data, final int valor, final int calendarParam) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.add(calendarParam, valor);
		return cal.getTime();
	}

	/**
	 * Retorna uma Data somada com os anos passados como parâmetro. Ex: param data 01/01/2014, param anos 5, resultado 06/01/2014.
	 * 
	 * @param data
	 * @param anos
	 * @return
	 */
	public static final Date maisNAnos(final Date data, final int anos) {
		return maisN(data, anos, Calendar.YEAR);
	}

	/**
	 * Retorna uma Data subtraida com os anos passados como parâmetro. Ex: param data 06/01/2009, param anos 5, resultado 06/01/2004.
	 * 
	 * @param data
	 * @param anos
	 * @return
	 */
	public static final Date menosNAnos(final Date data, final int anos) {
		return maisNAnos(data, -anos);
	}

	/**
	 * Retorna o objeto Date, ou null se a data for inválida
	 * 
	 * @param data
	 * @return
	 */
	public static final Date getDataAsDate(final String data) {
		Date retorno;
		try {
			if (data != null && !data.equalsIgnoreCase("")) {
				retorno = new Date(DateUtils.getDataLong(data));
			} else {
				retorno = null;
			}
			return retorno;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Retorna o horário no formato hh:mm:ss da diferença da data passada com a data atual.
	 * 
	 * @param data
	 * @return double - diferença
	 */
	public static final String getStringHorasDiferencaDataAutal(final Date data) {
		return getStringHorasDiferenca(new Date(), data);
	}

	/**
	 * Retorna o horário no formato hh:mm:ss da diferença entre duas datas.
	 * 
	 * @param dataMaior
	 *            - a maior data
	 * @param dataMenor
	 *            - a menor data
	 * @return String - horário formatado
	 */
	public static final String getStringHorasDiferenca(final Date dataMaior, final Date dataMenor) {
		// pega a diferença em milisegundos das duas datas passadas
		double dif = dataMaior.getTime() - dataMenor.getTime();

		// pega a quantidade de milisegundos em um minuto
		double minutos = 1000 * 60;

		// Aqui, faz o calculo das diferenças totais em segundos, minutos e
		// horas
		double segundosDiferenca = dif / 1000;
		double minutosDiferenca = dif / minutos;
		double horasDiferenca = minutosDiferenca / 60;

		// Aqui, faz o calculo das diferenças em segundos, minutos e horas em
		// formato padrão de horário.
		int horasDiaDiferenca = Double.valueOf(Math.floor(horasDiferenca)).intValue();
		int minutosDiaDiferenca = Double.valueOf(Math.floor(minutosDiferenca - (24 * 60))).intValue() - (horasDiaDiferenca - 24) * 60;
		int segundosDiaDiferenca = Double.valueOf(Math.floor(segundosDiferenca - (24 * 60 * 60))).intValue() - ((minutosDiaDiferenca) * 60 + (horasDiaDiferenca - 24) * 60 * 60);

		return FormatoUtil.numeroDecimalFormatado(horasDiaDiferenca) + ":" + FormatoUtil.numeroDecimalFormatado(minutosDiaDiferenca) + ":"
				+ FormatoUtil.numeroDecimalFormatado(segundosDiaDiferenca);
	}

	/**
	 * Retorna o valor de minutos da diferença entre uma data e a outra
	 * 
	 * @param data
	 *            - a menor data
	 * @return double - diferença
	 */
	public static final Double getMinutosDiferencaDataAtual(final Date data) {
		return getMinutosDiferenca(new Date(), data);
	}

	/**
	 * Retorna o valor de minutos da diferença entre uma data e a outra
	 * 
	 * @param dataMaior
	 *            - a maior data dataMenor - a menor data
	 * @return double - diferença
	 */
	public static final Double getMinutosDiferenca(final Date dataMaior, final Date dataMenor) {
		double diferencaMillis = dataMaior.getTime() - dataMenor.getTime();
		double qtdeMinutosMillis = 1000 * 60;

		return diferencaMillis / qtdeMinutosMillis;
	}

	/**
	 * Retorna uma String com horário(hora : minuto) de uma Date.
	 * 
	 * @param date
	 * @return String horário
	 */
	public static final String horaMinutoData(final Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hora = cal.get(Calendar.HOUR_OF_DAY);
		int minuto = cal.get(Calendar.MINUTE);
		String horaMinuto = FormatoUtil.numeroDecimalFormatado(hora).concat(":").concat(FormatoUtil.numeroDecimalFormatado(minuto));

		return horaMinuto;
	}

	/**
	 * Retorna uma String com o dia da semana
	 * 
	 * @param date
	 * @return Dia da semana
	 * @see Domingo
	 * @see Segunda
	 * @see Terça
	 * @see Quarta
	 * @see Quinta
	 * @see Sexta
	 * @see Sábado
	 */
//	public static final String getDiaSemana(final Date date) {
//		return getDiaSemana(date, false);
//	}

	/**
	 * Retorna uma String com o dia da semana podendo ser completo ou não
	 * 
	 * @param date
	 * @param isComplete
	 *            Caso true e ser entre segunda e sexta, será adicionado "-feira" no final.
	 * @return Dia da semana
	 * @see Domingo
	 * @see Segunda-feira
	 * @see Terça-feira
	 * @see Quarta-feira
	 * @see Quinta-feira
	 * @see Sexta-feira
	 * @see Sábado
	 */
//	public static final String getDiaSemana(final Date date, boolean isComplete) {
//		DiasDaSemana d = DiasDaSemana.getFlag(getIndiceDiaSemana(date));
//
//		if (isComplete && d.isDiaDaSemana()) {
//			return d.getDescricao().concat("-feira");
//		} else {
//			return d.getDescricao();
//		}
//	}

	/**
	 * Retorna o índice do dia da semana
	 * 
	 * @param date
	 * @see 1 = Domingo
	 * @see 2 = Segunda-feira
	 * @see 3 = Terça-feira
	 * @see 4 = Quarta-feira
	 * @see 5 = Quinta-feira
	 * @see 6 = Sexta-feira
	 * @see 7 = Sábado
	 */
	public static final int getIndiceDiaSemana(final Date date) {
		Calendar cal = Calendar.getInstance(new Locale("pt", "BR"));
		cal.setTime(date);

		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * Retorna um Date com a hora, minuto, segundo e milisegundos zerados.
	 * 
	 * @param date
	 * @return dd/MM/yyyy 00:00:00
	 */
	public static final Date getEmptyTime(final Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	/**
	 * Retorna um Date com a hora = 23, minuto = 59, segundo = 59.
	 * 
	 * @param date
	 * @return dd/MM/yyyy 23:59:59
	 */
	public static final Date getFullTime(final Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);

		return cal.getTime();
	}

	/**
	 * Calcula a diferença entre duas datas de acordo com o paramêtro passado(parâmetro pode ser minuto, hora e dia), por default retorna o segundo;
	 * 
	 * @param dataInicio
	 * @param dataFim
	 * @param parametro Calendar.MINUTE ou Calendar.HOUR ou Calendar.DAY_OF_MONTH
	 * @return
	 */
	public static long getDiferencaDias(Date dataInicio, Date dataFim, int parametro) {
		long tempo = ((dataFim.getTime() - dataInicio.getTime()) / 1000);

		switch (parametro) {
			case Calendar.MINUTE:
				return tempo / 60;
			case Calendar.HOUR:
				return tempo / (60 * 60);
			case Calendar.DAY_OF_MONTH:
				return (tempo / (60 * 60)) / 24;
			default:
				return tempo;
		}
	}
	
	/**
	 * Retorna um Date no primeiro dia do mês para o Mes e ano passado
	 * @param ano
	 * @param mes
	 * @return Date
	 */
	public static Date getDataByMesAno(final Long ano, final Long mes) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.YEAR, ano.intValue());
		cal.set(Calendar.MONTH, mes.intValue() - 1);
		return getEmptyTime(cal.getTime());
	}
	
	/**
	 * Retorna um Date conforme os parametros recebidos
	 * @param ano
	 * @param mes
	 * @param dia
	 * @return Date
	 */
	public static Date getDataByDiaMesAno(final Long ano, final Long mes, final Long dia) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, dia.intValue());
		cal.set(Calendar.YEAR, ano.intValue());
		cal.set(Calendar.MONTH, mes.intValue() - 1);
		return getEmptyTime(cal.getTime());
	}
	
	/**
	 * Retorna uma data no formato XMLGregorianCalendar da data passada.
	 *  
	 * @param data A data a ser convertida em XMLGregorianCalendar.
	 * @return XMLGregorianCalendar
	 * @throws DatatypeConfigurationException
	 */
	public static XMLGregorianCalendar dateToXmlGregorian(java.util.Date data) throws DatatypeConfigurationException {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(data);
		XMLGregorianCalendar xmlGrogerianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		return xmlGrogerianCalendar;
	}
	
	public static long getMinutos(String hora1) {  
		String[] time = hora1.split(":");  
		try {  
			return Integer.parseInt(time[1]) + (Integer.parseInt(time[0])*60);  
		} catch (NumberFormatException e) {  
			return 0;  
		}  
	}  

	public static String getTempo(long minutos){  
		return String.format("%02d:%02d", (minutos / 60), (minutos % 60));  
	}
	
	
	/**
	 * Retorna uma data no formato "dd/MM/yyyy em milliseconds
	 *  
	 * @param data Data no formato "dd/MM/yyyy" a ser convertida em milliseconds
	 * @return Long milliseconds
	 * @throws ParseException
	 */
	public static Long formataDataMillis(String data) throws ParseException {
		Long dataFormatada = new SimpleDateFormat("dd/MM/yyyy").parse(data).getTime();
		return dataFormatada;
	}
}