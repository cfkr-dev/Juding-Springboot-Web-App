package es.dawgroup2.juding.main;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

@Component
public class DateService {
    public Date stringToDate(String dateString) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", new Locale("es"));
        format.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));

        java.util.Date date = format.parse(dateString);

        return new Date(date.getTime());
    }
}
