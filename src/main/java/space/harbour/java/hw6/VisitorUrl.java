package space.harbour.java.hw6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class VisitorUrl implements Callable {
    Buffer buffer;

    VisitorUrl(Buffer buffer) {
        this.buffer = buffer;
    }

    public static String getContentOfWebPage(URL url) {
        final StringBuilder content = new StringBuilder();

        try (InputStream is = url.openConnection().getInputStream();
                InputStreamReader in = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(in);) {
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                content.append(inputLine);
            }
        } catch (IOException e) {
            System.out.println("Failed to retrieve content of " + url.toString());
            e.printStackTrace();
        }

        return content.toString();
    }

    @Override
    public CopyOnWriteArraySet call() throws Exception {
        synchronized (buffer.toVisit) {
            synchronized (buffer.alreadyVisited) {

                // get a URL from the head of toVisit queue
                AtomicReference<URL> url = new AtomicReference<>(buffer.get());
                /*in some cases, one thread will be trying to get an url while the queue is empty
                (while other threads are parsing new links)
                we want to stop threads working on the empty queue right away
                 */
                if (url == null) {
                    return buffer.alreadyVisited;
                }
                // mark it as visited by adding to alreadyVisited set
                buffer.putSet(url.get());

                // get content of the web page
                AtomicReference<String> content =
                        new AtomicReference<>(getContentOfWebPage(url.get()));
                // Remove this line after debugging before you submit your HW
                //System.out.println(content);

                //get URLs from the content
                String regex =
                        "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]"
                                + "*[-a-zA-Z0-9+&@#/%=~_|]";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(content.get());
                while (m.find()) {
                    AtomicReference<String> myString =
                            new AtomicReference<>(m.group());
                    try {
                        AtomicReference<URL> myUrl
                                = new AtomicReference<>(new URL(myString.get()));
                        if (!buffer.alreadyVisited.contains(myUrl.get())
                                && !buffer.toVisit.contains(myUrl.get())) {
                            buffer.toVisit.add(myUrl.get());
                        }

                    } catch (MalformedURLException e) {
                        System.out.println("Exception , malformed URL!");
                        e.printStackTrace();
                    }

                }

                // get URLs from the contect
                //URL[] urls = {};
                //for (URL newUrl : urls) {
                //    if (!alreadyVisited.contains(newUrl) && !toVisit.contains(newUrl)) {
                //        toVisit.add(newUrl);
                //    }
                //}
            }
        }
        return buffer.alreadyVisited;
    }
}


