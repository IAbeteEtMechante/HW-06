package space.harbour.java.hw6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class WebCrawlerTest {


    //test01: cehck that we can get the last link on the webpage
    @Test
    public void canGetLastPage() throws Exception {
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

        service.shutdown();
        Assert.assertTrue(mySet.toString().contains("https://iabeteetmechante.github.io/symmetrical-bassoon/10.html"));

    }

    //test 02: check that we have emptied the toVisit queue at the end
    @Test
    public void func() throws Exception {
        Buffer buffer = new Buffer();
        buffer.putQueue(new URL("https://iabeteetmechante.github.io/symmetrical-bassoon/index.html"));

        //for IO intensive tasks we can have a high count of threads
        ExecutorService service = Executors.newFixedThreadPool(100);

        Future<CopyOnWriteArraySet> future;

        //submit the tasks for execution
        //for (int i = 0; i < 1000; i++) {
        while (!buffer.toVisit.isEmpty()) {
            future = service.submit(new VisitorUrl(buffer));
            System.out.println(future.get());
        }

        service.shutdown();
        assertEquals(buffer.toVisit.size(), 0);

    }

    //test01 : test that we detect incorrect urls
    //@Test (expected = MalformedURLException.class) {
    //    public void incorrectUrl() {
    //        WebCrawler.
    //    }
    //}

    //test01: cehck that we can get the correct number of urls
    @Test
    public void getCorrectNumberOfLinks() throws Exception {
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

        service.shutdown();
        Assert.assertEquals(mySet.size(), 12);

    }

}