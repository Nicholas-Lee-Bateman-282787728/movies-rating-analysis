package io.anhkhue.akphim.crawlers.xml.parsers;

import com.sun.xml.internal.stream.events.XMLEventAllocatorImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventAllocator;
import java.io.InputStream;

@Slf4j
@Component
public class StAXParser implements Parser<XMLStreamReader> {

    private XMLEventAllocator allocator = null;

    @Override
    public XMLStreamReader parseFromSource(Object source) {
        XMLStreamReader reader = null;
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            factory.setEventAllocator(new XMLEventAllocatorImpl());
            allocator = factory.getEventAllocator();

            reader = factory.createXMLStreamReader((InputStream) source);
        } catch (XMLStreamException e) {
            log.info(this.getClass().getName() + "_" + e.getClass().getName() + ": " + e.getMessage());
        }
        return reader;
    }

    public XMLEvent getXMLEvent(XMLStreamReader reader) throws XMLStreamException {
        return allocator.allocate(reader);
    }

    public String getTextContent(XMLStreamReader reader, String elementName)
            throws XMLStreamException {
        if (reader != null) {
            while (reader.hasNext()) {
                int cursor = reader.getEventType();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    String tagName = reader.getLocalName();
                    if (tagName.equals(elementName)) {
                        reader.next(); // value
                        String result = reader.getText();
                        reader.nextTag(); // end element

                        return result;
                    } // end if tagName
                } // end if cursor START_ELEMENT
                reader.next();
            } // end while reader hasNext
        } // end if reader null

        return null;
    }
}
