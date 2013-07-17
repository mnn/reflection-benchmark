package monnef.playground.benchmark.reflection.test;

import monnef.playground.benchmark.reflection.Worker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FullReflectionWithoutLookup extends TimedTest {
    private static final Method method;
    private static final Field result;

    static {
        try {
            method = Worker.class.getMethod("work", int.class);
            result = Worker.class.getField("result");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected int runTestOnce(int input) throws InvocationTargetException, IllegalAccessException {
        method.invoke(worker, input);
        return result.getInt(worker);
    }
}
