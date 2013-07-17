package monnef.playground.benchmark.reflection.test;

import monnef.playground.benchmark.reflection.Worker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public class FieldMHReflection extends TimedTest {
    private static final MethodHandle field;

    static {
        try {
            field = MethodHandles.lookup().findGetter(Worker.class, "result", int.class);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected int runTestOnce(int input) throws Throwable {
        worker.work(input);
        return (int) field.invokeExact(worker);
    }
}
