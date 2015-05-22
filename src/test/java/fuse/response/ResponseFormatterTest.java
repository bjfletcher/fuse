package fuse.response;

import fuse.command.TimelineCommand;
import fuse.command.WallCommand;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.function.Function;

public class ResponseFormatterTest {

  @Test
  public void timeline() {
    Function<Response, String> formatter = ResponseFormatter.get(new TimelineCommand("Alice")).get();
    Response response = new PostResponse("Bob", "Good game though.", LocalDateTime.now().minusSeconds(2));
    Assert.assertEquals("Good game though. (2 seconds ago)", formatter.apply(response));
  }

  @Test
  public void wall() {
    Function<Response, String> formatter = ResponseFormatter.get(new WallCommand("Alice")).get();
    Response response = new PostResponse("Bob", "Good game though.", LocalDateTime.now().minusMinutes(15));
    Assert.assertEquals("Bob -> Good game though. (15 minutes ago)", formatter.apply(response));
  }

}
