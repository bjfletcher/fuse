package fuse;

import fuse.command.*;
import fuse.response.PostResponse;
import fuse.response.Response;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A Fuse service. with in-memory persistence. This service can be built upon to use a proper backend such as Storm,
 * Spark or Cassandra.
 */
public class FuseService {

  /**
   * Linked lists are ideal for first in first out (FIFO) operations, streaming and processing. See {@link FuseService}.
   */
  private final LinkedList<Command> commands = new LinkedList<>();

  /**
   * Runs a command.
   * @param command the command to run
   * @param consumerOptional the consumer to feed any responses to, required only for certain commands
   */
  public void run(Command command, Optional<Consumer<Response>> consumerOptional) {
    if (command instanceof PostCommand)
      run((PostCommand) command);
    else if (command instanceof FollowCommand)
      run((FollowCommand) command);
    if (command instanceof TimelineCommand)
      run((TimelineCommand) command, consumerOptional.get());
    else if (command instanceof WallCommand)
      run((WallCommand) command, consumerOptional.get());
  }

  /**
   * Runs a post command.
   * @param postCommand the command
   */
  private void run(PostCommand postCommand) {
    commands.addFirst(postCommand);
  }

  /**
   * Runs a timeline command.
   * @param timelineCommand the command
   * @param responseConsumer the consumer to feed any responses
   */
  private void run(TimelineCommand timelineCommand, Consumer<Response> responseConsumer) {
    commands.stream()
        .filter(command -> command instanceof PostCommand)
        .map(command -> (PostCommand) command)
        .filter(postCommand -> postCommand.userName.equals(timelineCommand.userName))
        .map(FuseService::asResponse)
        .forEach(responseConsumer);
  }

  /**
   * Runs a follow command.
   * @param followCommand the command
   */
  private void run(FollowCommand followCommand) {
    commands.addFirst(followCommand);
  }

  /**
   * Runs a wall command.
   * @param wallCommand the command
   * @param responseConsumer the consumer to feed any responses
   */
  private void run(WallCommand wallCommand, Consumer<Response> responseConsumer) {
    Set<String> userFollows = commands.stream()
      .filter(command -> command instanceof FollowCommand)
      .map(command -> (FollowCommand) command)
      .filter(followCommand -> followCommand.userName.equals(wallCommand.userName))
      .map(followCommand -> followCommand.anotherUserName)
      .collect(Collectors.toSet());
    commands.stream()
      .filter(command -> command instanceof PostCommand)
      .map(command -> (PostCommand) command)
      .filter(postCommand -> postCommand.userName.equals(wallCommand.userName) || userFollows.contains(postCommand.userName))
      .map(FuseService::asResponse)
      .forEach(responseConsumer);
  }

  /**
   * Takes data from a command and creates a response with the same data.
   *
   * @implNote This is a loopback.
   *
   * @param postCommand the command
   * @return the response with the same data
   */
  private static PostResponse asResponse(PostCommand postCommand) {
    return new PostResponse(postCommand.userName, postCommand.message, postCommand.when);
  }

}
