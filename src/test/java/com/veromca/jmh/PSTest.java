package com.veromca.jmh;

import org.openjdk.jmh.annotations.*;

/**
 * JMH Java׼���Թ����׼�
 */
public class PSTest {
    @Benchmark //������һ�δ���
    @Warmup(iterations = 1,time = 3)//Ԥ�ȣ�����JVM�ж����ض����������Ż������ػ�����Ԥ�ȶ��ڲ��Խ������Ҫ
    @Fork(5)//�߳���
    @BenchmarkMode(Mode.Throughput)//��׼���Ե�ģʽ ������
    @Measurement(iterations = 1,time = 3)//�ܹ�ִ�ж��ٴβ���
    public void testForEach() {
        PS.foreach();
    }
}