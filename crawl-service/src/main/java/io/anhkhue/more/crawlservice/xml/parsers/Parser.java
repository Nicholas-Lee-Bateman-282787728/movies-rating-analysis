package io.anhkhue.more.crawlservice.xml.parsers;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import static java.nio.charset.StandardCharsets.UTF_8;

public interface Parser<T> {

    T parseFromSource(Object source);

    default T parseFromHtml(String url, String startPoint, String endPoint) throws IOException {
        InputStream htmlStream = Parser.getHtmlStream(url, startPoint, endPoint);
        return parseFromSource(htmlStream);
    }

    static InputStream getHtmlStream(String link, String startPoint, String endPoint) throws IOException {
        StringBuilder content = new StringBuilder();
        boolean onGoing = false;

        URL url = new URL(link);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/4.76");

        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF_8));

        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
            if (inputLine.contains(startPoint)) {
                onGoing = true;
            }
            if (inputLine.contains(endPoint)) {
                onGoing = false;
            }
            if (onGoing) {
                inputLine = inputLine.replaceAll("&#8220;", "\\\"")
                                     .replaceAll("&#8221;", "\\\"")
                                     .replaceAll("&", "&amp;");
                if (link.contains("https://www.bhdstar.vn/movies/")) {
                    inputLine = inputLine.replaceAll("<span>", "")
                                         .replaceAll("<br />", "");
                }
                content.append(inputLine);
//                For checking output when crawling
//                System.out.println(inputLine);
            }
        }
        // Well-formed
        if (link.equals("http://www.phimmoi.com/") ||
            link.contains("http://www.phimmoi.com/phim-le/page/")) {
            content.append("</ul>");
        }
        bufferedReader.close();
        inputStream.close();

//      Print html file for easier checking
//        BufferedWriter writer = new BufferedWriter(new FileWriter("src/phimmoi.html"));
//        writer.write(String.valueOf(content));
//
//        writer.close();

        return new ByteArrayInputStream(content.toString().getBytes());
    }
}
