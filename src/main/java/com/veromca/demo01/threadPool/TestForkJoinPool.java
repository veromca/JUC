package com.veromca.demo01.threadPool;

import javax.xml.ws.soap.Addressing;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 并行计算，将一个大的计算拆分成多个小的任务，最终汇总结果
 */
public class TestForkJoinPool {
    static int[] nums = new int[1000000];
    static final int MAX_NUM = 50000;
    static Random r = new Random();

    public TestForkJoinPool() {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }
    }

    static class AddTaskRet extends RecursiveTask<Long> {
        int start, end;

        public AddTaskRet(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if(end-start<=MAX_NUM){
                Long sum = 0L;
                for(int i=start;i<end;i++) sum += nums[i];
                return sum;
            }

            int middle = start + (end-start)/2;
            AddTaskRet t1 = new AddTaskRet(start,middle);
            AddTaskRet t2 = new AddTaskRet(middle,end);
            t1.fork();
            t2.fork();
            return t1.join()+t2.join();
        }
    }


    public static void main(String[] args) {

        TestForkJoinPool temp = new TestForkJoinPool();
        long starttime = System.currentTimeMillis();
        System.out.println("1----------" + Arrays.stream(nums).sum());
        System.out.println("1----------time" +(System.currentTimeMillis()-starttime));

        long taskStartTime = System.currentTimeMillis();
        ForkJoinPool fjp = new ForkJoinPool();
        AddTaskRet tast = new AddTaskRet(0,nums.length);
        fjp.execute(tast);
        System.out.println("2----------" +tast.join());
        System.out.println("2----------time" +(System.currentTimeMillis()-taskStartTime));
    }


}
