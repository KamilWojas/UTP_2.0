/**
 *
 *  @author Wojas Kamil S23878
 *
 */

package zad3;


public class Writer implements Runnable {
    private final Author author;

    public Writer(Author author) {
        this.author = author;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String[] texts = author.getTexts();
                if (texts.length > 0) {
                    System.out.println("Writer: " + texts[0]);
                }
                synchronized (author) {
                    author.wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
