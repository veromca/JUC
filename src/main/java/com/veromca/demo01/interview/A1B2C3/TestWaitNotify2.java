package com.veromca.demo01.interview.A1B2C3;

/**
 * wait notify ʵ��
 * Ҫ�����߳�˳���ӡA1B2C3....Z26
 * �����߳̽��������A1B2C3....Z26
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
                        lock.wait();// ��֤t2�ȴ�ӡ
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 0; i < arary1.length; i++) {
                    System.out.print(arary1[i]);// �ȴ�ӡ����
                    lock.notify();
                    try {
                        lock.wait();// // �����Լ�
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
                    System.out.print(arary2[i]); // ��ӡ��ĸ
                    lockFlag=false;
                    try {
                        lock.notify();
                        lock.wait();// // �����Լ�
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();// ����t1
            }
        },"T2").start();
    }
}
