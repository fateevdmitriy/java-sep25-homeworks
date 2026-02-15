package ru.otus.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlipFlap {
    private static final Logger logger = LoggerFactory.getLogger(FlipFlap.class);
    private static final int MIN = 1;
    private static final int MAX = 10;
    private int counter = MIN;
    private int lastIndex = 2;
    private boolean isDirectOrder = true;

    private synchronized void action(int index) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // spurious wakeup https://en.wikipedia.org/wiki/Spurious_wakeup
                // поэтому не if
                while (lastIndex == index) {
                    this.wait();
                }

                logger.info("[{}]: {} ", index, counter);
                if (index > lastIndex) {
                    if (isDirectOrder) {
                        counter++;
                    } else {
                        counter--;
                    }
                    if (counter == MIN || counter == MAX) {
                        isDirectOrder = !isDirectOrder;
                    }
                }
                lastIndex = index;
                sleep();
                notifyAll();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        FlipFlap flipFlap = new FlipFlap();
        new Thread(() -> flipFlap.action(1)).start();
        new Thread(() -> flipFlap.action(2)).start();
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
