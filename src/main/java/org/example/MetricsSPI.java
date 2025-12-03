package org.example;

public interface MetricsSPI<C> {

  C messageReceived(String address);

  void messageProcessed(String address, C context);

}
