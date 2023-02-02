import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Main {

    private static final String LETTERS = "RLRFR";
    private static final int ROUTE_LENGTH = 100;
    private final static int AMOUNT_OF_THREAD = 1000;
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < AMOUNT_OF_THREAD; i++) {
            new Thread(() -> {
                String route = generateRoute(LETTERS, ROUTE_LENGTH);
                int fregency = (int) route.chars().filter(ch -> ch == 'R').count();

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(fregency)) {
                        sizeToFreq.put(fregency, sizeToFreq.get(fregency) + 1);
                    } else {
                        sizeToFreq.put(fregency, 1);
                    }
                }
            }).start();
        }
        Map.Entry<Integer, Integer> max = sizeToFreq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println("Частое количество повторений R: " + max.getKey() + "(встретилось " + max.getValue() + "раз)");
        System.out.println("Другие размеры: ");

        sizeToFreq
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println((" -> " + e.getKey() + "("+(e.getValue() + "раз)"))));
    }

    private static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
