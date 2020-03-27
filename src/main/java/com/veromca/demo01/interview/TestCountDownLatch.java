package com.veromca.demo01.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * <p>
 * 给lists添加volatile之后，t2能够接到通知，但是，t2线程的死循环很浪费cpu，如果不用死循环，该怎么做呢？
 * <p>
 * 这里使用wait和notify做到，wait会释放锁，而notify不会释放锁
 * 需要注意的是，运用这种方法，必须要保证t2先执行，也就是首先让t2监听才可以
 * <p>
 * 阅读下面的程序，并分析输出结果
 * 可以读到输出结果并不是size=5时t2退出，而是t1结束时t2才接收到通知而退出
 * 想想这是为什么？
 * <p>
 * notify之后，t1必须释放锁，t2退出后，也必须notify，通知t1继续执行
 * 整个通信过程比较繁琐
 * <p>
 * 使用Latch（门闩）替代wait notify来进行通知
 * 好处是通信方式简单，同时也可以指定等待时间
 * 使用await和countdown方法替代wait和notify
 * CountDownLatch不涉及锁定，当count的值为零时当前线程继续运行
 * 当不涉及同步，只是涉及线程通信的时候，用synchronized + wait/notify就显得太重了
 * 这时应该考虑countdownlatch/cyclicbarrier/semaphore
 */
public class TestCountDownLatch {

    volatile List lists = new ArrayList();

    private void add(Object o) {
        lists.add(o);

    }

    private int size() {
        return lists.size();
    }

    private static CountDownLatch latch1 = new CountDownLatch(1);
    private static CountDownLatch latch2 = new CountDownLatch(1);

    public static void main(String[] args) {
        TestCountDownLatch list = new TestCountDownLatch();
        //线程2 监听元素个数
        new Thread(() -> {
            System.out.println("T2 启动");
            if (list.size() != 5) {
                try {
                    latch1.await();// 锁住线程，让线程1执行打印
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (list.size() == 5) {
                System.out.println("T2 结束");
                latch2.countDown();// size等于5时，线程2打印完毕之后，打卡门闩
            }

        },"T2").start();

        //线程1 添加元素
        new Thread(() -> {
            System.out.println("T1 启动");
            for (int i = 0; i < 10; i++) {
                if (list.size() == 5) {
                    latch1.countDown();//释放门闩，让线程2执行打印
                    try {
                        latch2.await();// size等于5时，让第二个门闩锁住，等待线程2打印完毕
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.add(new Object());
                System.out.println("add " + i);
            }

        },"T1").start();


    }

}
