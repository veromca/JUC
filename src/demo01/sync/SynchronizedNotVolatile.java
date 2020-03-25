package demo01.sync;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile并不能保证多个线程共同修改count变量时所带来的不一致问题，也就是说volatile不能替代synchronized，因为不能保证原子性
 * 可以在m方法上增加synchronized解决此问题
 */
public class SynchronizedNotVolatile {
    volatile  int count = 0;
    synchronized void m(){
        for (int i=0;i<10000;i++){
            count++;
        }
    }

    public static void main(String[] args) {
        SynchronizedNotVolatile t = new SynchronizedNotVolatile();
        List<Thread> lists = new ArrayList<Thread>();

        for(int i=0;i<10;i++){
            lists.add(new Thread(t::m,"Thread-"+i));
        }
        lists.forEach((a)->a.start());
        lists.forEach((a)->{
            try {
                a.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(t.count);

    }

}

