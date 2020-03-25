package demo01.interview.A1B2C3;

import java.util.concurrent.locks.LockSupport;

/**
 * wait notify ʵ��
 * Ҫ�����߳�˳���ӡA1B2C3....Z26
 * �����߳̽��������A1B2C3....Z26
 * �˷����������⣺���ܱ�֤t1����t2�ȴ�ӡ
 */
public class TestWaitNotify {
    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        String[] arary1 = new String[]{"1", "2", "3", "4", "5", "6", "7"};
        String[] arary2 = new String[]{"A", "B", "C", "D", "E", "F", "G"};
       final Object lock = new Object();
        t1 = new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < arary1.length; i++) {
                    System.out.print(arary1[i]);// �ȴ�ӡ����
                    try {
                        lock.notify();
                        lock.wait();// // �����Լ�
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        }, "T1");

        t2 = new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < arary2.length; i++) {
                    System.out.print(arary2[i]); // ��ӡ��ĸ
                    try {
                        lock.notify();
                        lock.wait();// // �����Լ�
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();// ����t1
            }
        },"T2");
        t1.start();
        t2.start();
    }
}
