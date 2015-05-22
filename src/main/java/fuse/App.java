package fuse;

import fuse.command.CommandParser;
import fuse.response.Response;
import fuse.response.ResponseFormatter;
import jline.console.ConsoleReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/***
 * A Fuse application.
 */
public class App {

  public static final String WELCOME = "Welcome to Fuse";
  public static final String PROMPT = "> ";
  public static final String QUIT_COMMAND = ":q";

  /**
   * Runs the Fuse application.
   *
   * <p>This goes into a loop, doing the following:</p>
   *
   * <ol>
   *     <li>reads a line</li>
   *     <li>parses the line for any command</li>
   *     <li>if command requires a consumer with formatting, runs command with it</li>
   *     <li>if command doesn't require a consumer, runs command without any</li>
   * </ol>
   *
   * To exit application, use the {@link #QUIT_COMMAND} command.
   *
   * @param fuseService the Fuse service
   * @param reader the reader to gets commands from
   * @param writer the writer to outputs responses to
   */
  public App(final FuseService fuseService, final Supplier<String> reader, final Consumer<String> writer) {
    writer.accept(WELCOME);
    String line;
    while ((line = reader.get()) != null && !line.equals(QUIT_COMMAND)) {
      CommandParser.parse(line).ifPresent(command -> {
        Optional<Consumer<Response>> consumer = ResponseFormatter.get(command)
          // consumer with formatting
          .map(formatter -> Optional.of(consumerWithFormatter(formatter, writer)))
          // no consumer
          .orElse(Optional.empty());
        fuseService.run(command, consumer);
      });
    }
  }

  /**
   * Turns the consumer into a formatter-consumer.
   * @param formatter the formatter
   * @param consumer the consumer
   * @return the formatter-consumer
   */
  private static Consumer<Response> consumerWithFormatter(Function<Response, String> formatter, Consumer<String> consumer) {
    return response -> consumer.accept(formatter.apply(response));
  }

  /**
   * Runs a command-line Fuse application.
   *
   * <p>This uses a third-party library <a href="https://github.com/jline/jline2">JLine</a> for the command-line interface.</p>
   * @param args not used
   */
  public static void main(final String[] args) throws IOException {
    final FuseService fuseService = new FuseService();
    final ConsoleReader jLine = new ConsoleReader();
    jLine.setPrompt(PROMPT);
    final Supplier<String> reader = () -> {
      try { return jLine.readLine(); } catch (IOException e) { throw new UncheckedIOException(e); }
    };
    final Consumer<String> writer = new PrintWriter(jLine.getOutput())::println;
    new App(fuseService, reader, writer);
  }

}
