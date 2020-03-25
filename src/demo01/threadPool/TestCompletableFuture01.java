package demo01.threadPool;

import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 可以组合很多步骤，最终获取各个步骤的结果；场景：购物网站价格比较，线程1 京东价格，线程2 淘宝价格，线程3 天猫价格，最后汇总结果。
 */
public class TestCompletableFuture01 {


    public static void main(String[] args) {
        /*CompletableFuture cfTM = CompletableFuture.supplyAsync(()->getTMPrice());
        CompletableFuture cfTB = CompletableFuture.supplyAsync(()->getTMPrice());
        CompletableFuture cfJD = CompletableFuture.supplyAsync(()->getTMPrice());
        System.out.println("天猫价格获取状态："+cfTM.isDone()+"淘宝价格获取状态："+cfTM.isDone()+"京东价格获取状态："+cfTM.isDone());*/
        long startTime = System.currentTimeMillis();
        CompletableFuture[] arrays = new CompletableFuture[3];
        for (int i = 0; i < arrays.length; i++) {
            int finalI = i;
            arrays[i] = CompletableFuture.supplyAsync(()->getPrice(finalI));
        }
        try {
            CompletableFuture.allOf(arrays).get();
            long endTime = System.currentTimeMillis();
            System.out.println("采集完成耗时："+ (endTime-startTime)/1000 +"秒");
            //combindFuture.get();
            Map<String,Double> tm = (Map)arrays[0].get();
            Map<String,Double> tb = (Map)arrays[1].get();
            Map<String,Double> jd = (Map)arrays[2].get();
            System.out.println("天猫价格获取状态："+tm.get("线程：0")+"淘宝价格获取状态："+tb.get("线程：1")+"京东价格获取状态："+jd.get("线程：2"));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static double getTMPrice(){
        sleep();
        return 10.0;
    }
    private static double getTBPrice(){
        sleep();
        return 11.0;
    }
    private static double getJDPrice(){
        sleep();
        return 12.0;
    }
    private static void sleep(){
        int time = new Random().nextInt(5000);
        try {
            TimeUnit.MICROSECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after sleep:"+time+"!");
    }

    private static Map<String,Double> getPrice(int i){
        int time = new Random().nextInt(5);
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after sleep:"+time+"!");
        Map<String,Double> result = new ConcurrentHashMap<String,Double>();
        result.put("线程："+i, new Random().nextDouble()*100);
        return result;
    }

}
