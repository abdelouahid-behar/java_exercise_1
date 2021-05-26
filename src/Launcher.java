import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Launcher {

    public static int fibo(int n) {
        if (n == 0 || n == 1)
            return n;
        else
            return fibo(n - 1) + fibo(n - 2);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Hello, welcome! (quit to exit the program)");

        Scanner something = new Scanner(System.in);
        String inputThing = something.nextLine();
        while (!"quit".equals(inputThing)) {
            if ("fibo".equals(inputThing)) {
                System.out.println("n = ?");
                int n = something.nextInt();
                something.nextLine();
                System.out.println(fibo(n));
            } else if ("freq".equals(inputThing)) {
                System.out.println("Please give a path of the file");
                String Fpath = something.nextLine();
                Path path = Paths.get(Fpath);
                try {
                    String content = Files.readString(path);
                    String[] words = content.replaceAll("\\W", " ").split("\\s+");
                    Stream<String> wordstream = Arrays.stream(words);
                    Map<String, Long> freq = wordstream
                            .map(String::toLowerCase)
                            .filter(s -> !s.isBlank())
                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                    String top_three = freq.entrySet().stream()
                            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                            .limit(3)
                            .map(Map.Entry::getKey)
                            .collect(Collectors.joining(" "));
                    System.out.println(top_three);
                } catch (IOException e) {
                    System.out.println("Unreadable file: " + e.getClass() + " " + e.getMessage());
                }
            } else
                System.out.println("Unknown command");
            inputThing = something.nextLine();
        }
    }
}