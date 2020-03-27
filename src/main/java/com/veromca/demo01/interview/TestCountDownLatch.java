package com.veromca.demo01.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * �����������⣺���Ա�����
 * ʵ��һ���������ṩ����������add��size
 * д�����̣߳��߳�1���10��Ԫ�ص������У��߳�2ʵ�ּ��Ԫ�صĸ�������������5��ʱ���߳�2������ʾ������
 * <p>
 * ��lists���volatile֮��t2�ܹ��ӵ�֪ͨ�����ǣ�t2�̵߳���ѭ�����˷�cpu�����������ѭ��������ô���أ�
 * <p>
 * ����ʹ��wait��notify������wait���ͷ�������notify�����ͷ���
 * ��Ҫע����ǣ��������ַ���������Ҫ��֤t2��ִ�У�Ҳ����������t2�����ſ���
 * <p>
 * �Ķ�����ĳ��򣬲�����������
 * ���Զ���������������size=5ʱt2�˳�������t1����ʱt2�Ž��յ�֪ͨ���˳�
 * ��������Ϊʲô��
 * <p>
 * notify֮��t1�����ͷ�����t2�˳���Ҳ����notify��֪ͨt1����ִ��
 * ����ͨ�Ź��̱ȽϷ���
 * <p>
 * ʹ��Latch�����ţ����wait notify������֪ͨ
 * �ô���ͨ�ŷ�ʽ�򵥣�ͬʱҲ����ָ���ȴ�ʱ��
 * ʹ��await��countdown�������wait��notify
 * CountDownLatch���漰��������count��ֵΪ��ʱ��ǰ�̼߳�������
 * �����漰ͬ����ֻ���漰�߳�ͨ�ŵ�ʱ����synchronized + wait/notify���Ե�̫����
 * ��ʱӦ�ÿ���countdownlatch/cyclicbarrier/semaphore
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
        //�߳�2 ����Ԫ�ظ���
        new Thread(() -> {
            System.out.println("T2 ����");
            if (list.size() != 5) {
                try {
                    latch1.await();// ��ס�̣߳����߳�1ִ�д�ӡ
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (list.size() == 5) {
                System.out.println("T2 ����");
                latch2.countDown();// size����5ʱ���߳�2��ӡ���֮�󣬴�����
            }

        },"T2").start();

        //�߳�1 ���Ԫ��
        new Thread(() -> {
            System.out.println("T1 ����");
            for (int i = 0; i < 10; i++) {
                if (list.size() == 5) {
                    latch1.countDown();//�ͷ����ţ����߳�2ִ�д�ӡ
                    try {
                        latch2.await();// size����5ʱ���õڶ���������ס���ȴ��߳�2��ӡ���
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
