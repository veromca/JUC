package com.veromca.jmh;

import org.openjdk.jmh.annotations.*;

/**
 * JMH Java准测试工具套件
 */
public class PSTest {
    @Benchmark //测试哪一段代码
    @Warmup(iterations = 1,time = 3)//预热，由于JVM中对于特定代码会存在优化（本地化），预热对于测试结果很重要
    @Fork(5)//线程数
    @BenchmarkMode(Mode.Throughput)//基准测试的模式 吞吐量
    @Measurement(iterations = 1,time = 3)//总共执行多少次测试
    public void testForEach() {
        PS.foreach();
    }
}