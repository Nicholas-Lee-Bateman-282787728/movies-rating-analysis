package io.anhkhue.more.crawlers.sources.providers;

import io.anhkhue.more.crawlers.sources.Checkpoints;
import io.anhkhue.more.crawlers.sources.readers.SourceReader;
import io.anhkhue.more.crawlers.xml.parsers.Parser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

@Slf4j
@Component
public class PageDomProvider implements PageProvider<Document> {

    private final SourceReader sourceReader;

    private final Parser<Document> parser;

    public PageDomProvider(SourceReader sourceReader, Parser<Document> parser) {
        this.sourceReader = sourceReader;
        this.parser = parser;
    }

    @Override
    public Document getPage(String sourceName, int type, String addition)
            throws IOException,
                   XMLStreamException {
        Checkpoints checkpoints = sourceReader.getCheckpoints(sourceName, type, addition);
        Document document = parser.parseFromHtml(checkpoints.getLink(),
                                                 checkpoints.getStart(),
                                                 checkpoints.getEnd());
        log.info("Connected to " + checkpoints.getLink());
        return document;
    }
}
