package org.example.micrometer.meters;

import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.MeterRegistry;
import org.example.MetricsSPI;

public class ActiveTaskMetrics implements MetricsSPI<LongTaskTimer.Sample> {

  private final MeterRegistry registry;

  public ActiveTaskMetrics(MeterRegistry registry) {
    this.registry = registry;
  }

  @Override
  public LongTaskTimer.Sample messageReceived(String address) {
    return LongTaskTimer.builder("messagePending")
      .tags("address", address)
      .register(registry).start();
  }

  @Override
  public void messageProcessed(String address, LongTaskTimer.Sample sample) {
    sample.stop();
  }
}
