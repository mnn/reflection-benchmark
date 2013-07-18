package monnef.playground.benchmark.reflection.test;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import monnef.playground.benchmark.reflection.Worker;

public class BothReflectASM extends TimedTest {

    private static final MethodAccess methodAccess;
    private static final int methodIndex;
    private static final FieldAccess fieldAccess;
    private static final int fieldIndex;

    static {
        methodAccess = MethodAccess.get(Worker.class);
        methodIndex = methodAccess.getIndex("work");
        fieldAccess = FieldAccess.get(Worker.class);
        fieldIndex = fieldAccess.getIndex("result");
    }

    @Override
    protected int runTestOnce(int input) {
        methodAccess.invoke(worker, methodIndex, input);
        return fieldAccess.getInt(worker, fieldIndex);
    }
}
