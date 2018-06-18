package io.anhkhue.more.crawlservice.crawlers.websites;

import io.anhkhue.more.crawlservice.dto.ActorDTO;
import io.anhkhue.more.crawlservice.dto.CategoryDTO;
import io.anhkhue.more.crawlservice.dto.LinkDTO;
import io.anhkhue.more.crawlservice.dto.MovieDTO;
import io.anhkhue.more.crawlservice.xml.parsers.StAXParser;
import io.anhkhue.more.crawlservice.xml.readers.SourceReader;
import lombok.extern.slf4j.Slf4j;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.anhkhue.more.crawlservice.crawlers.CrawlerConstants.PageTypeConstants.*;

@Slf4j
public class PhimmoiCrawler extends WebsiteCrawler {

    private final Collection<MovieDTO> dtoCollection = new HashSet<>();

    private final String sourceName = "Phimmoi.com";

    private static final int MAX_PAGE = 2;

    @Override
    void crawlMoviesFromWebsite(int type, Collection<MovieDTO> movieSet) {
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
            XMLStreamReader pageStreamReader = SourceReader.getPageStreamReader(sourceName, NEW_LIST, null);

            MovieDTO movieDto = null;

            while (pageStreamReader.hasNext()) {
                if (!foundTitle &&
                    !foundPoster) {
                    movieDto = new MovieDTO();
                }

                int cursor = pageStreamReader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    StartElement element = StAXParser.getXMLEvent(pageStreamReader).asStartElement();

                    // Retrieve Movie items
                    if (element.getName().getLocalPart().equals("a")) {
                        // Retrieve links
                        String href = element.getAttributeByName(new QName(null, "href"))
                                             .getValue();
                        LinkDTO linkDto = LinkDTO.builder()
                                                 .url(href)
                                                 .source(sourceName)
                                                 .isCinema(false)
                                                 .build();
                        Collection<LinkDTO> links = Collections.singletonList(linkDto);
                        movieDto.setLinks(links);
                        // Retrieve title
                        String title = element.getAttributeByName(new QName(null, "title"))
                                              .getValue()
                                              .toLowerCase();
                        foundTitle = true;
                        movieDto.setTitle(title);
                    } // end if <a>

                    // Retrieve poster
                    if (element.getName().getLocalPart().equals("img")) {
                        String poster = element.getAttributeByName(new QName(null, "src"))
                                               .getValue();
                        foundPoster = true;
                        movieDto.setPoster(poster);
                    } // end if <img>

                    if (foundTitle &&
                        foundPoster) {
                        dtoCollection.add(movieDto);

                        foundTitle = false;
                        foundPoster = false;
                    }
                } // end if START ELEMENT
            } // end while reader hasNext

        } catch (IOException e) {
            log.info(this.getClass().getName() + "_" + e.getClass().getName() + ": " + e.getMessage());
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void crawlMoviesByPagination(int page) {
        boolean foundHref = false;
        boolean foundTitle = false;
        boolean foundPoster = false;

        try {
            XMLStreamReader pageStreamReader = SourceReader.getPageStreamReader(sourceName,
                                                                                PAGINATION_LIST,
                                                                                String.valueOf(page));

            MovieDTO dto = null;

            while (pageStreamReader.hasNext()) {
                if (!foundHref &&
                    !foundPoster &&
                    !foundTitle) {
                    dto = new MovieDTO();
                }

                int cursor = pageStreamReader.next();
                if (cursor == XMLStreamConstants.START_ELEMENT) {
                    StartElement element = StAXParser.getXMLEvent(pageStreamReader).asStartElement();

                    // Retrieve Movie items
                    if (element.getName().getLocalPart().equals("a")) {
                        // Retrieve links
                        String href = element.getAttributeByName(new QName(null, "href"))
                                             .getValue();
                        foundHref = true;
                        LinkDTO linkDto = LinkDTO.builder()
                                                 .source(sourceName)
                                                 .url(href)
                                                 .isCinema(false)
                                                 .build();
                        Collection<LinkDTO> links = Collections.singletonList(linkDto);
                        dto.setLinks(links);
                    } // end if <a>

                    // Retrieve poster
                    if (element.getName().getLocalPart().equals("div")) {
                        String movieThumbnail = element.getAttributeByName(new QName(null, "class"))
                                                       .getValue();

                        if (movieThumbnail.equals("movies-thumbnail")) {
                            String style = element.getAttributeByName(new QName(null, "style"))
                                                  .getValue();
                            Matcher matcher = Pattern.compile("\\(([^)]+)\\)").matcher(style);
                            if (matcher.find()) {
                                String poster = matcher.group(1);
                                dto.setPoster(poster);
                                foundPoster = true;
                            } // end if matcher
                        } // end if class="movies-thumbnail"
                    } // end if <div>

                    // Retrieve title
                    if (element.getName().getLocalPart().equals("span")) {
                        String sClass = element.getAttributeByName(new QName(null, "class"))
                                               .getValue();
                        if (sClass.equals("movies-title-1")) {
                            pageStreamReader.next();
                            String title = StAXParser.getXMLEvent(pageStreamReader).asCharacters()
                                                     .getData()
                                                     .toLowerCase();
                            dto.setTitle(title);
                            foundTitle = true;
                        } // end if class "movies-title-1"
                    } // end if <span>

                    if (foundHref &&
                        foundPoster &&
                        foundTitle) {
                        dtoCollection.add(dto);

                        foundTitle = false;
                        foundPoster = false;
                        foundHref = false;
                    }
                } // end if START ELEMENT
            } // end while reader hasNext
        } catch (IOException | XMLStreamException e) {
            log.info(this.getClass().getName() + "_" + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Override
    public void crawlMovieDetail() {
        Iterator<MovieDTO> dtoIterator = dtoCollection.iterator();
        while (dtoIterator.hasNext()) {
            boolean foundYear = false;
            boolean foundDirector = false;
            boolean foundCategories = false;
            boolean foundActors = false;

            MovieDTO movieDto = dtoIterator.next();
            try {
                List<LinkDTO> links = movieDto.getLinks()
                                              .stream()
                                              .filter(linkDto -> linkDto.getSource().equals(sourceName))
                                              .collect(Collectors.toList());
                XMLStreamReader pageStreamReader = SourceReader.getPageStreamReader(sourceName,
                                                                                    NEW_DETAIL,
                                                                                    links.get(0).getUrl());
                while (pageStreamReader.hasNext()) {
                    int cursor = pageStreamReader.next();
                    if (cursor == XMLStreamConstants.START_ELEMENT) {
                        StartElement element = StAXParser.getXMLEvent(pageStreamReader).asStartElement();

                        // Retrieve Year
                        if (element.getName().getLocalPart().equals("dt")) {
                            int nameEvent = pageStreamReader.next(); // get value
                            if (nameEvent == XMLStreamConstants.CHARACTERS) {
                                String nameValue = pageStreamReader.getText();
                                if (nameValue.equals("Năm:")) {
                                    String year = StAXParser.getTextContent(pageStreamReader, "dd");
                                    movieDto.setYear(year);
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
                                String director = StAXParser.getXMLEvent(pageStreamReader).asCharacters().getData();
                                if (director.equals("Đang cập nhật")) {
                                    dtoIterator.remove();
                                    continue;
                                } else {
                                    movieDto.setDirector(director);
                                    foundDirector = true;
                                }
                            } // end if class contains "dd-director"

                            // Retrieve categoryDTOS
                            if (sClass.contains("dd-cat")) {
                                boolean endLoop = false;
                                Collection<CategoryDTO> categories = new ArrayList<>();
                                while (!endLoop) {
                                    int nextTag = pageStreamReader.next();
                                    if (nextTag == XMLStreamConstants.START_ELEMENT) {
                                        StartElement startElement = StAXParser.getXMLEvent(pageStreamReader).asStartElement();
                                        if (startElement.getName().getLocalPart().equals("a")) {
                                            pageStreamReader.next(); // value
                                            String category = StAXParser.getXMLEvent(pageStreamReader).asCharacters()
                                                                        .getData()
                                                                        .replaceAll("Phim ", "")
                                                                        .toLowerCase();
                                            categories.add(new CategoryDTO(category));
                                        } else {
                                            endLoop = true;
                                            movieDto.setCategories(categories);
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
                                Collection<ActorDTO> actors = new ArrayList<>();
                                while (!endLoop) {
                                    int nextTag = pageStreamReader.next();
                                    if (nextTag == XMLStreamConstants.START_ELEMENT) {
                                        StartElement startElement = StAXParser.getXMLEvent(pageStreamReader).asStartElement();
                                        if (startElement.getName().getLocalPart().equals("a")) {
                                            String actor = startElement.getAttributeByName(new QName(null,
                                                                                                     "title"))
                                                                       .getValue()
                                                                       .replaceAll("[…]", "")
                                                                       .replaceAll("\\.\\.\\.", "")
                                                                       .replaceAll("\\.\\.\\.\\.", "");
                                            actors.add(ActorDTO.builder()
                                                               .fullName(actor)
                                                               .build());
                                        } // end if <a>
                                    } // end if start element
                                    if (actors.size() == 3) {
                                        endLoop = true;
                                        movieDto.setActors(actors);
                                        foundActors = true;
                                    }
                                    if (nextTag == XMLStreamConstants.END_ELEMENT) {
                                        EndElement endElement = StAXParser.getXMLEvent(pageStreamReader).asEndElement();
                                        if (endElement.getName().getLocalPart().equals("ul")) {
                                            endLoop = true;
                                            movieDto.setActors(actors);
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
                log.info(this.getClass().getName() + "_" + e.getClass().getName()
                         + ": Skipped crawling " + movieDto.getTitle());
                dtoIterator.remove();
            }
        }
        movies.addAll(dtoCollection);
    }
}
