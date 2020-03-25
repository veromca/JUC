package demo01.threadPool;

import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * ������Ϻܶಽ�裬���ջ�ȡ��������Ľ����������������վ�۸�Ƚϣ��߳�1 �����۸��߳�2 �Ա��۸��߳�3 ��è�۸������ܽ����
 */
public class TestCompletableFuture01 {


    public static void main(String[] args) {
        /*CompletableFuture cfTM = CompletableFuture.supplyAsync(()->getTMPrice());
        CompletableFuture cfTB = CompletableFuture.supplyAsync(()->getTMPrice());
        CompletableFuture cfJD = CompletableFuture.supplyAsync(()->getTMPrice());
        System.out.println("��è�۸��ȡ״̬��"+cfTM.isDone()+"�Ա��۸��ȡ״̬��"+cfTM.isDone()+"�����۸��ȡ״̬��"+cfTM.isDone());*/
        long startTime = System.currentTimeMillis();
        CompletableFuture[] arrays = new CompletableFuture[3];
        for (int i = 0; i < arrays.length; i++) {
            int finalI = i;
            arrays[i] = CompletableFuture.supplyAsync(()->getPrice(finalI));
        }
        try {
            CompletableFuture.allOf(arrays).get();
            long endTime = System.currentTimeMillis();
            System.out.println("�ɼ���ɺ�ʱ��"+ (endTime-startTime)/1000 +"��");
            //combindFuture.get();
            Map<String,Double> tm = (Map)arrays[0].get();
            Map<String,Double> tb = (Map)arrays[1].get();
            Map<String,Double> jd = (Map)arrays[2].get();
            System.out.println("��è�۸��ȡ״̬��"+tm.get("�̣߳�0")+"�Ա��۸��ȡ״̬��"+tb.get("�̣߳�1")+"�����۸��ȡ״̬��"+jd.get("�̣߳�2"));

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
        result.put("�̣߳�"+i, new Random().nextDouble()*100);
        return result;
    }

}
