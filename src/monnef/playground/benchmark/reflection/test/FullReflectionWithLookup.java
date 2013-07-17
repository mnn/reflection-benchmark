package monnef.playground.benchmark.reflection.test;

import monnef.playground.benchmark.reflection.Worker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FullReflectionWithLookup extends TimedTest {
    @Override
    protected int runTestOnce(int input) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        Method method = Worker.class.getMethod("work", int.class);
        Field result = Worker.class.getField("result");
        method.invoke(worker, input);
        return result.getInt(worker);
    }
}
