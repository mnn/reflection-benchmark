package monnef.playground.benchmark.reflection.test;

import monnef.playground.benchmark.reflection.Worker;

public abstract class TimedTest {
    protected static final Worker worker = new Worker();

    protected abstract int runTestOnce(int input) throws Throwable;

    public int run(int millisLimit) throws Throwable {
        long start = System.currentTimeMillis();
        int i = 0;
        do {
            runTestOnce(i++);
        } while (System.currentTimeMillis() - start < millisLimit);
        return i;
    }
}

