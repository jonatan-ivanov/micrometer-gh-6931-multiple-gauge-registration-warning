package org.example.micrometer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.example.MetricsSPI;
import org.example.micrometer.meters.LongGauges;

import java.util.concurrent.atomic.LongAdder;

public class MicrometerMetrics implements MetricsSPI<Void> {

  private final LongGauges longGauges;
  private final MeterRegistry registry;

  public MicrometerMetrics(LongGauges longGauges, MeterRegistry registry) {
    this.longGauges = longGauges;
    this.registry = registry;
  }

  @Override
  public Void messageReceived(String address) {
    LongAdder pending = longGauges.builder("messagePending", LongAdder::doubleValue)
      .tags(Tags.of("address", address))
      .register(registry);
    pending.increment();
    return null;
  }

  @Override
  public void messageProcessed(String address, Void context) {
    LongAdder pending = longGauges.builder("messagePending", LongAdder::doubleValue)
      .tags(Tags.of("address", address))
      .register(registry);
    pending.decrement();
  }
}
