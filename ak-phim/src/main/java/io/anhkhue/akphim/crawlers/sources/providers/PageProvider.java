package io.anhkhue.akphim.crawlers.sources.providers;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public interface PageProvider<T> {

    T getPage(String sourceName, int type, String addition) throws IOException, XMLStreamException;
}

