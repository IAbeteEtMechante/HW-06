package space.harbour.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
    static ConcurrentLinkedQueue<URL> toVisit = new ConcurrentLinkedQueue<>();
    static CopyOnWriteArraySet<URL> alreadyVisited = new CopyOnWriteArraySet<>();

    public static class UrlVisitor implements Runnable {
        public static String getContentOfWebPage(URL url) {
            final StringBuilder content = new StringBuilder();

            try (InputStream is = url.openConnection().getInputStream();
                    InputStreamReader in = new InputStreamReader(is, "UTF-8");
                    BufferedReader br = new BufferedReader(in); ) {
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
        public void run() {
            // get a URL from the head of toVisit queue
            URL url = toVisit.poll();
            // mark it as visited by adding to alreadyVisited set
            alreadyVisited.add(url);

            // get content of the web page
            String content = getContentOfWebPage(url);
            // Remove this line after debugging before you submit your HW
            System.out.println(content);

            //get URLs from the content
            String regex =
                    "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(content);
            while (m.find()) {
                System.out.println(m.group());
                try {
                    URL myUrl = new URL(m.group());
                    if (!alreadyVisited.contains(myUrl) && !toVisit.contains(myUrl)) {
                        toVisit.add(myUrl);
                    }

                } catch (MalformedURLException e) {
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

    public static void main(String[] args) throws MalformedURLException {
        toVisit.add(new URL("https://vasart.github.io/supreme-potato/"));
        new Thread(new UrlVisitor()).start();
    }

}
