package monnef.playground.benchmark.reflection.test;

import monnef.playground.benchmark.reflection.Worker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodReflectionWithoutLookup extends TimedTest {
    private static final Method method;

    static {
        try {
            method = Worker.class.getMethod("work", int.class);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected int runTestOnce(int input) throws InvocationTargetException, IllegalAccessException {
        method.invoke(worker, input);
        return worker.result;
    }
}
