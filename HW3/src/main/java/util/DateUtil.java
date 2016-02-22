package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.sql.Date;

/**
 * Created by Andrii_Pinchuk on 2/10/2016.
 */
public class DateUtil {
    private DateUtil() {
    }

    public static Date parseDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsed = format.parse(date);
            Date sql = new Date(parsed.getTime());
            return sql;
        } catch (ParseException e) {
            return null;
        }
    }
}
