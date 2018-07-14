package io.anhkhue.akphim.crawlers.sources.readers;

import io.anhkhue.akphim.crawlers.constants.CrawlersConstants;
import io.anhkhue.akphim.crawlers.sources.Checkpoints;
import io.anhkhue.akphim.crawlers.xml.parsers.StAXParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.anhkhue.akphim.crawlers.constants.CrawlersConstants.PageTypeConstants.*;

@Component
public class SourceReader {

    private final StAXParser stAXParser;

    private String XML_CRAWLER_SOURCES_PATH = "static/xml/crawler-sources.xml";

    public SourceReader(StAXParser stAXParser) {
        this.stAXParser = stAXParser;
    }

    public Checkpoints getCheckpoints(String sourceName, int type, String addition)
            throws IOException, XMLStreamException {
        String name = "";
        String pageType = "";
        String isComing = "";
        String containPagination = "";
        String link = "";
        String startPoint = "";
        String endPoint = "";

        File xmlSourceFile = new ClassPathResource(XML_CRAWLER_SOURCES_PATH).getFile();

        XMLStreamReader sourceReader = stAXParser.parseFromSource(new FileInputStream(xmlSourceFile));

        while (sourceReader.hasNext()) {
            int eventType = sourceReader.next();
            // Get all elements as XMLEvent object
            if (eventType == XMLStreamConstants.START_ELEMENT) {
                // Get immutable XMLEvent
                StartElement event = stAXParser.getXMLEvent(sourceReader).asStartElement();

                String nameData = getTextContent(event, sourceReader, "name");
                if (!nameData.equals("")) {
                    name = nameData;
                }

                if (name.equals(sourceName)) {
                    if (event.getName().getLocalPart().equals("page")) {
                        Attribute coming = event.getAttributeByName(new QName(null, "isComing"));
                        isComing = coming.getValue();

                        Attribute pagination = event.getAttributeByName(new QName(null, "containPagination"));
                        containPagination = pagination == null ? "" : pagination.getValue();

                        // Type
                        eventType = sourceReader.nextTag();
                        event = stAXParser.getXMLEvent(sourceReader).asStartElement();
                        pageType = getTextContent(event, sourceReader, "type");
                    } // end if page

                    switch (type) {
                        case NEW_LIST:
                            if (pageType.equals("List") &&
                                isComing.equals("false") &&
                                containPagination.isEmpty()) {
                                // Link
                                if (link.isEmpty()) {
                                    link = getTextContent(event, sourceReader, "link");
                                }
                                // StartPoint
                                if (startPoint.isEmpty()) {
                                    startPoint = getTextContent(event, sourceReader, "startPoint");
                                }
                                // EndPoint
                                if (endPoint.isEmpty()) {
                                    endPoint = getTextContent(event, sourceReader, "endPoint");
                                }
                                if (!link.isEmpty() &&
                                    !startPoint.isEmpty() &&
                                    !endPoint.isEmpty()) {
                                    break;
                                }
                            }
                            break;
                        case CrawlersConstants.PageTypeConstants.PAGINATION_LIST:
                            if (pageType.equals("List") &&
                                isComing.equals("false") &&
                                containPagination.equals("true")) {
                                // Link
                                if (link.isEmpty()) {
                                    String data = getTextContent(event, sourceReader, "link");
                                    if (!data.equals("")) {
                                        link = data + addition;
                                    }
                                }
                                // StartPoint
                                if (startPoint.isEmpty()) {
                                    startPoint = getTextContent(event, sourceReader, "startPoint");
                                }
                                // EndPoint
                                if (endPoint.isEmpty()) {
                                    endPoint = getTextContent(event, sourceReader, "endPoint");
                                }
                                if (!link.isEmpty() &&
                                    !startPoint.isEmpty() &&
                                    !endPoint.isEmpty()) {
                                    break;
                                }
                            }
                            break;
                        case COMING_LIST:
                            if (pageType.equals("List") &&
                                isComing.equals("true") &&
                                containPagination.isEmpty()) {
                                // Link
                                if (link.isEmpty()) {
                                    link = getTextContent(event, sourceReader, "link");
                                }
                                // StartPoint
                                if (startPoint.isEmpty()) {
                                    startPoint = getTextContent(event, sourceReader, "startPoint");
                                }
                                // EndPoint
                                if (endPoint.isEmpty()) {
                                    endPoint = getTextContent(event, sourceReader, "endPoint");
                                }
                                if (!link.isEmpty() &&
                                    !startPoint.isEmpty() &&
                                    !endPoint.isEmpty()) {
                                    break;
                                }
                            }
                            break;
                        case NEW_DETAIL:
                            if (pageType.equals("Detail") &&
                                isComing.equals("false") &&
                                containPagination.isEmpty()) {
                                // Link
                                if (link.isEmpty()) {
                                    link = addition;
                                }
                                // StartPoint
                                if (startPoint.isEmpty()) {
                                    startPoint = getTextContent(event, sourceReader, "startPoint");
                                }
                                // EndPoint
                                if (endPoint.isEmpty()) {
                                    endPoint = getTextContent(event, sourceReader, "endPoint");
                                }
                                if (!link.isEmpty() &&
                                    !startPoint.isEmpty() &&
                                    !endPoint.isEmpty()) {
                                    break;
                                }
                            }
                            break;
                        case COMING_DETAIL:
                            if (pageType.equals("Detail") &&
                                isComing.equals("true") &&
                                containPagination.isEmpty()) {
                                // Link
                                if (link.isEmpty()) {
                                    link = addition;
                                }
                                // StartPoint
                                if (startPoint.isEmpty()) {
                                    startPoint = getTextContent(event, sourceReader, "startPoint");
                                }
                                // EndPoint
                                if (endPoint.isEmpty()) {
                                    endPoint = getTextContent(event, sourceReader, "endPoint");
                                }
                                if (!link.isEmpty() &&
                                    !startPoint.isEmpty() &&
                                    !endPoint.isEmpty()) {
                                    break;
                                }
                            }
                            break;
                    } // end switch type
                } // end if name
            } // end if START_ELEMENT
        } // end while sourceReader

        return Checkpoints.builder()
                          .link(link)
                          .start(startPoint)
                          .end(endPoint)
                          .build();
    }

