package fuse.command;

import java.time.LocalDateTime;

/**
 * Base class for the commands.
 */
public class Command {

  final public String userName;
  final public LocalDateTime when;

  /**
   * Creates a command.
   * @param userName the command's username
   */
  public Command(final String userName) {
    this.userName = userName;
    this.when = LocalDateTime.now();
  }

}
