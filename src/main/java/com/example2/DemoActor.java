package com.example2;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class DemoActor extends AbstractActor {

  private final int magicNumber;

  public DemoActor(int magicNumber) {
    this.magicNumber = magicNumber;
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
      .match(Integer.class, i -> {
        getSender().tell(i + magicNumber, getSelf());
      })
      .build();
  }

  public static Props props(int magicNumber) {

    // Akka Props is used for creating Actor instances
    return Props.create(DemoActor.class, () -> new DemoActor(magicNumber));
  }

  public static void main(String[] args) {
    ActorSystem system = ActorSystem.create("DemoSystem");
    ActorRef demo = system.actorOf(DemoActor.props(42), "demo");
    demo.tell(123, ActorRef.noSender());
  }
}
