package io.anhkhue.more.crawlers.xml.parsers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class DomParser implements Parser<Document> {

    @Override
    public Document parseFromSource(Object source) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            if (source instanceof File) return builder.parse((File) source);
            if (source instanceof InputStream) return builder.parse((InputStream) source);
            if (source instanceof String) return builder.parse((String) source);
            if (source instanceof InputSource) return builder.parse((InputSource) source);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            log.info(this.getClass().getName() + "_" + e.getClass().getName() + ": " + e.getMessage());
        }

        return null;
    }

    public XPath createXPath() {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        return xPathFactory.newXPath();
    }
}
