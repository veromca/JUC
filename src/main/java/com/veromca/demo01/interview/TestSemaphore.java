package com.veromca.demo01.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class TestSemaphore {
    volatile List lists = new ArrayList();

    private void add(Object o) {
        lists.add(o);

    }

    private int size() {
        return lists.size();
    }

    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        TestSemaphore list = new TestSemaphore();
        t1 = new Thread(() -> {
            try {
                semaphore.acquire();
                for (int i = 0; i < 5; i++) {
                    list.add(new Object());
                    System.out.println("add " + i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
            try {
                t2.start();
                t2.join();// 让线程2先执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                semaphore.acquire();
                for (int i = 5; i < 10; i++) {
                    list.add(new Object());
                    System.out.println("add " + i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }


        }, "T1");

        t2 = new Thread(() -> {
            if (list.size() == 5) {
                try {
                    semaphore.acquire();
                    System.out.println("T2 结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }

        }, "T2");

        t1.start();

    }
}
