package fuse.command;

/**
 * A post command.
 */
public class PostCommand extends Command {

  public final String message;

  /**
   * Creates a post command.
   * @param userName the post's username
   * @param message the post's message
   */
  public PostCommand(final String userName, final String message) {
    super(userName);
    this.message = message;
  }

}
