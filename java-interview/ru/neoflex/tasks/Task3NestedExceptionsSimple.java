package ru.neoflex.tasks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Task3NestedExceptionsSimple {
    static final boolean DEBUG = true; // TODO: disable when done

    public static void main(String[] args) throws Exception {
        for (int testCase = 1; testCase <= 4; testCase++) {
            HeavyResource heavyResource = new HeavyResource(); // TODO: fix this ?
            try {
                heavyResource.testCaseForException(testCase);
            } catch (Exception e) {
                Throwable rootCause = findRootCause(e); // TODO: fix this
                System.err.println("=== testCase:" + testCase + " the root cause is [" + rootCause + "]");
                if (DEBUG) {
                    System.err.println("DEBUG: full stack trace -> ");
                    e.printStackTrace();
                }
                System.err.println();
            }
            heavyResource.close();
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println("=== HAPPY END ===");
    }

    static Throwable findRootCause(Throwable source) {
        return source;
    } // TODO: may be helpful to implement

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

    static class HeavyResource implements AutoCloseable {
        static final Map<Long, List<byte[]>> GLOBAL_BIG_DATA = new HashMap<>();
        final long id = System.nanoTime();

        {
            int size = (int) (Runtime.getRuntime().freeMemory() / 512);
            List<byte[]> localBigData = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                localBigData.add(new byte[1024]);
            }
            GLOBAL_BIG_DATA.put(id, localBigData);
        }

        void testCaseForException(int testCase) throws Exception {
            try {
                if (testCase == 1) {
                    arithmetic();
                } else if (testCase == 2) {
                    fileMethod();
                } else if (testCase == 3) {
                    sneakyFileMethod();
                } else if (testCase == 4) {
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