package ru.neoflex.tasks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Task3NestedExceptionsSimple {
    public static void main(String[] args) throws Exception {
        for (int testIdx = 0; testIdx < 4; testIdx++) {
            HeavyResource heavyResource = new HeavyResource();
            try {
                heavyResource.testCaseByIndex(testIdx);
            } catch (Exception e) {
                Throwable rootCause = e; // TODO: fix this
                System.err.println("=== testIdx:" + testIdx + " the root cause is " + rootCause);
                System.err.println("full stack trace -> ");
                e.printStackTrace();
                System.err.println();
            }
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println("=== exit ===");
    }

    static class CustomRuntimeException extends RuntimeException {
        public CustomRuntimeException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static class CustomException extends Exception {
        private final String customField;

        public CustomException(String customField, String message) {
            super(message);
            this.customField = customField;
        }

        public String getCustomField() {
            return customField;
        }
    }

    static Throwable findRootCause(Throwable source) {
        return source;
    } // TODO: may be helpful

    static class HeavyResource implements AutoCloseable {
        static final Map<Long, List<byte[]>> GLOBAL_BIG_DATA = new HashMap<>();
        final long id = System.nanoTime();

        {
            int size = (int) (Runtime.getRuntime().freeMemory() / 1024);
            List<byte[]> localBigData = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                localBigData.add(new byte[1024]);
            }
            GLOBAL_BIG_DATA.put(id, localBigData);
        }

        void testCaseByIndex(int idx) throws Exception {
            try {
                int id = idx % 4;
                if (id == 0) {
                    arithmetic();
                } else if (id == 1) {
                    fileMethod();
                } else if (id == 2) {
                    sneakyFileMethod();
                } else if (id == 3) {
                    sneakyCustomMethod();
                }
            } catch (RuntimeException e) {
                throw new RuntimeException("wrapped runtime exception", e);
            }
        }

        private void arithmetic() {
            int z = 1 / 0;
        }

        private void fileMethod() throws IOException {
            Files.readAllLines(Paths.get("/a/b/c.txt"));
        }

        private void sneakyFileMethod() throws IOException {
            try {
                fileMethod();
            } catch (IOException e) {
                throw new RuntimeException("wrapped IO ex", e);
            }
        }

        private void unsafeCustom() throws CustomException {
            throw new CustomException("id=" + 3, "custom ex");
        }

        private void sneakyCustomMethod() {
            try {
                unsafeCustom();
            } catch (CustomException e) {
                throw new CustomRuntimeException("custom ex wrapped", e);
            }
        }

        @Override
        public void close() throws Exception {
            GLOBAL_BIG_DATA.remove(id);
        }
    }
}