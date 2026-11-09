package org.example.jmh;

import java.util.concurrent.TimeUnit;

import org.example.App;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, warmups = 2)
@Warmup(iterations = 2)
public class InvertedFileBenchmark {
  @Param({  "src\\main\\java\\org\\example\\Files_for_Testing\\Sample4.txt",
       "src\\main\\java\\org\\example\\Files_for_Testing\\Sample3.txt",
       "src\\main\\java\\org\\example\\Files_for_Testing\\Sample2.txt",
       "src\\main\\java\\org\\example\\Files_for_Testing\\Sample1.txt" })
  String args;

  @Benchmark
  @Timeout(time = 5, timeUnit = TimeUnit.SECONDS)
  public void testInvertedFileBenchmark() {
    App.main(new String[] {args});
  }
}
