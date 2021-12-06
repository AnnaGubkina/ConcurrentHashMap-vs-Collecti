import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReaderThread implements Runnable {

    private Map<Integer, Integer> map;
    private String name;
    private String nameMap;


    public ReaderThread(Map<Integer, Integer> map, String threadName, String nameMap) {
        this.map = map;
        this.name = threadName;
        this.nameMap = nameMap;

    }

    @Override
    public void run() {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < map.size(); i++) {
            Integer value = map.get(i);
            // System.out.println(name + ": значение из мапы = " + value);
        }
        long timeFinish = System.currentTimeMillis();
        System.out.println("Время чтения мапы " + nameMap + " составляет " +
                (timeFinish - timeStart) + "\n");
    }
}
