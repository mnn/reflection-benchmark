package monnef.playground.benchmark.reflection.test;

import monnef.playground.benchmark.reflection.Worker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodMHReflection extends TimedTest {
    private static final MethodHandle method;

    static {
        try {
            method = MethodHandles.lookup().findVirtual(Worker.class, "work", MethodType.methodType(void.class, int.class));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected int runTestOnce(int input) throws Throwable {
        method.invokeExact(worker, input);
        return worker.result;
    }
}
