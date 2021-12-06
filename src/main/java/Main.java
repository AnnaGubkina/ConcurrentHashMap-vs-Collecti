import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static int ARRAY_SIZE = 1000;
    public static int ARRAY_SIZE_BIG = 1000000;

    public static void main(String[] args) {

        //вариант с маленьким массивом на 1000 элементов
        int[] array = new int[ARRAY_SIZE];
        generationArray(array);

        //Производительность  ConcurrentHashMap
        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        startThread(map, array, "ConcurrentHashMap");


        //Производительность  Collections.synchronizedMap
        Map<Integer, Integer> mapSync = Collections.synchronizedMap(new HashMap<Integer, Integer>());
        startThread(mapSync, array, "Collections.synchronizedMap");

        //вариант с большим массивом на 1000000 элементов"
        int[] arrayBig = new int[ARRAY_SIZE_BIG];
        generationArray(arrayBig);

        //Производительность  ConcurrentHashMap
        Map<Integer, Integer> mapBig = new ConcurrentHashMap<>();
        startThread(mapBig, arrayBig, "ConcurrentHashMap");

        //Производительность  Collections.synchronizedMap
        Map<Integer, Integer> mapSyncBig = Collections.synchronizedMap(new HashMap<Integer, Integer>());
        startThread(mapSyncBig, arrayBig, "Collections.synchronizedMap");


    }

    public static void startThread(Map<Integer, Integer> map, int[] array, String nameMap) {
        //поток писатель

        Thread writer = new Thread(new WriterThread(map, "Поток писатель ", array, nameMap));
        writer.start();

        //ждем когда писатель заполнит мапу, чтобы читатель мог ее прочитать
        try {
            writer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //поток читатель
        Thread reader = new Thread(new ReaderThread(map, "Поток читатель ", nameMap));
        reader.start();
    }


    public static void generationArray(int[] array) {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(50);
        }
    }
}

/*
Из результатов работы можно сделать вывод, что:

Время записи в мапу ConcurrentHashMap при любом обьеме массива в этой задаче, больше чем время записи
в мапу Collections.synchronizedMap. Это происходит потому, что  запись в сегмент ConcurrentHashMap происходит с блокировкой.
Чтение в  ConcurrentHashMap при малом массиве происходит гораздо быстрее , чем в Collections.synchronizedMap,
так как в ConcurrentHashMap оно неблокирующее, отсюда такая скорость.
На большом массиве , так как у нас в чтении участвует только 1 поток, время чтения у обоих классов примерно одинаковое.
Но если мы сделаем несколько потоков чтения, то скорость чтения у ConcurrentHashMap на большом массиве будет больше,
потому что у Collections.synchronizedMap эти потоки будут блокироваться, а у  ConcurrentHashMap  нет.
 */