    public List<String> getCrawlerClassPaths() throws XMLStreamException,
                                                      IOException {
        List<String> classPaths = new ArrayList<>();

        List<String> vendorCrawlerClassPaths = new ArrayList<>();
        List<String> websiteCrawlerClassPaths = new ArrayList<>();

        File xmlSourceFile = new ClassPathResource(XML_CRAWLER_SOURCES_PATH).getFile();

        XMLStreamReader sourceReader = stAXParser.parseFromSource(new FileInputStream(xmlSourceFile));

        boolean isCinema = false;
        while (sourceReader.hasNext()) {
            int eventType = sourceReader.next();
            // Get all elements as XMLEvent object
            if (eventType == XMLStreamConstants.START_ELEMENT) {
                StartElement element = stAXParser.getXMLEvent(sourceReader).asStartElement();

                if (element.getName().getLocalPart().equals("source")) {
                    Attribute isCinemaAttribute = element.getAttributeByName(new QName(null, "isCinema"));
                    isCinema = isCinemaAttribute.getValue().equals("true");
                }

                String classPath = getTextContent(element, sourceReader, "classPath");
                if (!classPath.isEmpty()) {
                    if (isCinema) {
                        vendorCrawlerClassPaths.add(classPath);
                    } else {
                        websiteCrawlerClassPaths.add(classPath);
                    }
                }
            }
        }

        classPaths.addAll(vendorCrawlerClassPaths);
        classPaths.addAll(websiteCrawlerClassPaths);
        return classPaths;
    }

    private String getTextContent(StartElement startElement, XMLStreamReader streamReader, String tagName)
            throws XMLStreamException {
        if (startElement.getName().getLocalPart().equals(tagName)) {
            int eventType = streamReader.next();
            if (eventType == XMLStreamConstants.CHARACTERS) {
                Characters value = stAXParser.getXMLEvent(streamReader).asCharacters();
                return value.getData();
            }
        }

        return "";
    }
}


