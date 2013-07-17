package monnef.playground.benchmark.reflection.test;

import monnef.playground.benchmark.reflection.Worker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class FieldReflectionWithoutLookup extends TimedTest {
    private static final Field result;

    static {
        try {
            result = Worker.class.getField("result");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected int runTestOnce(int input) throws InvocationTargetException, IllegalAccessException {
        worker.work(input);
        return result.getInt(worker);
    }
}
