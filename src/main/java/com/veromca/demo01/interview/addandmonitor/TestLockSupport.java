package com.veromca.demo01.interview.addandmonitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

public class TestLockSupport {
    // ���volatile��ʹt2�ܹ��õ�֪ͨ
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        TestLockSupport c = new TestLockSupport();
        t1 = new Thread(() -> {
            System.out.println("t1����");
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add " + i);
                if (c.size() == 5) {
                    LockSupport.unpark(t2);// ���߳�2
                    LockSupport.park();
                }
            }
        }, "t1");

        t2 = new Thread(() -> {
            System.out.println("t2����");
            //if (c.size() != 5) {
                LockSupport.park();
           // }
            LockSupport.unpark(t1);
            System.out.println("t2 ����");


        }, "t2");
        t2.start();
        t1.start();
    }
}
