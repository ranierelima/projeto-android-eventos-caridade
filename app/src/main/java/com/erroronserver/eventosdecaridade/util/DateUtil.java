package com.erroronserver.eventosdecaridade.util;

/**
 * Created by Raniere de Lima (ranieredelima@gmail.com) on 20/06/2017.
 */
import android.text.format.DateUtils;
import android.util.Log;

import com.erroronserver.eventosdecaridade.application.EventoCaridadeApplication;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class DateUtil {

    private static SimpleDateFormat iso8601Format = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static Date stringToDate(String dateString) {
        try {
            return iso8601Format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateToString(Date date) {
        if (date != null) {
            long when = date.getTime();
            int flags = 0;
            flags |= DateUtils.FORMAT_SHOW_TIME;
            flags |= DateUtils.FORMAT_SHOW_DATE;
            flags |= DateUtils.FORMAT_ABBREV_MONTH;
            flags |= DateUtils.FORMAT_SHOW_YEAR;

            return DateUtils.formatDateTime(EventoCaridadeApplication.getAppContext(),
                    when + TimeZone.getDefault().getOffset(when), flags);
        }
        return null;
    }

    public static Calendar convertToGmt(Calendar cal) {

        Date date = cal.getTime();
        TimeZone tz = cal.getTimeZone();

        //Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT
        long msFromEpochGmt = date.getTime();

        //gives you the current offset in ms from GMT at the current date
        int offsetFromUTC = tz.getOffset(msFromEpochGmt);

        //create a new calendar in GMT timezone, set to this date and add the offset
        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
        gmtCal.setTime(date);
        gmtCal.add(Calendar.MILLISECOND, offsetFromUTC);


        return gmtCal;
    }

    public static Date convertToGmt(Date date) {

        //gives you the current offset in ms from GMT at the current date
        int offsetFromUTC = date.getTimezoneOffset();

        //create a new calendar in GMT timezone, set to this date and add the offset
        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
        gmtCal.setTime(date);
        gmtCal.add(Calendar.MILLISECOND, offsetFromUTC);

        System.out.println(gmtCal.getTime());

        return gmtCal.getTime();
    }

    public static Date timeMillisToDate(Integer timeMillis) {
        if (timeMillis != null) {
            TimeZone tz = TimeZone.getDefault();
            TimeZone.setDefault(tz);
            Calendar cal = GregorianCalendar.getInstance(tz);
            cal.setTimeInMillis(timeMillis);

            return cal.getTime();
        }
        return null;
    }

    public static String getFormatterHora(Date data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm", Locale.getDefault());
        return dateFormat.format(data);
    }

    public static String getFormatterDate(Date data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(data);
    }


    public static String getDateTime(Date data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(data);
    }

    public static Date getDateTime(String data) {
        if (data == null) {
            return null;
        }

        Date parsedDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            parsedDate = dateFormat.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }

    public static Calendar getCalendarNow() {
        TimeZone tz = TimeZone.getDefault();
        TimeZone.setDefault(tz);

        return GregorianCalendar.getInstance(tz);
    }

    public static Calendar acrescentarDias(Calendar calendar, int dias) {
        Calendar copiaCalendar = (Calendar) calendar.clone();
        copiaCalendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + dias);

        return copiaCalendar;
    }

    public static long diferencaHoras(Date dateInicial, Date dataFinal) {
        long diff = dateInicial.getTime() - dataFinal.getTime();//as given

        return TimeUnit.MILLISECONDS.toHours(diff);
    }

    public static final long MILLIS_POR_DIA = 1000 * 60 * 60 * 24;
    public static final long MILLIS_POR_ANO = 365 * MILLIS_POR_DIA;

    private static String calendarToString(Calendar c, String modelo) {

        if (c == null) {
            return "";
        }

        String timezone = "";

        if (c.getTimeZone() != null && c.getTimeZone().getID() == null) {
            timezone = "UTC";
        } else {
            timezone = c.getTimeZone().getID();
        }

        String data = "";

        if (c != null) {
            SimpleDateFormat formatoData = new SimpleDateFormat(modelo);
            formatoData.setTimeZone(TimeZone.getTimeZone(timezone));
            data = formatoData.format(c.getTime());
        }

        return data;
    }

    private static Calendar stringToCalendar(String data, String modelo) {

        SimpleDateFormat formatoData = new SimpleDateFormat(modelo);
        formatoData.setLenient(true);
        formatoData.setTimeZone(TimeZone.getDefault());

        Calendar novaData = Calendar.getInstance(TimeZone.getDefault());

        if (StringUtils.isNotEmpty(data)) {
            try {
                novaData.setTime(formatoData.parse(data));
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage() );
            }

            return novaData;
        } else {
            return null;
        }

    }

    /*
     *
     * Conversão de Calendar em String
     */
    public static String calendarToStringHhmm(Calendar c) {
        String modeloData = "HH:mm";
        return calendarToString(c, modeloData);
    }

    public static String calendarToStringHhmmss(Calendar c) {
        String modeloData = "HH:mm:ss";
        return calendarToString(c, modeloData);
    }

    public static String calendarToStringDd(Calendar c) {
        String modeloData = "dd";
        return calendarToString(c, modeloData);
    }

    public static String calendarToStringDdMmAaaa(Calendar c) {
        String modeloData = "dd/MM/yyyy";
        return calendarToString(c, modeloData);
    }

    public static String calendarToStringDdMmAaaaHhMm(Calendar c) {
        String modeloData = "dd/MM/yyyy HH:mm";
        return calendarToString(c, modeloData);
    }

    public static String calendarToStringDdMmAaaaHhmmss(Calendar c) {
        String modeloData = "dd/MM/yyyy HH:mm:ss";
        return calendarToString(c, modeloData);
    }

    public static String calendarToStringDdMmAaaaHhmmssZ(Calendar c) {
        String modeloData = "dd/MM/yyyy HH:mm:ss z";
        return calendarToString(c, modeloData);
    }

    public static String calendarToStringAaaaMm(Calendar c) {
        String modeloData = "yyyy-MM";
        return calendarToString(c, modeloData);
    }

    public static String calendarToStringYyyymm(Calendar c) {
        String modeloData = "yyyy-MM";
        return calendarToString(c, modeloData);
    }

    public static String calendarToStringYyyymmdd(Calendar c) {
        String modeloData = "yyyy-MM-dd";
        return calendarToString(c, modeloData);
    }

    public static String calendarToStringYyyymmddHhmmss(Calendar c) {
        String modeloData = "yyyy-MM-dd HH:mm:ss";
        return calendarToString(c, modeloData);
    }

    public static String calendarToStringHhmmssDdMmYyyy(Calendar c) {
        String modeloData = "HH:mm:ss dd/MM/yyyy";
        return calendarToString(c, modeloData);
    }

    public static String calendarToStringDiaDaSemanaCompleto(Calendar c) {
        String modeloData = "EEEE";
        return calendarToString(c, modeloData);
    }

    public static String calendarToStringDiaDaSemanaTresLetras(Calendar c) {
        String modeloData = "EEE";
        return calendarToString(c, modeloData);
    }

    public static String calendarToStringDdDeMesDeAaaa(Calendar c) {
        String modeloData = "dd 'de' MMMM 'de' yyyy";
        return calendarToString(c, modeloData);
    }

    public static String calendarToStringMes(Calendar c) {
        String modeloData = "MMMM";
        return calendarToString(c, modeloData);
    }

    public static String calendarToStringTotalHHmm(Calendar calendar) {
        long intervaloInMillis = calendar.getTimeInMillis();

        long horas = TimeUnit.MILLISECONDS.toHours(intervaloInMillis);
        long minutos = TimeUnit.MILLISECONDS.toMinutes(intervaloInMillis) % 60;

        String tempo = (horas < 10 ? "0" + horas : horas) + ":" + (minutos < 10 ? "0" + minutos : minutos);

        return tempo;
    }

	/*
     *
	 * Converter Date para String
	 */

    public static String dateToStringDdMmAaaaa(Date d) {
        return calendarToStringDdMmAaaa(convertDateToCalendar(d));
    }

    public static String dateToStringDdMmAaaaHhmmss(Date d, String timezone) {
        return calendarToStringDdMmAaaaHhmmss(convertDateToCalendar(d));
    }

    public static String dateToStringDdMmAaaaHhmmssZ(Date d, String timezone) {
        return calendarToStringDdMmAaaaHhmmssZ(convertDateToCalendar(d));
    }

    /*
     *
     * Conversão de String para Calendar
     */
    public static Calendar stringHhMmToCalendar(String d, String timezone) {
        String modeloData = "HH:mm";
        return stringToCalendar(d, modeloData);
    }

    public static Calendar stringDdMmAaaaToCalendar(String d) {
        String modeloData = "dd/MM/yyyy";
        Calendar c = stringToCalendar(d, modeloData);
        return c;
    }

    public static Calendar stringDdMmAaaaHhMmSsToCalendar(String d) {
        String modeloData = "dd/MM/yyyy HH:mm:ss";
        return stringToCalendar(d, modeloData);
    }

    public static Calendar stringAaMmDdHhMmToCalendar(String d) {
        String modeloData = "yyMMddHHmm";
        Calendar c = stringToCalendar(d, modeloData);
        return c;
    }

    public static Calendar stringDdMmAaaaHhMmToCalendar(String d) {
        String modeloData = "dd/MM/yyyy HH:mm";
        return stringToCalendar(d, modeloData);
    }

    public static Calendar stringAaaaMmDdToCalendar(String d) {
        String modeloData = "yyyy-MM-dd";
        Calendar c = stringToCalendar(d, modeloData);
        return c;
    }

    public static Calendar stringAaaaMmDdHhMmSsToCalendar(String d) {
        String modeloData = "yyyy-MM-dd HH:mm:ss";
        return stringToCalendar(d, modeloData);
    }

    public static Calendar setHoraInicioDia(Calendar c) {
        if (c != null) {
            Calendar atual = c;
            c.set(atual.get(Calendar.YEAR), atual.get(Calendar.MONTH), atual.get(Calendar.DATE), 0, 0, 0);
            c.set(Calendar.MILLISECOND, 0);
        }

        return c;
    }

    public static Calendar setHoraInicioDia(Date d) {
        return setHoraInicioDia(convertDateToCalendar(d));
    }

    public static Calendar setHoraFimDia(Calendar c) {
        if (c != null) {
            Calendar atual = c;
            c.set(atual.get(Calendar.YEAR), atual.get(Calendar.MONTH), atual.get(Calendar.DATE), 23, 59, 59);
        }

        return c;
    }

    public static Calendar setHoraFimDia(Date d) {
        return setHoraFimDia(convertDateToCalendar(d));
    }

    public static int diferencaCalendarEmSegundo(Calendar inicio, Calendar fim) {

        long m1 = convertCalendarInMillis(inicio);
        long m2 = convertCalendarInMillis(fim);

        long x = ((m2 - m1) / 1000);

        return Integer.valueOf(x + "").intValue();
    }

    public static int diferencaCalendarEmMinuto(Calendar inicio, Calendar fim) {

        long m1 = convertCalendarInMillis(inicio);
        long m2 = convertCalendarInMillis(fim);

        long x = ((m2 - m1) / (60 * 1000));

        return Integer.valueOf(x + "").intValue();
    }

    public static int diferencaCalendarEmHora(Calendar inicio, Calendar fim) {

        long m1 = convertCalendarInMillis(inicio);
        long m2 = convertCalendarInMillis(fim);

        long x = ((m2 - m1) / (60 * 60 * 1000));

        return Integer.valueOf(x + "").intValue();
    }

    public static int diferencaCalendarEmDia(Calendar inicio, Calendar fim) {

        long m1 = convertCalendarInMillis(inicio);
        long m2 = convertCalendarInMillis(fim);

        long x = ((m2 - m1) / 1000 / 60 / 60 / 24);

        return Integer.valueOf(x + "").intValue();
    }

    public static int diferencaCalendarEmDiaIgnorandoHorario(Calendar inicio, Calendar fim) {

        long m1 = convertCalendarInMillis(setHoraInicioDia(inicio));
        long m2 = convertCalendarInMillis(setHoraInicioDia(fim));

        long x = ((m2 - m1) / 1000 / 60 / 60 / 24);

        return Integer.valueOf(x + "").intValue();
    }

    public static int diferencaCalendarEmMinutoIgnorandoData(Calendar dataInicio, Calendar dataFim) {

        if (dataInicio != null && dataFim != null) {

            Calendar inicio = (Calendar) dataInicio.clone();
            Calendar fim = (Calendar) dataFim.clone();

            inicio.set(1111, 11, 11, dataInicio.get(Calendar.HOUR_OF_DAY), dataInicio.get(Calendar.MINUTE), dataInicio.get(Calendar.SECOND));
            fim.set(1111, 11, 11, dataFim.get(Calendar.HOUR_OF_DAY), dataFim.get(Calendar.MINUTE), dataFim.get(Calendar.SECOND));

            return diferencaCalendarEmMinuto(inicio, fim);

        } else {
            return 0;
        }

    }

    public static Calendar convertDateToCalendar(Date d) {

        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String data = formatoData.format(d);

        Calendar c;
        c = stringToCalendar(data, "yyyy-MM-dd HH:mm:ss");

        return c;
    }

    public static Calendar copiarHorarioDeDateParaCalendar(Date horario, Calendar c) {
        int ano = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DATE);

        c.setTimeInMillis(horario.getTime());

        c.set(ano, mes, dia);

        return c;
    }

    public static Calendar getDataInicioSemanaContemData(Calendar data) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.clear();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        data.setFirstDayOfWeek(Calendar.SUNDAY);
        c.set(Calendar.WEEK_OF_YEAR, data.get(Calendar.WEEK_OF_YEAR));
        c.set(Calendar.YEAR, data.get(Calendar.YEAR));
        return c;
    }

    public static Float converterUTCtoFloat(String utc) throws Exception {

        Float tempo = 0f;
        try {
            String[] utcPart = utc.trim().split(":");
            String sinal = utcPart[0].substring(0, 1);
            String horas = utcPart[0].substring(1, 3);
            String minutos = utcPart[1];

            tempo = Float.valueOf(horas).floatValue() + (Float.valueOf(minutos).floatValue() / 60);
            if (sinal.equals("−") || sinal.equals("-")) {
                tempo = tempo * -1;
            }
        } catch (Exception e) {
            throw new Exception("Falha na conversão do UTC. Detalhes: " + e.getMessage());
        }

        return tempo;
    }

    public static Long convertCalendarInMillis(Calendar c) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, c.get(Calendar.MILLISECOND));
        return calendar.getTimeInMillis();

    }

    public static Calendar getCalendarUTC() {
        return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    }

    public static Calendar getCalendarUTCZerado() {
        Calendar calendar = getCalendarUTC();
        calendar.setTime(new Date(0));

        return calendar;
    }

    public static Date convertDateWithTimezoneToDateUTC(Date d, String timezone) {
        TimeZone timeZone = TimeZone.getTimeZone(timezone);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formatter.setTimeZone(timeZone);

        String data = dateToStringDdMmAaaaHhmmssZ(d, timeZone.getID());

        Date dataUTC = null;
        try {

            dataUTC = formatter.parse(data);

        } catch (ParseException e) {
            Log.e("ERROR",e.getMessage());
        }

        return dataUTC;
    }


    public static String convertDateToStringInTimeZone(Date data, String timezone) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        return sdf.format(data);
    }

    public static Date convertDateToDateInTimeZone(Date date, String timezone) {
        String data = convertDateToStringInTimeZone(date, timezone);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            return sdf.parse(data);
        } catch (ParseException e) {
            Log.e("ERROR",e.getMessage());
            return null;
        }
    }

    public static String getGMTString(Float utcUsuario) {
        Integer utc = (int) ((float) utcUsuario);

        String gmtString = "GMT";

        if (utc > 0) {
            gmtString += "+" + utc;
        } else if (utc < 0) {
            gmtString += utc;
        }

        return gmtString;
    }

    public static Date acrescentarDias(Date data, int dias, String timezone) {
        Calendar calendar = convertDateToCalendar(data);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + dias);

        return calendar.getTime();
    }

    public static Date acrescentarHoras(Date data, int horas, String timezone) {
        Calendar calendar = convertDateToCalendar(data);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + horas);

        return calendar.getTime();
    }

    public static Date acrescentarSegundos(Date data, int segundos, String timezone) {
        Calendar calendar = convertDateToCalendar(data);
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + segundos);

        return calendar.getTime();
    }

    public static Date ajustarSegundos(Date data) {
        Calendar calendar = convertDateToCalendar(data);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }


    public static Calendar getCalendarUsuario() {
        Date date = new Date();
        //  date = convertDateToDateInTimeZone(date);

        return convertDateToCalendar(date);
    }

    public static int getIdadePorDataNascimento(Calendar dataNascimento) {

        if (dataNascimento == null) {
            return 0;
        }

        long ageMillis = System.currentTimeMillis() - dataNascimento.getTime().getTime();

        return (int) (ageMillis / MILLIS_POR_ANO);
    }

    /**
     * Metodo que retorna um Calendar zerado (01/01/1970) + o tempo passado como parametro em minutos. (Ex.: 109 min = 01/01/1970 01:49:00)
     *
     * @param min
     * @return Calendar - Data zerada mais o minuto passado como parametro
     */
    public static Calendar getDataZeradaPorMinuto(Float min) {
        Calendar cal = Calendar.getInstance();

        int segundos = (int) (min * 60);

        cal.clear(); // para limpar o campo hora
        cal.set(Calendar.SECOND, segundos);

        return cal;
    }

    /**
     * Metodo que retorna um Calendar zerado (01/01/1970) + o tempo passado como parametro em horas. (Ex.: 6,02 horas = 01/01/1970 06:01:12)
     *
     * @param hora
     * @return Calendar - Data zerada mais a hora passada como parametro
     */
    public static Calendar getDataZeradaPorHora(Float hora) {
        Calendar cal = Calendar.getInstance();

        int segundos = (int) (hora * 60 * 60);

        cal.clear(); // para limpar o campo hora
        cal.set(Calendar.SECOND, segundos);

        return cal;
    }

    /**
     * Metodo que retorna um Calendar zerado (01/01/1970) + o tempo passado como parametro em dias. (Ex.: 14,28 dias = 15/01/1970 06:43:12)
     *
     * @param dia
     * @return Calendar - Data zerada mais o dia passado como parametro
     */
    public static Calendar getDataZeradaPorDia(Float dia) {
        Calendar cal = Calendar.getInstance();

        int segundos = (int) (dia * 24 * 60 * 60);

        cal.clear(); // para limpar o campo hora
        cal.set(Calendar.SECOND, segundos);

        return cal;
    }

    public static Date copiarHorarioDeDateParaDate(Date src, Date dest) {
        Calendar destZerado = stringDdMmAaaaToCalendar(dateToStringDdMmAaaaa(dest));

        return copiarHorarioDeDateParaCalendar(src, destZerado).getTime();
    }


    public static int diferencaDateEmSegundo(Date date, Date date2) {
        return diferencaCalendarEmSegundo(convertDateToCalendar(date), convertDateToCalendar(date2));
    }

    public static int compararDateIgnorandoData(Date d1, Date d2) {
        DateFormat f = new SimpleDateFormat("HH:mm:ss.SSS");

        return f.format(d1).compareTo(f.format(d2));
    }
}