package com.veromca.demo01.sync;

import java.util.concurrent.TimeUnit;

/**
 * 实现账号充值和查询一个小程序：
 * 一个类的两个方法，一个充值（写：加锁），一个查询账户余额（读不加锁），是否可行？会出现什么问题？
 *
 */
public class TestAccount {
    String name;
    double balance;

    public synchronized void setBalance(String name, double balance) {
        this.name = name;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public double getBalance(String name) {
        return this.balance;
    }

    public static void main(String[] args) {
        TestAccount testAccount = new TestAccount();
        new Thread(() -> testAccount.setBalance("zhangsan", 100.0)).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(testAccount.getBalance("zhangsan"));
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(testAccount.getBalance("zhangsan"));
    }


}
