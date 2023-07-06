package com.abstractbehavior;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class GreeterMain2 extends AbstractBehavior<GreeterMain2.SaySomething> {

    ActorContext<SaySomething> context;

    interface SaySomething{}

    public static class SayHello implements SaySomething {
        public final String name;

        public SayHello(String name) {
            this.name = name;
        }
    }

    public static class SayGoodbye implements SaySomething {
        public final String name;

        public SayGoodbye(String name) {
            this.name = name;
        }
    }

    private final ActorRef<Greeter.Greet> greeter;

    public static Behavior<SaySomething> create() {
        return Behaviors.setup(GreeterMain2::new);
    }

    private GreeterMain2(ActorContext<SaySomething> context) {
        super(context);
        //#create-actors
        this.context = context;
        greeter = context.spawn(Greeter.create(), "greeter");
        //#create-actors
    }

    @Override
    public Receive<SaySomething> createReceive() {
        return newReceiveBuilder()
          .onMessage(SayHello.class, this::onSayHello)
          .onMessage(SayGoodbye.class, this::onSayGoodbye)
          .build();
    }

    private Behavior<SaySomething> onSayHello(SayHello command) {
        //#create-actors
        ActorRef<Greeter.Greeted> replyTo =
                // getContext().spawn(GreeterBot.create(3), command.name);
          this.context.spawn(GreeterBot.create(3), command.name);
        greeter.tell(new Greeter.Greet(command.name, replyTo));
        //#create-actors
        return this;
    }

    private Behavior<SaySomething> onSayGoodbye(SayGoodbye command) {
        //#create-actors
        ActorRef<Greeter.Greeted> replyTo =
          // getContext().spawn(GreeterBot.create(3), command.name);
          this.context.spawn(GreeterBot.create(5), command.name);
        greeter.tell(new Greeter.Greet(command.name, replyTo));
        //#create-actors
        return this;
    }
}
