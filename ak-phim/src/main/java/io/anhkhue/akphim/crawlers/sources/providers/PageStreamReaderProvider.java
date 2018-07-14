package io.anhkhue.akphim.crawlers.sources.providers;

import io.anhkhue.akphim.crawlers.sources.Checkpoints;
import io.anhkhue.akphim.crawlers.sources.readers.SourceReader;
import io.anhkhue.akphim.crawlers.xml.parsers.Parser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;

@Slf4j
@Component
public class PageStreamReaderProvider implements PageProvider<XMLStreamReader> {

    private final SourceReader sourceReader;

    private final Parser<XMLStreamReader> parser;

    public PageStreamReaderProvider(SourceReader sourceReader, Parser<XMLStreamReader> parser) {
        this.sourceReader = sourceReader;
        this.parser = parser;
    }

    @Override
    public XMLStreamReader getPage(String sourceName, int type, String addition) throws IOException, XMLStreamException {
        Checkpoints checkpoints = sourceReader.getCheckpoints(sourceName, type, addition);
        XMLStreamReader reader = parser.parseFromHtml(checkpoints.getLink(),
                                                      checkpoints.getStart(),
                                                      checkpoints.getEnd());
        log.info("Connected to " + checkpoints.getLink());
        return reader;
    }

}
