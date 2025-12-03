package org.example;

import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.example.micrometer.meters.ActiveTaskMetrics;

public class Main {

  public static void main(String[] args) {
    SimpleMeterRegistry registry = new SimpleMeterRegistry();
//    registry.config().meterFilter(MeterFilter.ignoreTags("address"));

//    MetricsSPI<Void> metricsSPI = new MicrometerMetrics(new LongGauges(new ConcurrentHashMap<>()), registry);
    MetricsSPI<LongTaskTimer.Sample> metricsSPI = new ActiveTaskMetrics(registry);
    MessageBus bus = new MessageBus(metricsSPI);

    bus.registerConsumer("foo", s -> {
      System.out.println(registry.getMetersAsString());
      System.out.printf("foo: %s%n", s);
    });
    bus.registerConsumer("bar", s -> {
      System.out.println(registry.getMetersAsString());
      System.out.printf("bar: %s%n", s);
    });

    bus.deliverMessage("foo", "Hello");
    bus.deliverMessage("foo", "Hello");
    bus.deliverMessage("foo", "Hello");

    bus.deliverMessage("bar", "Hi");
    bus.deliverMessage("bar", "Hi");

    System.out.println(registry.getMetersAsString());
  }
}
