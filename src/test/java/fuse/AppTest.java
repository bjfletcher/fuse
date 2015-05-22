package fuse;

import fuse.command.Command;
import fuse.response.PostResponse;
import fuse.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class AppTest {

  @Mock FuseService fuseService;
  @Mock Supplier<String> reader;
  @Mock Consumer<String> writer;

  @Test
  public void reader() {
    Mockito.when(reader.get()).thenReturn(":q");
    new App(fuseService, reader, writer);
    // this hangs if not exiting the loop
  }

  @Test
  public void writer() {
    Mockito.when(reader.get()).thenReturn(":q");
    new App(fuseService, reader, writer);
    Mockito.verify(writer).accept(App.WELCOME);
  }

  @Test
  public void integration() {
    Mockito.when(reader.get())
      .thenReturn("Alice -> I love the weather today")
      .thenReturn("Alice wall")
      .thenReturn(":q");

    new App(fuseService, reader, writer);

    Mockito.verify(writer).accept(App.WELCOME);
    ArgumentCaptor<Optional> consumer = ArgumentCaptor.forClass(Optional.class);
    Mockito.verify(fuseService, Mockito.times(2)).run(Mockito.any(Command.class), consumer.capture());
    Consumer<Response> responseConsumer = (Consumer<Response>) consumer.getValue().get();
    // return a response from the service
    responseConsumer.accept(new PostResponse("Alice", "I love the weather today", LocalDateTime.now().minusYears(5)));
    // should see a formatted response consumed by the writer
    Mockito.verify(writer).accept("Alice -> I love the weather today (5 years ago)"); // woohoooo!
  }

}
