package com.veromca.demo01.singleton;

/**
 * 饿汉式单例:在程序加载时创建类的实例
 */
public class TestSingleton02 {
    public static final TestSingleton02 instance = new TestSingleton02();

    private TestSingleton02() {

    }

    public static TestSingleton02 getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(getInstance().hashCode());
            }
        }).start();
    }
}
