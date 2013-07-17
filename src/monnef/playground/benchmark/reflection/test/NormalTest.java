package monnef.playground.benchmark.reflection.test;

public class NormalTest extends TimedTest {
    @Override
    protected int runTestOnce(int input) {
        worker.work(input);
        return worker.result;
    }
}
