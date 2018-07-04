package io.anhkhue.more.controllers.routers;

import io.anhkhue.more.crawlers.utils.JaxbUtils;
import io.anhkhue.more.mining.cache.AccountRecommendationCache;
import io.anhkhue.more.models.dto.Account;
import io.anhkhue.more.models.dto.Movie;
import io.anhkhue.more.models.mining.report.Vendor;
import io.anhkhue.more.models.mining.report.Vendors;
import io.anhkhue.more.services.CrawlService;
import io.anhkhue.more.services.MiningService;
import io.anhkhue.more.services.MovieService;
import io.anhkhue.more.services.VendorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import java.util.List;

import static io.anhkhue.more.controllers.routers.PageRouterConstants.ROUTER;
import static io.anhkhue.more.controllers.routers.PageRouterConstants.Url.*;

@Slf4j
@Controller
public class PageRouter {

    private final MovieService movieService;
    private final JaxbUtils jaxbUtils;

    private final MiningService miningService;

    private final VendorService vendorService;

    public PageRouter(MovieService movieService, JaxbUtils jaxbUtils, MiningService miningService, VendorService vendorService) {
        this.movieService = movieService;
        this.jaxbUtils = jaxbUtils;
        this.miningService = miningService;
        this.vendorService = vendorService;
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
            List<Movie> movieList = null;
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
        session.setAttribute("CRAWLING", CrawlService.isCrawling);
        return new ModelAndView(ROUTER.get(CRAWLER_SWITCH));
    }

    @GetMapping(VENDOR_MANAGER)
    public ModelAndView goVendorManager(HttpServletRequest request) {
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
    }
}
