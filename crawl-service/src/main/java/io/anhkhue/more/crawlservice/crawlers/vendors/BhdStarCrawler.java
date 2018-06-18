package io.anhkhue.more.crawlservice.crawlers.vendors;

import io.anhkhue.more.crawlservice.dto.ActorDTO;
import io.anhkhue.more.crawlservice.dto.CategoryDTO;
import io.anhkhue.more.crawlservice.dto.LinkDTO;
import io.anhkhue.more.crawlservice.dto.MovieDTO;
import io.anhkhue.more.crawlservice.xml.readers.SourceReader;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.stream.XMLStreamException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static io.anhkhue.more.crawlservice.crawlers.CrawlerConstants.PageTypeConstants.*;

@Slf4j
public class BhdStarCrawler extends VendorCrawler {

    private final String sourceName = "BHD Star";

    @Override
    protected void crawlMoviesFromVendor(int type, Collection<MovieDTO> movieSet) {
        try {
            Document pageDom = SourceReader.getPageDom(sourceName, type, null);

            // Retrieve Movie items
            String movieItemXPath = "//div[contains(@class, 'film--item')]";
            NodeList movieItems = (NodeList) xPath.evaluate(movieItemXPath, pageDom, XPathConstants.NODESET);

            if (movieItems != null) {
                for (int i = 0; i < movieItems.getLength(); i++) {
                    Node movieItem = movieItems.item(i);

                    CategoryDTO categoryDTO = new CategoryDTO();

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
                    String link = aNode2Attributes.getNamedItem("href").getNodeValue();
                    LinkDTO linkDto = LinkDTO.builder()
                                             .isCinema(true)
                                             .url(link)
                                             .source(sourceName)
                                             .build();
                    Collection<LinkDTO> links = Collections.singletonList(linkDto);
                    // Retrieve title
                    String titleXPath = "span[contains(@class, movies)]";
                    Node spanTitle = (Node) xPath.evaluate(titleXPath, aNode2, XPathConstants.NODE);
                    String title = spanTitle.getTextContent()
                                            .toLowerCase();

                    MovieDTO movieDto = new MovieDTO();
                    movieDto.setTitle(title);
                    movieDto.setPoster(poster);
                    movieDto.setLinks(links);
                    movieDto.setOnCinema(true);

                    switch (type) {
                        case NEW_LIST:
                            movieDto.setComing(false);
                            showingMovies.add(movieDto);
                            break;
                        case COMING_LIST:
                            movieDto.setComing(true);
                            comingMovies.add(movieDto);
                            break;
                    }
                }
            }
        } catch (XPathExpressionException |
                IOException e) {
            log.info(this.getClass().getName() + "_" + e.getClass().getName() + ": " + e.getMessage());
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void crawlMovieDetail() {
        crawlMovieDetailFromCollection(showingMovies, NEW_DETAIL);
        crawlMovieDetailFromCollection(comingMovies, COMING_DETAIL);
    }

    private void crawlMovieDetailFromCollection(Collection<MovieDTO> collection, int detailType) {
        Iterator<MovieDTO> dtoIterator = collection.iterator();
        while (dtoIterator.hasNext()) {
            MovieDTO movieDto = dtoIterator.next();
            try {
                List<LinkDTO> links = movieDto.getLinks().stream()
                                              .filter(linkDto -> linkDto.getSource().equals(sourceName))
                                              .collect(Collectors.toList());
                Document pageDom = SourceReader.getPageDom(sourceName, detailType, links.get(0).getUrl());

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
                String actors = actorsNode.getTextContent();
                HashSet<ActorDTO> actorSet = Arrays.stream(actors.split(", "))
                                                   .filter(actorName -> !actorName.contains("…"))
                                                   .filter(actorName -> !actorName.contains("..."))
                                                   .map(name -> ActorDTO.builder().fullName(name).build())
                                                   .collect(Collectors.toCollection(HashSet::new));
                movieDto.setActors(actorSet);
                // 2 - Thể loại
                Node categoryNode = infoNodeList.item(2);
                String categories = categoryNode.getTextContent();
                List<CategoryDTO> categoryList = Arrays.stream(categories.split(", "))
                                                       .map(category -> {
                                                           category = category.toLowerCase();
                                                           return new CategoryDTO(category);
                                                       })
                                                       .collect(Collectors.toList());
                movieDto.setCategories(categoryList);
                // 4 - Năm khởi chiếu
                movieDto.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
            } catch (XPathExpressionException |
                    IOException |
                    XMLStreamException e) {
                log.info(this.getClass().getName() + "_" + e.getClass().getName()
                         + ": Skipped crawling " + movieDto.getTitle());
                dtoIterator.remove();
            }
        }
    }
}
