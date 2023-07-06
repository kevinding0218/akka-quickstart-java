package com.abstractbehavior;

import akka.actor.typed.ActorSystem;

import java.io.IOException;
public class AkkaQuickstart {
  public static void main(String[] args) {
    //#actor-system
    final ActorSystem<GreeterMain.SayHello> greeterMain = ActorSystem.create(GreeterMain.create(), "helloakka");
    final ActorSystem<GreeterMain2.SaySomething> greeterMain2 = ActorSystem.create(GreeterMain2.create(), "interactWithAkka");
    //#actor-system

    //#main-send-messages
    // greeterMain.tell(new GreeterMain.SayHello("Charles"));

    greeterMain2.tell(new GreeterMain2.SayHello("Hello_to_Charles"));
    greeterMain2.tell(new GreeterMain2.SayGoodbye("GoodBye_to_Akka"));
    //#main-send-messages

    try {
      System.out.println(">>> Press ENTER to exit <<<");
      System.in.read();
    } catch (IOException ignored) {
    } finally {
      greeterMain.terminate();
    }
  }
}
