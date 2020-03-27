package com.veromca.demo01.interview.A1B2C3;

import java.util.concurrent.locks.LockSupport;

/**
 * Ҫ�����߳�˳���ӡA1B2C3....Z26
 * �����߳̽��������1A2B3C4D5E6F7G
 */
public class TestLockSuport2 {
    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        String[] arary1 = new String[]{"1", "2", "3", "4", "5", "6", "7"};
        String[] arary2 = new String[]{"A", "B", "C", "D", "E", "F", "G"};
        t1 = new Thread(() -> {
            for (int i = 0; i < arary1.length; i++) {
                System.out.print(arary1[i]);// �ȴ�ӡ����
                LockSupport.unpark(t2);// ����t2
                LockSupport.park();// // �����Լ�
            }
        }, "T1");

        t2 = new Thread(() -> {
            for (int i = 0; i < arary2.length; i++) {
                LockSupport.park();// �����Լ�
                System.out.print(arary2[i]); // ��ӡ��ĸ
                LockSupport.unpark(t1);// ����t1
            }
        },"T2");
        t1.start();
        t2.start();
    }

}
