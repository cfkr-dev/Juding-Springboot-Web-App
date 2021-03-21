package es.dawgroup2.juding.main;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

@Component
public class DateService {
    /**
     * Transforms a date in Spanish-readable format (e.g. 30/10/2000) into a SQL Date object.
     * @param dateString Date in Spanish-readable format.
     * @return Date object.
     * @throws ParseException Parsing exception.
     */
    public Date stringToDate(String dateString) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", new Locale("es"));
        format.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        java.util.Date date = format.parse(dateString);
        return new Date(date.getTime());
    }

    /**
     * Transforms a date in Spanish-readable format (e.g. 30/10/2000 12:34) into a SQL Timestamp object.
     * @param dateString Date in Spanish-readable format.
     * @return Timestamp object.
     * @throws ParseException Parsing exception.
     */
    public Timestamp stringToTimestamp(String dateString) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("es"));
        format.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));

        java.util.Date date = format.parse(dateString);

        return new Timestamp(date.getTime());
    }
}
