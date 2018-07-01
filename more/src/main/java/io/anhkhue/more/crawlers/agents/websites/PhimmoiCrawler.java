package io.anhkhue.more.crawlers.agents.websites;

import io.anhkhue.more.crawlers.sources.providers.PageProvider;
import io.anhkhue.more.crawlers.sources.providers.PageStreamReaderProvider;
import io.anhkhue.more.crawlers.sources.readers.SourceReader;
import io.anhkhue.more.crawlers.xml.parsers.StAXParser;
import io.anhkhue.more.models.dto.*;
import lombok.extern.slf4j.Slf4j;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.anhkhue.more.crawlers.constants.CrawlersConstants.PageTypeConstants.*;

@Slf4j
public class PhimmoiCrawler extends WebsiteCrawler {

    private final String sourceName = "Phimmoi.com";
    private static final int MAX_PAGE = 10;

    private final Collection<Movie> dtoCollection = new HashSet<>();

    private StAXParser stAXParser = new StAXParser();
    private SourceReader sourceReader = new SourceReader(stAXParser);

    private PageProvider<XMLStreamReader> pageProvider = new PageStreamReaderProvider(sourceReader, stAXParser);

    @Override
    void crawlMoviesFromWebsite(int type, Collection<Movie> movieSet) {
        switch (type) {
            case NEW_LIST:
                crawlNewMovies();
                break;
            case PAGINATION_LIST:
                for (int i = 1; i <= MAX_PAGE; i++) {
                    crawlMoviesByPagination(i);
                }
                break;
        }
    }

