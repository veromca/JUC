package demo01.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestSingleThreadPool {
    public static void main(String[] args) {
        ExecutorService es = Executors.newSingleThreadExecutor();
       // ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            es.execute(()->{
                System.out.println(finalI +" " + Thread.currentThread().getName());
            });
        }
        System.out.println("1 "+Thread.currentThread().getName());


        System.out.println("2 "+Thread.currentThread().getName());
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            es.execute(()->{
                System.out.println(finalI +" - " + Thread.currentThread().getName());
            });
        }
    }
}
