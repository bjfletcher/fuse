package fuse.response;

import fuse.command.Command;
import fuse.command.TimelineCommand;
import fuse.command.WallCommand;
import fuse.util.TimeFormatter;

import java.util.Optional;
import java.util.function.Function;

/**
 * A formatter for the responses.
 */
public class ResponseFormatter {

  /**
   * Creates a consumer that formats too.
   * @param command the {@code command} for the responses that this will consume and format
   * @return a formatting consumer based on the command
   */
  public static Optional<Function<Response, String>> get(Command command) {
    if (command instanceof TimelineCommand)
      return Optional.of(ResponseFormatter::timeline);
    else if (command instanceof WallCommand)
      return Optional.of(ResponseFormatter::wall);
    else
      return Optional.empty();
  }

  private static String timeline(Response response) {
    PostResponse postResponse = (PostResponse) response;
    return postResponse.message + " (" + TimeFormatter.prettyTime(postResponse.when) + ")";
  }

  private static String wall(Response response) {
    PostResponse postResponse = (PostResponse) response;
    return postResponse.userName + " -> " + postResponse.message + " (" + TimeFormatter.prettyTime(postResponse.when) + ")";
  }

}
