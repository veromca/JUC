package com.veromca.demo01.interview.A1B2C3;

/**
 * wait notify 实现
 * 要求用线程顺序打印A1B2C3....Z26
 * 两个线程交替输出：A1B2C3....Z26
 *
 */
public class TestWaitNotify2 {
    private static volatile boolean lockFlag = true;
    public static void main(String[] args) {
        String[] arary1 = new String[]{"1", "2", "3", "4", "5", "6", "7"};
        String[] arary2 = new String[]{"A", "B", "C", "D", "E", "F", "G"};
       final Object lock = new Object();
       new Thread(() -> {
            synchronized (lock) {
                while (lockFlag){
                    try {
                        lock.wait();// 保证t2先打印
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < arary1.length; i++) {
                    System.out.print(arary1[i]);// 先打印数组
                    lock.notify();
                    try {
                        lock.wait();// // 阻塞自己
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        }, "T1").start();

        new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < arary2.length; i++) {
                    System.out.print(arary2[i]); // 打印字母
                    lockFlag=false;
                    try {
                        lock.notify();
                        lock.wait();// // 阻塞自己
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();// 唤醒t1
            }
        },"T2").start();
    }
}
