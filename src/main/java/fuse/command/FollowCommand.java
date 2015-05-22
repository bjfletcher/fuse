package fuse.command;

/**
 * A follow command.
 */
public class FollowCommand extends Command {

  final public String anotherUserName;

  /**
   * Creates a follow command.
   * @param userName the follower's username
   * @param anotherUserName the followed's username
   */
  public FollowCommand(String userName, String anotherUserName) {
    super(userName);
    this.anotherUserName = anotherUserName;
  }

}
