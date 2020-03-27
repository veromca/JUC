package demo01.queue;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionBoundedQueue {
    private LinkedList<Object> buffer;    //����������
    private int maxSize;           //�������ֵ�Ƕ���
    private Lock lock;
    private Condition fullCondition;
    private Condition notFullCondition;

    ConditionBoundedQueue(int maxSize) {
        this.maxSize = maxSize;
        buffer = new LinkedList<Object>();
        lock = new ReentrantLock();
        fullCondition = lock.newCondition();
        notFullCondition = lock.newCondition();
    }

    /**
     * ������
     *
     * @param obj
     * @throws InterruptedException
     */
    public void put(Object obj) {
        lock.lock();    //��ȡ��
        try {
            while (maxSize == buffer.size()) {
                try {
                    notFullCondition.await();       //���ˣ���ӵ��߳̽���ȴ�״̬
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            buffer.add(obj);
            fullCondition.signal(); //֪ͨ
        } finally {
            lock.unlock();
        }
    }

    /**
     * ������
     *
     * @return
     * @throws InterruptedException
     */
    public Object get() {
        Object obj;
        lock.lock();
        try {
            while (buffer.size() == 0) { //������û�������� �߳̽���ȴ�״̬
                try {
                    fullCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            obj = buffer.poll();
            notFullCondition.signal(); //֪ͨ
        } finally {
            lock.unlock();
        }
        return obj;
    }

    public static void main(String[] args) {
        ConditionBoundedQueue queue = new ConditionBoundedQueue(2);
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                queue.put("" + i);
            }
        }).start();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                System.out.println(queue.get().toString());
            }).start();
        }
    }
}
