package ru.neoflex.task1;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Task2TopGamer {
    static class GamerScore {
        long gamerId;
        double score;
        long timestamp;
        private Random rnd = new Random();

        public GamerScore(int gamerId) {
            this.gamerId = gamerId;
            this.score = rnd.nextInt(1, 1000) / 10.0;
            this.timestamp = System.currentTimeMillis() % 60_000;
        }

        @Override
        public String toString() {
            return "GamerScore{" +
                    "gamerId=" + gamerId +
                    ", score=" + score +
                    ", timestamp=" + timestamp +
                    '}';
        }
    }

    static Timer timer = new Timer(true);
    static ScheduledExecutorService gamerScoreEmitter = Executors.newScheduledThreadPool(10);
    static ScheduledExecutorService topGamerPrinter = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            final int gamerId = i;
            gamerScoreEmitter.scheduleAtFixedRate(
                    () -> listenGamerScore(new GamerScore(gamerId)), System.nanoTime() % 9 + 1, System.nanoTime() % 7 + 1, TimeUnit.SECONDS);
        }
        topGamerPrinter.scheduleAtFixedRate(
                () -> printTop3Gamers(), 5, 5, TimeUnit.SECONDS);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gamerScoreEmitter.shutdownNow();
                topGamerPrinter.shutdownNow();
            }
        }, 20_000);
    }

    static Map<Long, GamerScore> currentGamerScore = new HashMap<>();

    static void listenGamerScore(GamerScore gamerScore) {
//        System.out.println(gamerScore);
        currentGamerScore.put(gamerScore.gamerId, gamerScore);
    }

    static void printTop3Gamers() {
        System.out.println(LocalTime.now() + " Top 3 Gamers ");
        currentGamerScore.forEach((gamerId, gamerScore) -> System.out.println(gamerId + "=" + gamerScore.score));
    }
}
