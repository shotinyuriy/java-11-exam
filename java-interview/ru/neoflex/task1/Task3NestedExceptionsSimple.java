package ru.neoflex.task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Task3NestedExceptionsSimple {
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

        void importantMethod(int idx) throws Exception {
            try {
                int id = idx % 4;
                if (id == 0) {
                    int z = 1 / 0;
                } else if (id == 1) {
                    Files.readAllLines(Paths.get("/a/b/c.txt"));
                } else if (id == 2) {
                    try {
                        Files.readAllLines(Paths.get("/a/b/c.txt"));
                    } catch (IOException e) {
                        throw new RuntimeException("wrap IO exception", e);
                    }
                } else if (id == 3) {
                    throw new CustomException("id=" + 3, "custom");
                }
            } catch (CustomException e) {
                throw new CustomRuntimeException("wrapped", e);
            } catch (RuntimeException e) {
                throw new RuntimeException("wrap exception", e);
            }
        }

        @Override
        public void close() throws Exception {
            GLOBAL_BIG_DATA.remove(id);
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 4; i++) {
            HeavyResource heavyResource = new HeavyResource();
            try {
                heavyResource.importantMethod(i);
            } catch (Exception e) {
                Throwable rootCause = e;
                System.err.println("=== " + i + " Caused by root === " + rootCause);
                System.err.println("full stack trace -> ");
                e.printStackTrace();
                System.err.println();
            }
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println("=== exit ===");
    }
}