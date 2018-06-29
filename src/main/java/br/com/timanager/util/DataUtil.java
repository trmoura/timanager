package br.com.timanager.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;

import br.com.timanager.dominio.DominioMes;

public class DataUtil {

	public static Date getDataAtual() {

		return new Date();
	}

	public static String getDataHoraAtual() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static String getDataAtualStringAmericano() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static Date getUtimaDataUtilDoMes() {

		return DateUtils.setDays(DataUtil.getDataAtual(),
				DateUtils.toCalendar(DataUtil.getDataAtual()).getActualMaximum(Calendar.DAY_OF_MONTH));
	}

	public static Date getUtimaDataUtilDoProximoMes(final Date data) {

		return DateUtils.setDays(DataUtil.adicionarMes(data),
				DateUtils.toCalendar(DataUtil.adicionarMes(data)).getActualMaximum(Calendar.DAY_OF_MONTH));
	}

	public static int getMesAtual() {

		return DataUtil.getDataAtual().getMonth() + 1;
	}

	public static boolean isMesAtual(Date data) {

		return DataUtil.getDataAtual().getMonth() == data.getMonth();
	}

	public static boolean beforeTruncate(Date data) {

		return DataUtil.beforeTruncate(data, DataUtil.getDataAtual());
	}

	public static boolean before(Date data) {

		return DataUtil.before(data, DataUtil.getDataAtual());
	}

	public static boolean beforeTruncate(Date data, Date dataLimite) {

		return (data != null) && (dataLimite != null)
				&& DataUtil.before(data, DataUtil.truncate(dataLimite, Calendar.HOUR_OF_DAY));
	}

	public static boolean before(Date data, Date dataLimite) {

		return (data != null) && (dataLimite != null) && data.before(dataLimite);
	}

	public static boolean afterTruncate(Date data) {

		return DataUtil.afterTruncate(data, DataUtil.getDataAtual());
	}

	public static boolean after(Date data) {

		return DataUtil.after(data, DataUtil.getDataAtual());
	}

	public static boolean afterTruncate(Date data, Date dataLimite) {

		return (data != null) && (dataLimite != null)
				&& DataUtil.after(data, DataUtil.truncate(dataLimite, Calendar.HOUR_OF_DAY));
	}

	public static boolean after(Date data, Date dataLimite) {

		return (data != null) && (dataLimite != null) && data.after(dataLimite);
	}

