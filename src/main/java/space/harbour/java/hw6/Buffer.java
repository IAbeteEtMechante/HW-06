package space.harbour.java.hw6;


import java.net.URL;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;

//Shared class used by threads

class Buffer {
    ConcurrentLinkedQueue<URL> toVisit = new ConcurrentLinkedQueue<>();
    CopyOnWriteArraySet<URL> alreadyVisited = new CopyOnWriteArraySet<>();

    //Retrieving from the queue

    public URL get() {
        return toVisit.poll();
    }
    // putting in the queue

    public void putQueue(URL myUrl) {
        toVisit.add(myUrl);
    }

    public void putSet(URL myUrl) {
        alreadyVisited.add(myUrl);
    }
}
