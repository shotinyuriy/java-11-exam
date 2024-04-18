package dailyquestion.easy;

import java.time.Duration;
import java.time.Instant;

public class AbstractTest {
    public static void printTime(Instant instant1, Instant instant2, int count) {
        if (count < 1) count = 1;
        var duration = Duration.between(instant1, instant2);
        var durationSingle = duration.dividedBy(count);
        System.out.printf("count=%d duration=%s duration_single=%s\n", count, duration, durationSingle);
    }
}
