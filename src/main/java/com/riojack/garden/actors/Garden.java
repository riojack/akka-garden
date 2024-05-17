package com.riojack.garden.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.riojack.garden.messages.Plant;
import org.slf4j.Logger;

public class Garden extends AbstractBehavior<Plant> {
  private Logger log = getContext().getLog();
  public static Behavior<Plant> create() {
    return Behaviors.setup(Garden::new);
  }

  private Garden(ActorContext<Plant> context) {
    super(context);
  }

  @Override
  public Receive<Plant> createReceive() {
    return newReceiveBuilder().onMessage(Plant.class, this::onReceivePlant).build();
  }

  private Behavior<Plant> onReceivePlant(Plant plant) {
    log.info("Got " + plant.getSpecies().getClass().getName());
    return Behaviors.stopped();
  }
}