	public static Date truncate(Date date, int level) {

		if (date == null) {
			throw new IllegalArgumentException("invalid date");
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		switch (level) {
		case Calendar.YEAR:
			cal.set(Calendar.YEAR, cal.getActualMinimum(Calendar.YEAR));
		case Calendar.MONTH:
			cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
		case Calendar.DAY_OF_MONTH:
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		case Calendar.HOUR_OF_DAY:
			cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
		case Calendar.MINUTE:
			cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
		case Calendar.SECOND:
			cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
		case Calendar.MILLISECOND:
			cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
			break;
		default:
			throw new IllegalArgumentException("invalid level");
		}
		return cal.getTime();
	}

	public static String getDataComoString(Date data) {

		return StringUtils.join(new SimpleDateFormat("d", new Locale("pt", "BR")).format(data), " de ",
				new SimpleDateFormat("MMMM", new Locale("pt", "BR")).format(data), " de ",
				new SimpleDateFormat("YYYY", new Locale("pt", "BR")).format(data));
	}

	public static Date adicionarMes(final Date data) {

		return DateUtils.setDays(DateUtils.addMonths(data, 1), 10);
	}

	public static Date adicionarMeses(final Date data, final int quantidade) {

		return DateUtils.setDays(DateUtils.addMonths(data, quantidade), 10);
	}

	public static Date adicionarMeses(final Date data, final int dia, final int quantidade) {

		return DateUtils.setDays(DateUtils.addMonths(data, quantidade), dia);
	}

	public static Date adicionarDia(final Date data) {

		return DateUtils.addDays(data, 1);
	}

	public static String competencia(final Date data) {

		return new SimpleDateFormat("MM/yyyy").format(data);
	}

	public static String competencia(final DominioMes mes, final Integer ano) {

		return StringUtils.join(new String[] { mes.getCodigo().toString(), ano.toString() }, "/");
	}

	public static String toString(final Date data) {

		return DataUtil.toString(data, "dd/MM/yyyy");
	}

	public static String formatoBanco(final Date data) {

		return DataUtil.toString(data, "yyyy-MM-dd");
	}

	public static Date toDate(final String data) {

		try {
			return DateUtils.parseDate(data, "dd/MM/yyyy");
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static Date toDateAmericano(final String data) {
		
		try {
			return DateUtils.parseDate(data, "yyyy-MM-dd");
		} catch (ParseException e) {
			return null;
		}
	}

	public static String hash() {

		return DataUtil.toString(DataUtil.getDataAtual(), "ddMMyyyyHHmmss");
	}

	public static Integer getAnoAtual() {

		return Integer.parseInt(DataUtil.toString(DataUtil.getDataAtual(), "yyyy"));
	}

	public static Integer getAno(Date data) {

		return Integer.parseInt(DataUtil.toString(data, "yyyy"));
	}

	public static String toString(final Date data, final String formato) {

		return new SimpleDateFormat(formato).format(data);
	}

	public static boolean isFinalDeSemana(final Date data) {

		final Calendar calendar = DateUtils.toCalendar(data);
		return (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				|| (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
	}

	public static int getQuantidadeMesesVencidos(final Date dataVencimento, final Date dataCalculo) {

		return Months.monthsBetween(new DateTime(DateUtils.setDays(dataVencimento, 1)), new DateTime(dataCalculo))
				.getMonths() + 1;
	}

	public static int getQuantidadeMesesEntreDatas(final Date dataInicio, final Date dataFim) {
		return Months.monthsBetween(new DateTime(dataInicio), new DateTime(dataFim)).getMonths();
	}

	public static int getQuantidadeDiasVencidos(final Date dataVencimento, final Date dataCalculo) {

		return Days.daysBetween(new DateTime(DateUtils.setDays(dataVencimento, 1)), new DateTime(dataCalculo))
				.getDays();
	}

	public static int getQuantidadeDiasEntre(final Date dataInicio, final Date dataFim) {
		
		return Days.daysBetween(new DateTime(dataInicio), new DateTime(dataFim)).getDays();
	}

	// FIXME: teste
	public static void main(String[] args) {

		DateTime dataVencimento = new DateTime(DataUtil.toDate("01/08/2016"));// DataUtil.getUtimaDataUtilDoMes();

		DateTime dataCalculo = new DateTime(DataUtil.toDate("01/09/2016"));

		System.out.println(Months.monthsBetween(dataVencimento, dataCalculo).getMonths());
		// System.out.println(DataUtil.getQuantidadeMesesVencidos(dataVencimento,
		// dataCalculo));
		// System.out.println(DateUtils.truncatedCompareTo(DataUtil.toDate("18/03/2016"),
		// DataUtil.toDate("10/03/2016"), Calendar.MONTH));
		// System.out.println(new
		// SimpleDateFormat("MM/yyyy").format(DataUtil.getDataAtual()));
		// System.out.println(DateUtils.addDays(new Date(), 1));
		// System.out.println(DateUtils.addDays(DataUtil.getUtimaDataUtilDoMes(),
		// -1));

	}

	private static void testeDataVencimento() {

		Date dataAtual = DataUtil.toDate("31/08/2016");

		Date dataVencimento = DataUtil.toDate("31/08/2016");// DataUtil.getUtimaDataUtilDoMes();
		while (!DataUtil.beforeTruncate(dataVencimento, dataAtual) && DataUtil.isFinalDeSemana(dataVencimento)) {
			dataVencimento = DateUtils.addDays(dataVencimento, -1);
		}
		if (DataUtil.beforeTruncate(dataVencimento, dataAtual)) {
			dataVencimento = DataUtil.getUtimaDataUtilDoProximoMes(dataVencimento);
			while (DataUtil.isFinalDeSemana(dataVencimento)) {
				dataVencimento = DateUtils.addDays(dataVencimento, -1);
			}
		}
		System.out.println(dataVencimento);
	}

}
