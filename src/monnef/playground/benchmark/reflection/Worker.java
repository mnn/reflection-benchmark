package monnef.playground.benchmark.reflection;

public class Worker {
    public static final int TASK_LEN = 1;
    public int result;

    public void work(int input) {
        result = input;
        for (int i = 0; i < TASK_LEN; i++) {
            result += i;
        }
    }
}
