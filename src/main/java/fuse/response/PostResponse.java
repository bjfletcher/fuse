package fuse.response;

import java.time.LocalDateTime;

/**
 * A post response.
 */
public class PostResponse extends Response {

  public final String userName;
  public final String message;
  public LocalDateTime when;

  /**
   * Creates a post response.
   * @param userName the post's username
   * @param message the post's message
   * @param when the post's date and time
   */
  public PostResponse(final String userName, final String message, final LocalDateTime when) {
    this.userName = userName;
    this.message = message;
    this.when = when;
  }

}
