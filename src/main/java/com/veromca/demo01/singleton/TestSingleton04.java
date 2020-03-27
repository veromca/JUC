package demo01.singleton;

/**
 * 枚举单例
 */
public class TestSingleton04 {
    private TestSingleton04(){

    }
    static enum SingletonEnum{
        INSTANCE;
        private TestSingleton04 instance;
        private SingletonEnum(){
            instance = new TestSingleton04();
        }
        public TestSingleton04 getInstance(){
            return instance;
        }

    }
    public static TestSingleton04 getInstance(){
       return SingletonEnum.INSTANCE.getInstance();
    }
    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++)
                System.out.println(TestSingleton04.getInstance().hashCode());
        }).start();

    }

}