    private void crawlNewMovies() {
        boolean foundTitle = false;
        boolean foundPoster = false;

        try {
            XMLStreamReader pageStreamReader = pageProvider.getPage(sourceName, NEW_LIST, null);

            Movie movie = null;

            while (pageStreamReader.hasNext()) {
                if (!foundTitle &&
                    !foundPoster) {
                    movie = new Movie();
                }

                int cursor = pageStreamReader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    StartElement element = stAXParser.getXMLEvent(pageStreamReader).asStartElement();

                    // Retrieve Movie items
                    if (element.getName().getLocalPart().equals("a")) {
                        // Retrieve links
                        String href = element.getAttributeByName(new QName(null, "href"))
                                             .getValue();
                        Link link = Link.builder()
                                        .url(href)
                                        .source(sourceName)
                                        .isCinema(false)
                                        .build();
                        Links links = new Links();
                        links.getLink().add(link);
                        movie.setLinks(links);
                        // Retrieve title
                        String title = element.getAttributeByName(new QName(null, "title"))
                                              .getValue()
                                              .toLowerCase();
                        foundTitle = true;
                        movie.setTitle(title);
                    } // end if <a>

                    // Retrieve poster
                    if (element.getName().getLocalPart().equals("img")) {
                        String poster = element.getAttributeByName(new QName(null, "src"))
                                               .getValue();
                        foundPoster = true;
                        movie.setPoster(poster);
                    } // end if <img>

                    if (foundTitle &&
                        foundPoster) {
                        dtoCollection.add(movie);

                        foundTitle = false;
                        foundPoster = false;
                    }
                } // end if START ELEMENT
            } // end while reader hasNext

        } catch (IOException | XMLStreamException e) {
            log.info(this.getClass().getSimpleName() + "_" + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private void crawlMoviesByPagination(int page) {
        boolean foundHref = false;
        boolean foundTitle = false;
        boolean foundPoster = false;

        try {
            XMLStreamReader pageStreamReader = pageProvider.getPage(sourceName,
                                                                    PAGINATION_LIST,
                                                                    String.valueOf(page));

            Movie movie = null;

            while (pageStreamReader.hasNext()) {
                if (!foundHref &&
                    !foundPoster &&
                    !foundTitle) {
                    movie = new Movie();
                }

                int cursor = pageStreamReader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    StartElement element = stAXParser.getXMLEvent(pageStreamReader).asStartElement();

                    // Retrieve Movie items
                    if (element.getName().getLocalPart().equals("a")) {
                        // Retrieve links
                        String href = element.getAttributeByName(new QName(null, "href"))
                                             .getValue();
                        foundHref = true;
                        Link link = Link.builder()
                                        .source(sourceName)
                                        .url(href)
                                        .isCinema(false)
                                        .build();
                        Links links = new Links();
                        links.getLink().add(link);
                        movie.setLinks(links);
                    } // end if <a>

                    // Retrieve poster
                    if (element.getName().getLocalPart().equals("div")) {
                        String movieThumbnail = element.getAttributeByName(new QName(null, "class"))
                                                       .getValue();

                        if (movieThumbnail.equals("movie-thumbnail")) {
                            String style = element.getAttributeByName(new QName(null, "style"))
                                                  .getValue();
                            Matcher matcher = Pattern.compile("\\(([^)]+)\\)").matcher(style);
                            if (matcher.find()) {
                                String poster = matcher.group(1);
                                movie.setPoster(poster);
                                foundPoster = true;
                            } // end if matcher
                        } // end if class="movies-thumbnail"
                    } // end if <div>

                    // Retrieve title
                    if (element.getName().getLocalPart().equals("span")) {
                        String sClass = element.getAttributeByName(new QName(null, "class"))
                                               .getValue();
                        if (sClass.equals("movie-title-1")) {
                            pageStreamReader.next();
                            String title = stAXParser.getXMLEvent(pageStreamReader).asCharacters()
                                                     .getData()
                                                     .toLowerCase();
                            movie.setTitle(title);
                            foundTitle = true;
                        } // end if class "movies-title-1"
                    } // end if <span>

                    if (foundHref &&
                        foundPoster &&
                        foundTitle) {
                        dtoCollection.add(movie);

                        foundTitle = false;
                        foundPoster = false;
                        foundHref = false;
                    }
                } // end if START ELEMENT
            } // end while reader hasNext
        } catch (IOException | XMLStreamException e) {
            log.info(this.getClass().getSimpleName() + "_" + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    @Override
    public void crawlMovieDetail() {
        Iterator<Movie> movieIterator = dtoCollection.iterator();

        while (movieIterator.hasNext()) {

            boolean foundYear = false;
            boolean foundDirector = false;
            boolean foundCategories = false;
            boolean foundActors = false;

            Movie movie = movieIterator.next();

            try {
                List<Link> links = movie.getLinks()
                                        .getLink()
                                        .stream()
                                        .filter(linkDto -> linkDto.getSource().equals(sourceName))
                                        .collect(Collectors.toList());
                XMLStreamReader pageStreamReader = pageProvider.getPage(sourceName,
                                                                        NEW_DETAIL,
                                                                        links.get(0).getUrl());
                    while (pageStreamReader.hasNext()) {
                    int cursor = pageStreamReader.next();
                    if (cursor == XMLStreamConstants.START_ELEMENT) {
                        StartElement element = stAXParser.getXMLEvent(pageStreamReader).asStartElement();

                        // Retrieve Year
                        if (element.getName().getLocalPart().equals("dt")) {
                            int nameEvent = pageStreamReader.next(); // get value
                            if (nameEvent == XMLStreamConstants.CHARACTERS) {
                                String nameValue = pageStreamReader.getText();
                                if (nameValue.equals("Năm:")) {
                                    String year = stAXParser.getTextContent(pageStreamReader, "dd").trim();
                                    movie.setYear(BigInteger.valueOf(Long.parseLong(year)));
                                    foundYear = true;
                                } // end if "Năm:"
                            } // end if CHARACTERS
                        }

                        if (element.getName().getLocalPart().equals("dd")) {
                            String sClass = element.getAttributeByName(new QName(null, "class"))
                                                   .getValue();
                            // Retrieve director
                            if (sClass.contains("dd-director")) {
                                pageStreamReader.nextTag(); // to <a>
                                pageStreamReader.next(); // value
                                String director = stAXParser.getXMLEvent(pageStreamReader)
                                                            .asCharacters().getData();
                                if (director.equals("Đang cập nhật")) {
                                    movieIterator.remove();
                                    continue;
                                } else {
                                    movie.setDirector(director);
                                    foundDirector = true;
                                }
                            } // end if class contains "dd-director"

                            // Retrieve categoryDTOS
                            if (sClass.contains("dd-cat")) {
                                boolean endLoop = false;
                                Categories categories = new Categories();
                                while (!endLoop) {
                                    int nextTag = pageStreamReader.next();
                                    if (nextTag == XMLStreamConstants.START_ELEMENT) {
                                        StartElement startElement = stAXParser.getXMLEvent(pageStreamReader).asStartElement();
                                        if (startElement.getName().getLocalPart().equals("a")) {
                                            pageStreamReader.next(); // value
                                            String category = stAXParser.getXMLEvent(pageStreamReader).asCharacters()
                                                                        .getData()
                                                                        .replaceAll("Phim ", "")
                                                                        .toLowerCase();
                                            if (!category.equals("chiếu rạp")) {
                                                categories.getCategory()
                                                          .add(new Category(category));
                                            }
                                        } else {
                                            endLoop = true;
                                            movie.setCategories(categories);
                                            foundCategories = true;
                                        }
                                    } // end if start element
                                } // while endLoop
                            } // end if class contains "dd-director"
                        } // end if <dd>


                        if (element.getName().getLocalPart().equals("ul")) {
                            // Retrieve actorDTOs
                            Attribute idAttr = element.getAttributeByName(new QName(null, "id"));
                            String sClass = idAttr == null ? "" : idAttr.getValue();
                            if (sClass.equals("list_actor_carousel")) {
                                boolean endLoop = false;
                                Actors actors = new Actors();
                                while (!endLoop) {
                                    int nextTag = pageStreamReader.next();
                                    if (nextTag == XMLStreamConstants.START_ELEMENT) {
                                        StartElement startElement = stAXParser.getXMLEvent(pageStreamReader)
                                                                              .asStartElement();
                                        if (startElement.getName().getLocalPart().equals("a")) {
                                            String actorName
                                                    = startElement.getAttributeByName(new QName(null,
                                                                                                "title"))
                                                                  .getValue()
                                                                  .replaceAll("[…]", "")
                                                                  .replaceAll("\\.\\.\\.", "")
                                                                  .replaceAll("\\.\\.\\.\\.", "");
                                            actors.getActor()
                                                  .add(actorName);
                                        } // end if <a>
                                    } // end if start element
                                    if (actors.getActor().size() == 3) {
                                        endLoop = true;
                                        movie.setActors(actors);
                                        foundActors = true;
                                    }
                                    if (nextTag == XMLStreamConstants.END_ELEMENT) {
                                        EndElement endElement = stAXParser.getXMLEvent(pageStreamReader).asEndElement();
                                        if (endElement.getName().getLocalPart().equals("ul")) {
                                            endLoop = true;
                                            movie.setActors(actors);
                                            foundActors = true;
                                        } // end if <a>
                                    } // end if end element
                                } // while endLoop
                            } // end if class="list_actor_carousel"
                        } // end if <ul>

                        if (foundDirector &&
                            foundYear &&
                            foundCategories &&
                            foundActors) {
                            break;
                        }
                    } // end while reader hasNext
                }
            } catch (IOException | XMLStreamException e) {
                log.info(this.getClass().getSimpleName() + "_" + e.getClass().getSimpleName()
                         + ": Skipped crawling " + movie.getTitle());
                movieIterator.remove();
            }
        }
        movies.addAll(dtoCollection);
    }
}
