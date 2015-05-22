package fuse.command;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A parser for the commands.
 */
public class CommandParser {

  public static final Pattern POST_COMMAND_PATTERN = Pattern.compile("^(\\S*) -> (.*)$");
  public static final Pattern FOLLOW_COMMAND_PATTERN = Pattern.compile("^(\\S*) follows (\\S*)$");
  public static final Pattern TIMELINE_COMMAND_PATTERN = Pattern.compile("^(\\S*)$");
  public static final Pattern WALL_COMMAND_PATTERN = Pattern.compile("^(\\S*) wall$");

  /**
   * Parses a string command.
   * @param line the string command
   * @return an {@code Optional} with any parsed {@code Command}
   */
  public static Optional<Command> parse(final String line) {
    Command command = null;
    Matcher matcher;
    if ((matcher = POST_COMMAND_PATTERN.matcher(line)).find()) {
      command = new PostCommand(matcher.group(1), matcher.group(2));
    } else if ((matcher = FOLLOW_COMMAND_PATTERN.matcher(line)).find()) {
      command = new FollowCommand(matcher.group(1), matcher.group(2));
    } else if ((matcher = TIMELINE_COMMAND_PATTERN.matcher(line)).find()) {
      command = new TimelineCommand(matcher.group(1));
    } else if ((matcher = WALL_COMMAND_PATTERN.matcher(line)).find()) {
      command = new WallCommand(matcher.group(1));
    }
    return Optional.ofNullable(command);
  }

}
