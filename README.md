# Fuse

A console-based social networking application (similar to Twitter).

## Getting Started

### Docker

1. install [Docker](https://www.docker.com/)
1. pull image: `docker pull bjfletcher/fuse:latest`
1. run image: `docker run -it bjfletcher/fuse:latest`

Expected output:
```
$ docker pull bjfletcher/fuse:latest
latest: Pulling from bjfletcher/fuse
...
Status: Image is up to date for bjfletcher/fuse:latest

$ docker run -it bjfletcher/fuse:latest
Welcome to Fuse
>
```

### Local Machine

1. install [Java](https://www.java.com/) version 8 or higher
1. run `java -jar fuse-1.0-SNAPSHOT-jar-with-dependencies.jar`

For development notes, please see [CONTRIBUTING.md](CONTRIBUTING.md).

## Scenarios

### 1. Posting

Alice can publish messages to a personal timeline.

```
> Alice -> I love the weather today
> Bob -> Damn! We lost!
> Bob -> Good game though.
```

### 2. Reading

Bob can view Alice’s timeline.

```
> Alice
I love the weather today (5 minutes ago)
> Bob
Good game though. (1 minute ago)
Damn! We lost! (2 minutes ago)
```

### 3. Following

Charlie can subscribe to Alice’s and Bob’s timelines, and view an aggregated list of all subscriptions.

```
> Charlie -> I'm in New York today! Anyone wants to have a coffee?
> Charlie follows Alice
> Charlie wall
Charlie - I'm in New York today! Anyone wants to have a coffee? (2 seconds ago)
Alice - I love the weather today (5 minutes ago)

> Charlie follows Bob
> Charlie wall
Charlie - I'm in New York today! Anyone wants to have a coffee? (15 seconds ago)
Bob - Good game though. (1 minute ago)
Bob - Damn! We lost! (2 minutes ago)
Alice - I love the weather today (5 minutes ago)
```

