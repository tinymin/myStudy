package from.wiki.observer.sample1;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

class EventSource extends Observable implements Runnable {
    @Override
    public void run() {
        try {
            final Random random = new Random();
//            final InputStreamReader isr = new InputStreamReader(System.in);
//            final BufferedReader br = new BufferedReader(isr);
            while (true) {
//                String response = br.readLine();
                setChanged();
                notifyObservers(Long.toString(random.nextInt(1000)));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ResponseHandler implements Observer {
    private final int id;
    private String response;

    public ResponseHandler(int id) {
        this.id = id;
    }

    @Override
    public void update(Observable obj, Object arg) {
        if (arg instanceof String) {
            response = (String) arg;
//            System.out.print("this.hashCode :" + this.hashCode());
            System.out.print("this.id = " + id);
            System.out.println(", Received Response: " + response );
        }
    }
}

public class ObserverMain {
    public static void main(String[] args) {
        System.out.println("Enter Text >");

        EventSource eventSource = new EventSource();
        ResponseHandler responseHandlerA = new ResponseHandler(1);
        ResponseHandler responseHandlerB = new ResponseHandler(2);
        eventSource.addObserver(responseHandlerA);
        eventSource.addObserver(responseHandlerB);

        Thread thread = new Thread(eventSource);
        thread.start();
    }
}