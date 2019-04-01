package ssd.disruptor;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent> {
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Event: " + event + "  " + Thread.currentThread().getId() + " sequence=" + sequence + " endOfBatch" + endOfBatch);
    }
}
