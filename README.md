# The Simplest Countdown Timer Ever

An experiment in making the simplest possible timeboxing timer, driving ideas:

1. People should have a *vague* idea of time left.
  * A clock is too distracting - people end up watching seconds tick one at a
    time.
  * Conversely, with no idea of time left, it's too easy to overrun as
    discussions get bogged down.
  * Announcing the time remaining at certain points in the meeting interrupts
    conversations.
2. No distracting movement or colours - ever tried handling a meeting when
   there's anything moving on the TV monitor?
3. One circle = 1 hour for a consistent frame of reference. If you're
   timeboxing over 1 hour you are doing it wrong anyway.
4. No pause. Pausing is cheating.

The question is: Can a constantly visible, but unintrusive, tool encourage
better use of timeboxed meetings?

Available at [timeboxing.rocks](http://timeboxing.rocks)

## Using / Development

```clojure
lein cljsbuild auto # or once
```

Then open index.html

