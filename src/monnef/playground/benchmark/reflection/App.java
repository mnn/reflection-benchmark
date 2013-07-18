package monnef.playground.benchmark.reflection;

import monnef.playground.benchmark.reflection.test.BothMHReflection;
import monnef.playground.benchmark.reflection.test.BothReflectASM;
import monnef.playground.benchmark.reflection.test.FieldMHReflection;
import monnef.playground.benchmark.reflection.test.FieldReflectionWithoutLookup;
import monnef.playground.benchmark.reflection.test.FullReflectionWithLookup;
import monnef.playground.benchmark.reflection.test.FullReflectionWithoutLookup;
import monnef.playground.benchmark.reflection.test.MethodMHReflection;
import monnef.playground.benchmark.reflection.test.MethodReflectionWithoutLookup;
import monnef.playground.benchmark.reflection.test.NormalTest;
import monnef.playground.benchmark.reflection.test.TimedTest;

import java.util.HashMap;
import java.util.LinkedList;

public class App {
    private static final int MILLIS_LOW = (int) (0.5 * 1000);
    private static final int MILLIS_MED = 3 * 1000;
    private static final int MILLIS_HIGH = 10 * 1000;
    private static final int MILLIS_ULTRA_HIGH = 60 * 1000;

    private static final String FAST = "Fast";
    private static final String MEDIUM = "Medium";
    private static final String LONG = "Long";
    private static final String ULTRA_LONG = "Ultra Long";

    private static LinkedList<Class<? extends TimedTest>> tests;
    private static HashMap<String, Tuple<Integer, String>> timeSets;
    private static LinkedList<Tuple<Integer, String>> toRun;

    public static void main(String[] args) {
        initTests();
        initTimeSets();
        constructToRunList(args);
        runTests();
    }

    private static void runTests() {
        while (!toRun.isEmpty()) {
            Tuple<Integer, String> item = toRun.remove();
            runWholeSet(tests, item.first, item.second);
        }
    }

    private static void constructToRunList(String[] args) {
        toRun = new LinkedList<>();
        for (String arg : args) {
            Tuple<Integer, String> item = timeSets.get(arg.toLowerCase());
            if (item != null) {
                toRun.add(item);
            } else {
                for (Character c : arg.toCharArray()) {
                    item = timeSets.get(c.toString());
                    if (item != null) {
                        toRun.add(item);
                    } else {
                        throw new RuntimeException("unknown parameter: " + arg + " (" + c + ")");
                    }
                }
            }
        }
        if (toRun.size() == 0) {
            toRun.add(timeSets.get(FAST.toLowerCase()));
            toRun.add(timeSets.get(MEDIUM.toLowerCase()));
        }
    }

    private static void initTimeSets() {
        timeSets = new HashMap<>();
        addSet(timeSets, MILLIS_LOW, FAST);
        addSet(timeSets, MILLIS_MED, MEDIUM);
        addSet(timeSets, MILLIS_HIGH, LONG);
        addSet(timeSets, MILLIS_ULTRA_HIGH, ULTRA_LONG);
    }

    private static void initTests() {
        tests = new LinkedList<>();
        tests.add(NormalTest.class);
        tests.add(MethodReflectionWithoutLookup.class);
        tests.add(FieldReflectionWithoutLookup.class);
        tests.add(FullReflectionWithoutLookup.class);
        tests.add(FullReflectionWithLookup.class);
        tests.add(MethodMHReflection.class);
        tests.add(FieldMHReflection.class);
        tests.add(BothMHReflection.class);
        tests.add(BothReflectASM.class);
    }

    private static void addSet(HashMap<String, Tuple<Integer, String>> sets, int limit, String title) {
        Tuple<Integer, String> value = new Tuple<>(limit, title);
        sets.put(title.substring(0, 1).toLowerCase(), value);
        sets.put(title.toLowerCase(), value);
    }

    private static void runWholeSet(LinkedList<Class<? extends TimedTest>> tests, int limit, String title) {
        println("Running set: " + title);
        println("-----------------------");
        for (Class<? extends TimedTest> test : tests) {
            test(test, limit);
        }
        println("");
    }

    private static void test(Class<? extends TimedTest> testClass, int limit) {
        int iterations;
        TimedTest obj;
        long passed;
        try {
            obj = testClass.newInstance();
            passed = System.currentTimeMillis();
            iterations = obj.run(limit);
            passed = System.currentTimeMillis() - passed;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        println(String.format("%10d (%.3f s) : %32s . . . %.5f ns", iterations, passed / 1000f, obj.getClass().getSimpleName(), ((float) passed * 1000) / iterations));
    }

    private static void println(String msg) {
        System.out.println(msg);
    }


}

