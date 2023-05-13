/**
 *
 *  @author Wojas Kamil S23878
 *
 */

package zad3;


import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Author implements Runnable {
    private final BlockingQueue<String> queue;

    public Author(String[] texts) {
        this.queue = new LinkedBlockingQueue<>(Arrays.asList(texts));
    }

    public synchronized String[] getTexts() {
        return queue.toArray(new String[0]);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String text = queue.take();
                synchronized (queue) {
                    queue.notifyAll();
                }
                System.out.println("Author: " + text);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

