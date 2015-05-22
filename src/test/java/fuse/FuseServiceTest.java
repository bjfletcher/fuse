package fuse;

import fuse.command.*;
import fuse.response.PostResponse;
import fuse.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class FuseServiceTest {

  FuseService fuseService;
  Consumer<Response> consumer;
  ArgumentCaptor<Response> response;

  @Before
  public void before() {
    fuseService = new FuseService();
    consumer = Mockito.mock(Consumer.class);
    response = ArgumentCaptor.forClass(Response.class);
  }

  // TESTS

  @Test
  public void post() {
    fuseService.run(new PostCommand("Alice", "I love the weather today"), Optional.empty());
  }

  @Test
  public void timeline() {
    fuseService.run(new TimelineCommand("Alice"), Optional.of(consumer));
  }

  @Test
  public void timelines() {
    //given
    fuseService.run(new PostCommand("Alice", "I love the weather today"), Optional.empty());
    fuseService.run(new PostCommand("Bob", "Damn! We lost!"), Optional.empty());
    fuseService.run(new PostCommand("Bob", "Good game though."), Optional.empty());
    fuseService.run(new PostCommand("Charlie", "I'm in New York today! Anyone wants to have a coffee?"), Optional.empty());

    //when
    fuseService.run(new TimelineCommand("Bob"), Optional.of(consumer));

    //then
    Mockito.verify(consumer, Mockito.times(2)).accept(response.capture());
    Mockito.verifyNoMoreInteractions(consumer);
    // all responses for the timeline are posts
    List<PostResponse> postResponses = asPostResponses(response.getAllValues());
    // check the right messages come out and in the right order
    Assert.assertEquals("Good game though.", postResponses.get(0).message);
    Assert.assertEquals("Damn! We lost!", postResponses.get(1).message);
  }

  private List<PostResponse> asPostResponses(List<Response> responses) {
    return responses.stream().map(response -> (PostResponse) response).collect(Collectors.toList());
  }

  @Test
  public void follow() {
    fuseService.run(new FollowCommand("Charlie", "Alice"), Optional.empty());
  }

  @Test
  public void wall() {
    fuseService.run(new WallCommand("Alice"), Optional.of(consumer));
  }

  @Test
  public void walls() {
    //given
    fuseService.run(new PostCommand("Alice", "I love the weather today"), Optional.empty());
    fuseService.run(new PostCommand("Bob", "Damn! We lost!"), Optional.empty());
    fuseService.run(new PostCommand("Bob", "Good game though."), Optional.empty());
    fuseService.run(new PostCommand("Charlie", "I'm in New York today! Anyone wants to have a coffee?"), Optional.empty());
    fuseService.run(new FollowCommand("Charlie", "Alice"), Optional.empty());

    //when
    fuseService.run(new WallCommand("Charlie"), Optional.of(consumer));

    //then
    Mockito.verify(consumer, Mockito.times(2)).accept(response.capture());
    Mockito.verifyNoMoreInteractions(consumer);
    // all responses for the wall are posts
    List<PostResponse> postResponses = asPostResponses(response.getAllValues());
    // check the right messages come out and in the right order
    Assert.assertEquals("I'm in New York today! Anyone wants to have a coffee?", postResponses.get(0).message);
    Assert.assertEquals("I love the weather today", postResponses.get(1).message);
  }

  @Test
  public void postResponseFromCommand() {
    //given
    fuseService.run(new PostCommand("Alice", "I love the weather today"), Optional.empty());

    //when
    fuseService.run(new TimelineCommand("Alice"), Optional.of(consumer));

    //then
    Mockito.verify(consumer).accept(response.capture());
    PostResponse postResponse = (PostResponse) response.getValue();
    // check all the different data comes through
    Assert.assertEquals("Alice", postResponse.userName);
    Assert.assertEquals("I love the weather today", postResponse.message);
    Assert.assertTrue(LocalDateTime.now().plusSeconds(1).isAfter(postResponse.when));
    Assert.assertTrue(LocalDateTime.now().minusSeconds(1).isBefore(postResponse.when));
  }

}
