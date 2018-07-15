package io.anhkhue.akphim.crawlers.agents.vendors;

import io.anhkhue.akphim.crawlers.sources.providers.PageDomProvider;
import io.anhkhue.akphim.crawlers.sources.providers.PageProvider;
import io.anhkhue.akphim.crawlers.sources.readers.SourceReader;
import io.anhkhue.akphim.crawlers.xml.parsers.DomParser;
import io.anhkhue.akphim.crawlers.xml.parsers.StAXParser;
import io.anhkhue.akphim.models.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.stream.XMLStreamException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import static io.anhkhue.akphim.crawlers.constants.CrawlersConstants.PageTypeConstants.*;

@Slf4j
public class BhdStarCrawler extends VendorCrawler {

    private final String sourceName = "BHD Star";

    private DomParser domParser = new DomParser();
    private PageProvider<Document> pageProvider = new PageDomProvider(new SourceReader(new StAXParser()),
                                                                      domParser);

    private XPath xPath = domParser.createXPath();

    @Override
    protected void crawlMoviesFromVendor(int type) {
        try {
            Document pageDom = pageProvider.getPage(sourceName, type, null);

            // Retrieve Movie items
            String movieItemXPath = "//div[contains(@class, 'film--item')]";
            NodeList movieItems = (NodeList) xPath.evaluate(movieItemXPath, pageDom, XPathConstants.NODESET);

            if (movieItems != null) {
                for (int i = 0; i < movieItems.getLength(); i++) {
                    Node movieItem = movieItems.item(i);

                    // Return 2 <a>
                    String movieInfoXPath = "a[contains(@href, 'https://www.bhdstar.vn/movies/')]";
                    NodeList aNodeList = (NodeList) xPath.evaluate(movieInfoXPath, movieItem, XPathConstants.NODESET);
                    // Retrieve 1st <a>
                    Node aNode1 = aNodeList.item(0);
                    String imgXPath = "img";
                    // Retrieve poster
                    Node imgPoster = (Node) xPath.evaluate(imgXPath, aNode1, XPathConstants.NODE);
                    String poster = imgPoster.getAttributes().getNamedItem("src").getNodeValue();

                    // Retrieve 2nd <a>
                    Node aNode2 = aNodeList.item(1);
                    // Retrieve links
                    NamedNodeMap aNode2Attributes = aNode2.getAttributes();
                    String linkAttr = aNode2Attributes.getNamedItem("href").getNodeValue();
                    Link link = Link.builder()
                                    .isCinema(true)
                                    .url(linkAttr)
                                    .source(sourceName)
                                    .build();
                    Links links = new Links();
                    links.getLink().add(link);
                    // Retrieve title
                    String titleXPath = "span[contains(@class, movies)]";
                    Node spanTitle = (Node) xPath.evaluate(titleXPath, aNode2, XPathConstants.NODE);
                    String title = spanTitle.getTextContent()
                                            .toLowerCase();

                    Movie movie = Movie.builder()
                                       .title(title)
                                       .poster(poster)
                                       .links(links)
                                       .onCinema(true)
                                       .build();
                    switch (type) {
                        case NEW_LIST:
                            movie.setComing(false);
                            showingMovies.add(movie);
                            break;
                        case COMING_LIST:
                            movie.setComing(true);
                            comingMovies.add(movie);
                            break;
                    }
                }
            }
        } catch (XPathExpressionException | IOException | XMLStreamException e) {
            log.info(this.getClass().getSimpleName() + "_" + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    @Override
    public void crawlMovieDetail() {
        crawlMovieDetailFromCollection(showingMovies, NEW_DETAIL);
        crawlMovieDetailFromCollection(comingMovies, COMING_DETAIL);
    }

    private void crawlMovieDetailFromCollection(Collection<Movie> collection, int detailType) {
        Iterator<Movie> dtoIterator = collection.iterator();
        while (dtoIterator.hasNext()) {
            Movie movieDto = dtoIterator.next();
            try {
                List<Link> linkList = movieDto.getLinks()
                                              .getLink()
                                              .stream()
                                              .filter(linkDto -> linkDto.getSource().equals(sourceName))
                                              .collect(Collectors.toList());
                Links links = new Links();
                links.getLink().addAll(linkList);

                Document pageDom = pageProvider.getPage(sourceName, detailType, linkList.get(0).getUrl());

                // Retrieve description
                String descriptionXPath = "//div[contains(@class, 'film--detail')]/p";
                Node descriptionNode = (Node) xPath.evaluate(descriptionXPath, pageDom, XPathConstants.NODE);
                String description = descriptionNode.getTextContent();

                // Retrieve info
                String infoXPath = "//ul[contains(@class, 'film--info')]";
                Node infoNode = (Node) xPath.evaluate(infoXPath, pageDom, XPathConstants.NODE);
                // Retrieve InfoList
                String infoListXPath = "li/span[contains(@class, 'col-right')]";
                NodeList infoNodeList = (NodeList) xPath.evaluate(infoListXPath, infoNode, XPathConstants.NODESET);
                // 0 - Đạo diễn
                Node directorNode = infoNodeList.item(0);
                String director = directorNode.getTextContent();
                movieDto.setDirector(director);
                // 1 - Diễn viên
                Node actorsNode = infoNodeList.item(1);
                String actorNamesRaw = actorsNode.getTextContent();
                HashSet<String> actorNames = Arrays.stream(actorNamesRaw.split(", "))
                                                   .filter(actorName -> !actorName.contains("…"))
                                                   .filter(actorName -> !actorName.contains("\\.\\.\\."))
                                                   .collect(Collectors.toCollection(HashSet::new));
                Actors actors = new Actors();
                actors.getActor().addAll(actorNames);
                movieDto.setActors(actors);
                // 2 - Thể loại
                Node categoryNode = infoNodeList.item(2);
                String categoriesRaw = categoryNode.getTextContent();
                List<Category> categoryList = Arrays.stream(categoriesRaw.split(", "))
                                                    .map(category -> {
                                                        category = category.toLowerCase();
                                                        return new Category(category);
                                                    })
                                                    .collect(Collectors.toList());
                Categories categories = new Categories();
                categories.getCategory().addAll(categoryList);
                movieDto.setCategories(categories);
                // 4 - Năm khởi chiếu
                Node yearNode = infoNodeList.item(3);
                String year = yearNode.getTextContent().split("-")[0];
                movieDto.setYear(new BigInteger(year));
            } catch (XPathExpressionException |
                    IOException |
                    XMLStreamException e) {
                log.info(this.getClass().getSimpleName() + "_" + e.getClass().getSimpleName()
                         + ": Skipped crawling " + movieDto.getTitle());
                dtoIterator.remove();
            }
        }
    }
}
