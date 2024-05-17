package com.riojack.garden;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.Terminated;
import akka.actor.typed.javadsl.Behaviors;
import com.riojack.garden.actors.Garden;
import com.riojack.garden.messages.Onion;
import com.riojack.garden.messages.Plant;

public class App {
    private static Behavior<Void> create() {
        return Behaviors.setup(
                ctx -> {
                    ActorRef<Plant> garden = ctx.spawn(Garden.create(), "garden");
                    ctx.watch(garden);
                    garden.tell(Plant.builder().species(Onion.builder().build()).build());

                    return Behaviors.receive(Void.class)
                            .onSignal(Terminated.class, sig -> Behaviors.stopped())
                            .build();
                });
    }

    public static void main(String[] args) {
        ActorSystem.create(create(), "my-garden");
    }
}
