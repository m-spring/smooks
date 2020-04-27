package org.milyn.javabean.decoders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;
import org.milyn.assertion.AssertArgument;

import org.milyn.javabean.DataDecodeException;
import org.milyn.javabean.DataDecoder;
import org.milyn.javabean.DataEncoder;
import org.milyn.javabean.DecodeType;

/**
 * {@link java.util.Calendar} data decoder.
 * <p/>
 * Decodes the supplied string into a {@link java.util.Calendar} value
 * based on the supplied "{@link java.text.SimpleDateFormat format}" parameter.
 * <p/>
 * This decoder is synchronized on its underlying {@link SimpleDateFormat} instance.
 *
 * @see {@link LocaleAwareDateDecoder}
 *
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 * @author Pavel Kadlec
 * @author <a href="mailto:daniel.bevenius@gmail.com">daniel.bevenius@gmail.com</a>
 *
 */
@DecodeType(LocalDate.class)
public class LocalDateDecoder extends LocaleAwareDateDecoder implements DataDecoder, DataEncoder {

    @Override
    public Object decode(String data) throws DataDecodeException {
        if (decoder == null) {
            throw new IllegalStateException("Calendar decoder not initialised.  A decoder for this type (" + getClass().getName() + ") must be explicitly configured (unlike the primitive type decoders) with a date 'format'. See Javadoc.");
        }
        try {
            // Must be sync'd - DateFormat is not synchronized.
            synchronized(decoder) {
                decoder.parse(data.trim());
                decoder.parse(data.trim());
                Calendar calendar = (Calendar) decoder.getCalendar().clone();
                TimeZone tz = calendar.getTimeZone();
                ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
                return LocalDateTime.ofInstant(calendar.toInstant(), zid).toLocalDate();
            }
        } catch (ParseException e) {
            throw new DataDecodeException("Error decoding Date data value '" + data + "' with decode format '" + format + "'.", e);
        }
    }
    
    @Override
    public String encode(Object date) throws DataDecodeException {
        AssertArgument.isNotNull(date, "date");
        if(!(date instanceof LocalDate)) {
            throw new DataDecodeException("Cannot encode Object type '" + date.getClass().getName() + "'.  Must be type '" + LocalDate.class.getName() + "'.");
        }
        // Must be sync'd - DateFormat is not synchronized.
        synchronized(decoder) {
            LocalDate ld = (LocalDate) date;
            Calendar cal = Calendar.getInstance();
            cal.set(ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth());
            return decoder.format(cal.getTime());
        }
    }
}
