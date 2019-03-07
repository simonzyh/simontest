package disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SampleTest {

    static EventFactory<LongEvent> eventFactory = new LongEventFactory();
    static ExecutorService executor = Executors.newFixedThreadPool(10);
    static int ringBufferSize = 1024 * 1024; // RingBuffer 大小，必须是 2 的 N 次方；

    static Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(eventFactory, ringBufferSize, executor);


    public static void main(String[] args) {
        EventHandler<LongEvent> eventHandler = new LongEventHandler();
        disruptor.handleEventsWith(eventHandler);
        disruptor.handleEventsWith(eventHandler);

        disruptor.start();
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    send(123L);
                }
            }.start();
        }
    }

    private static void send(long data) {
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        long sequence = ringBuffer.next();//请求下一个事件序号；

        try {
            LongEvent event = ringBuffer.get(sequence);//获取该序号对应的事件对象；
            event.set(data);
        } finally {
            ringBuffer.publish(sequence);//发布事件；
        }
    }

}
