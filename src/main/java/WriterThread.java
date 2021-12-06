import java.util.Map;


public class WriterThread implements Runnable {

    private Map<Integer, Integer> map;
    private String name;
    private int[] array;
    private String nameMap;

    public WriterThread(Map<Integer, Integer> map, String threadName, int[] array, String nameMap) {
        this.map = map;
        this.name = threadName;
        this.array = array;
        this.nameMap = nameMap;
    }

    @Override
    public void run() {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            Integer key = i;
            Integer value = array[i];
            map.put(key, value);
            //  System.out.println(name + "записал в мапу ключ " + key + " cо значением " + value);
        }
        long timeFinish = System.currentTimeMillis();
        System.out.println("Время записи мапы " + nameMap + " составляет " +
                (timeFinish - timeStart) + "\n");
    }
}
