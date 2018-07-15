package io.anhkhue.akphim.controllers.routers;

import io.anhkhue.akphim.crawlers.utils.JaxbUtils;
import io.anhkhue.akphim.mining.cache.AccountRecommendationCache;
import io.anhkhue.akphim.models.constants.RoleConstants;
import io.anhkhue.akphim.models.dto.Account;
import io.anhkhue.akphim.models.dto.AccountRateMovie;
import io.anhkhue.akphim.models.dto.Link;
import io.anhkhue.akphim.models.dto.Movie;
import io.anhkhue.akphim.models.mining.report.Report;
import io.anhkhue.akphim.models.mining.report.Vendor;
import io.anhkhue.akphim.models.mining.report.Vendors;
import io.anhkhue.akphim.repositories.AccountRateMovieRepository;
import io.anhkhue.akphim.repositories.LinkRepository;
import io.anhkhue.akphim.services.CrawlService;
import io.anhkhue.akphim.services.MiningService;
import io.anhkhue.akphim.services.MovieService;
import io.anhkhue.akphim.services.VendorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static io.anhkhue.akphim.controllers.routers.PageRouterConstants.ROUTER;
import static io.anhkhue.akphim.controllers.routers.PageRouterConstants.Url.*;

@Slf4j
@Controller
public class PageRouter {

    private final MovieService movieService;
    private final JaxbUtils jaxbUtils;

    private final MiningService miningService;

    private final VendorService vendorService;

    private final LinkRepository linkRepository;

    private final AccountRateMovieRepository accountRateMovieRepository;

    public PageRouter(MovieService movieService,
                      JaxbUtils jaxbUtils,
                      MiningService miningService,
                      VendorService vendorService,
                      LinkRepository linkRepository,
                      AccountRateMovieRepository accountRateMovieRepository) {
        this.movieService = movieService;
        this.jaxbUtils = jaxbUtils;
        this.miningService = miningService;
        this.vendorService = vendorService;
        this.linkRepository = linkRepository;
        this.accountRateMovieRepository = accountRateMovieRepository;
    }

    @GetMapping(value = {INDEX, TRANG_CHU})
    public ModelAndView goHome() {
        return new ModelAndView(ROUTER.get(TRANG_CHU));
    }

    @GetMapping(PHIM_MOI)
    public ModelAndView goNewMovies(HttpServletRequest request) {
        Page<Movie> moviePage = movieService.findTopNewMovies(1, 12);
        int totalPages = moviePage.getTotalPages();
        request.setAttribute("TOTAL_PAGES", totalPages);

        return new ModelAndView(ROUTER.get(PHIM_MOI));
    }

    @GetMapping(SAP_RA_MAT)
    public ModelAndView goComingMovies(HttpServletRequest request) {
        Page<Movie> moviePage = movieService.findIsComingMovies(1, 12);
        int totalPages = moviePage.getTotalPages();
        request.setAttribute("TOTAL_PAGES", totalPages);

        return new ModelAndView(ROUTER.get(SAP_RA_MAT));
    }

    @GetMapping(DANH_CHO_BAN)
    public ModelAndView goRecommended(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("USER");
        if (account != null) {
            List<Movie> movieList;

            List<AccountRateMovie> ratings = accountRateMovieRepository.findByAccountUsername(account.getUsername());

            if (ratings.isEmpty()) {
                return new ModelAndView("errors/no-rating");
            }

            if (AccountRecommendationCache.getRecommendationMap()
                                          .containsKey(account.getUsername())) {
                movieList = AccountRecommendationCache.getRecommendationMap()
                                                      .get(account.getUsername());
            } else {
                movieList = miningService.getRecommendationForUser(account);
                AccountRecommendationCache.cache(account.getUsername(), movieList);
            }
            PagedListHolder<Movie> moviePage = movieService.getPageFromList(1, 12, movieList);
            int totalPages = moviePage.getPageCount();
            request.setAttribute("TOTAL_PAGES", totalPages);
        }

        return new ModelAndView(ROUTER.get(DANH_CHO_BAN));
    }

    @GetMapping(TIM_KIEM)
    public ModelAndView goSearch(HttpServletRequest request) {
        String searchValue = request.getParameter("searchValue");

        Page<Movie> moviePage = movieService.searchMoviesByTitle(1, 12, searchValue);

        int totalPages = moviePage.getTotalPages();
        request.setAttribute("TOTAL_PAGES", totalPages);
        request.setAttribute("SEARCH_VALUE", searchValue);

        return new ModelAndView(ROUTER.get(TIM_KIEM));
    }

