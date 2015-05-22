package fuse.util;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.units.JustNow;
import org.ocpsoft.prettytime.units.Millisecond;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * A formatter for times to become easy-read and relative.
 *
 * <p>This uses a third-party library <a href="https://github.com/ocpsoft/prettytime">PrettyTime</a>.</p>
 */
public class TimeFormatter {

  /**
   * Creates an easy-read relative time.
   * @param when the time to when we want a relative time from now
   * @return a relative time
   */
  public static String prettyTime(LocalDateTime when) {
    LocalDateTime from = LocalDateTime.now();
    PrettyTime prettyTime = new PrettyTime(asDate(from));
    prettyTime.removeUnit(JustNow.class); // don't want "moments ago"
    prettyTime.removeUnit(Millisecond.class); // don't want "123 milliseconds ago"
    return prettyTime.format(asDate(when)).replaceFirst("1 second from now", "just now"); // workaround for bug :(
  }

  /**
   * Converts from {@code LocalDateTime} to {@code Date}.
   * @param localDateTime the {@code LocalDateTime} to convert
   * @return the converted {@code Date} date
   */
  public static Date asDate(final LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

}
