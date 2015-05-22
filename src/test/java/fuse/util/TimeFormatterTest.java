package fuse.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class TimeFormatterTest {

  @Test
  public void justNow() {
    LocalDateTime now = LocalDateTime.now();
    String nowStr = TimeFormatter.prettyTime(now);
    Assert.assertTrue("just now".equals(nowStr) || "1 second ago".equals(nowStr)); // "just now" or "1 second ago" depending on platform
  }

  @Test
  public void oneNanoAgo() {
    LocalDateTime oneNanoAgo = LocalDateTime.now().minusNanos(1);
    String oneNanoAgoStr = TimeFormatter.prettyTime(oneNanoAgo);
    Assert.assertTrue("just now".equals(oneNanoAgoStr) || "1 second ago".equals(oneNanoAgoStr)); // "just now" or "1 second ago" depending on platform
  }

  @Test
  public void secondsAgo() {
    LocalDateTime secondsAgo = LocalDateTime.now().minusSeconds(5L);
    Assert.assertEquals("5 seconds ago", TimeFormatter.prettyTime(secondsAgo));

  }

  @Test
  public void hoursAgo() {
    LocalDateTime hoursAgo = LocalDateTime.now().minusHours(1L);
    Assert.assertEquals("1 hour ago", TimeFormatter.prettyTime(hoursAgo));

  }

  @Test
  public void monthsAgo() {
    LocalDateTime monthsAgo = LocalDateTime.now().minusMonths(11L);
    Assert.assertEquals("11 months ago", TimeFormatter.prettyTime(monthsAgo));
  }

}