    @GetMapping(CHI_TIET + "/{id}")
    public ModelAndView goDetail(HttpServletRequest request,
                                 @PathVariable String id) {
        try {
            movieService.visit(Integer.parseInt(id));
            Movie movie = movieService.findById(Integer.parseInt(id));
            if (movie != null) {
                String movieXml = jaxbUtils.marshall(movie);
                request.setAttribute("MOVIE", movieXml);
            }
        } catch (JAXBException | NumberFormatException e) {
            log.info(this.getClass().getSimpleName() + "_" + e.getClass().getSimpleName());
        }
        return new ModelAndView(ROUTER.get(CHI_TIET));
    }

    @GetMapping(DANG_NHAP)
    public ModelAndView goSignIn() {
        return new ModelAndView(ROUTER.get(DANG_NHAP));
    }

    @GetMapping(DANG_KY)
    public ModelAndView goSignUp() {
        return new ModelAndView(ROUTER.get(DANG_KY));
    }

    @GetMapping(DANG_XUAT)
    public ModelAndView signOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("USER", null);

        return new ModelAndView(ROUTER.get(DANG_XUAT));
    }

    @GetMapping(CRAWLER_SWITCH)
    public ModelAndView goCrawlerSwitch(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("USER");
        if (account != null && account.getRole() == RoleConstants.ADMIN) {
            session.setAttribute("CRAWLING", CrawlService.isCrawling);
            return new ModelAndView(ROUTER.get(CRAWLER_SWITCH));
        } else {
            return new ModelAndView("/errors/unauthorized");
        }
    }

    @GetMapping(VENDOR_MANAGER)
    public ModelAndView goVendorManager(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("USER");
        if (account != null && account.getRole() == RoleConstants.ADMIN) {
            List<Vendor> vendorList = vendorService.findAll();

            Vendors vendors = Vendors.builder()
                                     .vendor(vendorList)
                                     .build();
            try {
                String vendorsXml = jaxbUtils.marshall(vendors);
                request.setAttribute("VENDORS", vendorsXml);
            } catch (JAXBException e) {
                log.info(this.getClass().getSimpleName() + "_" + e.getClass().getSimpleName());
            }
            return new ModelAndView(ROUTER.get(VENDOR_MANAGER));
        } else {
            return new ModelAndView("/errors/unauthorized");
        }
    }

    @GetMapping(COMING_PREDICTION)
    public void generatePredictionReport(HttpServletRequest request,
                                         HttpServletResponse response)
            throws JAXBException,
                   TransformerException,
                   SAXException,
                   IOException, ServletException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("USER");

        if (account != null
            && account.getRole() == RoleConstants.VENDOR) {

            Vendor vendor = vendorService.findById(account.getVendorId());
            List<Link> links = linkRepository.findBySourceLike(vendor.getName());

            if (links.isEmpty()) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("errors/no-movies-to-report");
                requestDispatcher.forward(request, response);
            } else {
                Report report = vendorService.getPredictionReport(account.getVendorId());

                String xmlString = jaxbUtils.marshall(report);
                StreamSource xmlStreamSource = new StreamSource(new StringReader(xmlString));
                String path = request.getServletContext().getRealPath("/");
                byte[] content = vendorService.generatePdf(path, xmlStreamSource);
                response.setContentType("application/pdf");
                response.setContentLength(content.length);
                response.getOutputStream().write(content);
                response.getOutputStream().flush();
            }
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("errors/unauthorized");
            requestDispatcher.forward(request, response);
        }
    }

    @GetMapping(RANKING_REPORT)
    public void generateRankingReport(HttpServletRequest request,
                                      HttpServletResponse response)
            throws JAXBException,
                   TransformerException,
                   SAXException,
                   IOException, ServletException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("USER");

        if (account != null
            && account.getRole() == RoleConstants.VENDOR) {

            Vendor vendor = vendorService.findById(account.getVendorId());
            List<Link> links = linkRepository.findBySourceLike(vendor.getName());

            if (links.isEmpty()) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("errors/no-movies-to-report");
                requestDispatcher.forward(request, response);
            } else {
                Report report = vendorService.getRankingReport(account.getVendorId());

                String xmlString = jaxbUtils.marshall(report);
                StreamSource xmlStreamSource = new StreamSource(new StringReader(xmlString));
                byte[] content = vendorService.generatePdf("", xmlStreamSource);
                response.setContentType("application/pdf");
                response.setContentLength(content.length);
                response.getOutputStream().write(content);
                response.getOutputStream().flush();
            }

        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("errors/unauthorized");
            requestDispatcher.forward(request, response);
        }
    }
}
