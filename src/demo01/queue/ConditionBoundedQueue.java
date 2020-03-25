package demo01.queue;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionBoundedQueue {
    private LinkedList<Object> buffer;    //生产者容器
    private int maxSize;           //容器最大值是多少
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
     * 生产者
     *
     * @param obj
     * @throws InterruptedException
     */
    public void put(Object obj) {
        lock.lock();    //获取锁
        try {
            while (maxSize == buffer.size()) {
                try {
                    notFullCondition.await();       //满了，添加的线程进入等待状态
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            buffer.add(obj);
            fullCondition.signal(); //通知
        } finally {
            lock.unlock();
        }
    }

    /**
     * 消费者
     *
     * @return
     * @throws InterruptedException
     */
    public Object get() {
        Object obj;
        lock.lock();
        try {
            while (buffer.size() == 0) { //队列中没有数据了 线程进入等待状态
                try {
                    fullCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            obj = buffer.poll();
            notFullCondition.signal(); //通知
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
