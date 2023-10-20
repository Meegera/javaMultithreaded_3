package org.task1;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counterLength3 = new AtomicInteger(0);
    public static AtomicInteger counterLength4 = new AtomicInteger(0);
    public static AtomicInteger counterLength5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        //Stream.of(texts).forEach(System.out::println);

        Thread threadCriteria1 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (isPalindrome(texts[i])) {
                    if(texts[i].length() == 3) {
                        counterLength3.getAndIncrement();
                    } else if (texts[i].length() == 4) {
                        counterLength4.getAndIncrement();
                    } else {
                        counterLength5.getAndIncrement();
                    }
                }
            }
        });

        Thread threadCriteria2 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (isOneLetter(texts[i])) {
                    if(texts[i].length() == 3) {
                        counterLength3.getAndIncrement();
                    } else if (texts[i].length() == 4) {
                        counterLength4.getAndIncrement();
                    } else {
                        counterLength5.getAndIncrement();
                    }
                }
            }
        });

        Thread threadCriteria3 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (isAscendingRow(texts[i])) {
                    if(texts[i].length() == 3) {
                        counterLength3.getAndIncrement();
                    } else if (texts[i].length() == 4) {
                        counterLength4.getAndIncrement();
                    } else {
                        counterLength5.getAndIncrement();
                    }
                }
            }
        });


        Thread[] threads = new Thread[]{threadCriteria1, threadCriteria2, threadCriteria3};

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Красивых слов с длиной 3: " + counterLength3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + counterLength4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + counterLength5 + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        int left = 0;
        int right = text.length() - 1;

        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    public static boolean isOneLetter(String text) {
        char origin = text.charAt(0);

        for (char letter : text.toCharArray()) {
            if (origin != letter) {
                return false;
            }
        }

        return true;
    }

    public static boolean isAscendingRow(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (!(text.charAt(i) <= text.charAt(i + 1))) {
                return false;
            }
        }

        return true;
    }
}