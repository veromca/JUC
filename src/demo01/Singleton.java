package demo01;

public class Singleton {
    // volatile 作用：1 保证线程可见性（MESI,缓存一致性协议），2 禁止指令重排序
    // volatile 不能替代synchronized，它不能保证原子性
    private static volatile Singleton instance;// JIT 这里需要添加volatile，因为多线程下存在指令重排情况，添加volatile后禁止指令重排序（CPU），等对象初始化完成后才赋值

    public Singleton(){

    }

    public static  Singleton getInstance(){
        // 业务逻辑代码省略
        if(instance == null){
            synchronized (Singleton.class){
                // 双重检查
               if(instance == null) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    instance = new Singleton();// 编译时分三步：1申请一块内存（并赋默认值比如int=0），2初始化修改默认值，3引用赋值（最终初始化）
               }
           }
        }
        return instance;
    }

    public static void main(String[] args) {
        //多线程调用
        for(int i=0;i<100;i++){
            new Thread(()->{
                System.out.println(Singleton.getInstance().hashCode());
            }).start();
        }
        for(int i=0;i<100;i++){
            new Thread(()->{
                System.out.println(Singleton.getInstance().hashCode());
            }).start();
        }
    }
}
