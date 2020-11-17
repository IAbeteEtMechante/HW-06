package space.harbour.java.hw6;

import java.net.URL;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WebCrawler {
    public static void main(String[] args) throws Exception {
        Buffer buffer = new Buffer();
        buffer.putQueue(new URL("https://iabeteetmechante.github.io/symmetrical-bassoon/index.html"));

        //for IO intensive tasks we can have a high count of threads
        ExecutorService service = Executors.newFixedThreadPool(100);

        Future<CopyOnWriteArraySet> future;
        CopyOnWriteArraySet<URL> mySet = new CopyOnWriteArraySet<URL>();

        //submit the tasks for execution
        //for (int i = 0; i < 1000; i++) {
        while (!buffer.toVisit.isEmpty()) {
            future = service.submit(new VisitorUrl(buffer));
            mySet = future.get();
        }
        for (URL url : mySet) {
            System.out.println(url.toString());
        }


        service.shutdown();
    }
}







