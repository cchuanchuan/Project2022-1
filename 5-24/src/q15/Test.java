package q15;

public class Test {
    public static void main(String[] args) {
        // your implementation goes here
        Counter counter = new Counter(10,10000);
        new Thread(()->{
            counter.countRange();
        }).start();
        new Thread(()->{
            counter.countRange();
        }).start();
    }
}

class Counter {
    private final int min;
    private final int max;

    Counter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    synchronized public void countRange() {
        for (int i = this.min; i <= this.max; i++) {
            System.out.println(i);
        }
    }
}