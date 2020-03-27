package demo01.singleton;

/**
 * 懒汉模式单例
 * 调用时实例化
 * 缺点：不适合多线程场景
 */
public class TestSingleton03 {
    private static TestSingleton03 instance;

    public TestSingleton03() {

    }

    public static TestSingleton03 getInstance() {
        if (null == instance) {
            instance = new TestSingleton03();
        }
        return instance;
    }

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++)
                System.out.println(getInstance().hashCode());
        }).start();

    }

}
