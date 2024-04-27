package ru.neoflex.tasks;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Task2TopGamer {
    static final Random rnd = new Random();

    static class GamerScore {
        long gamerId;
        double score;
        LocalTime timestamp;

        public GamerScore(int gamerId, double score, LocalTime localTime) {
            this.gamerId = gamerId;
            this.score = score;
            this.timestamp = localTime;
        }

        @Override
        public String toString() {
            return "GamerScore{" + "gamerId=" + gamerId + ", score=" + score + ", timestamp=" + timestamp + '}';
        }
    }

    static final Timer timer = new Timer(true);
    static final ScheduledExecutorService gamerScoreEmitter = Executors.newScheduledThreadPool(10);
    static final ScheduledExecutorService topGamerPrinter = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            final int gamerId = i % 5 + 1;
            gamerScoreEmitter.scheduleAtFixedRate(
                    () -> listenGamerScore(
                            new GamerScore(gamerId, 1 + rnd.nextInt(1000) / 10.0,
                                    LocalTime.now().plusSeconds(-3 + rnd.nextInt(4)))),
                    1 + rnd.nextInt(10), 1 + rnd.nextInt(10), TimeUnit.MILLISECONDS);
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
        System.out.println("=== " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) + " Top 3 Gamers ===");
        currentGamerScore.forEach((gamerId, gamerScore) ->
                System.out.println(
                        "gamerId=" + gamerId + " scored " + gamerScore.score + " at " + gamerScore.timestamp.truncatedTo(ChronoUnit.SECONDS)));
    }
}
