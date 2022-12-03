import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final int AMOUNT_OF_THREADS = 1000;
    public static final String LETTERS = "RLRFR";
    public static final int ROUTE_LENGTH = 100;
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < AMOUNT_OF_THREADS; i++) {
            new Thread(() -> {
                String route = generateRoute();
                int frequence = (int) route.chars().filter(ch -> ch == 'R').count();

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(frequence)) {
                        sizeToFreq.put(frequence, sizeToFreq.get(frequence) + 1);
                    } else {
                        sizeToFreq.put(frequence, 1);
                    }
                }
            }).start();
        }

        Map.Entry<Integer, Integer> max = sizeToFreq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println("Самое частое количество повторений" + max.getKey() + "(встретилось " + max.getValue() + " раз)");
        System.out.println("Другие размеры: ");

        sizeToFreq.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println("-" + e.getKey() + "(встретилось " + e.getValue() + " раз)"));
    }

    private static String generateRoute() {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < ROUTE_LENGTH; i++) {
            route.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
        }
        return route.toString();
    }
}