package fuse.command;

import org.junit.Assert;
import org.junit.Test;

public class CommandParserTest {

  @Test
  public void post() {
    PostCommand postCommand = (PostCommand) CommandParser.parse("Alice -> I love the weather today").get();
    Assert.assertEquals(postCommand.userName, "Alice");
    Assert.assertEquals(postCommand.message, "I love the weather today");
  }

  @Test
  public void timeline() {
    TimelineCommand timelineCommand = (TimelineCommand) CommandParser.parse("Bob").get();
    Assert.assertEquals(timelineCommand.userName, "Bob");
  }

  @Test
  public void follow() {
    FollowCommand followCommand = (FollowCommand) CommandParser.parse("Charlie follows Alice").get();
    Assert.assertEquals(followCommand.userName, "Charlie");
    Assert.assertEquals(followCommand.anotherUserName, "Alice");
  }

  @Test
  public void wall() {
    WallCommand wallCommand = (WallCommand) CommandParser.parse("Charlie wall").get();
    Assert.assertEquals(wallCommand.userName, "Charlie");
  }

}
