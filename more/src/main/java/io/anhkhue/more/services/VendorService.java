package io.anhkhue.more.services;

import io.anhkhue.more.mining.cache.VendorReportCache;
import io.anhkhue.more.models.custom.VendorWrapper;
import io.anhkhue.more.models.dto.Account;
import io.anhkhue.more.models.dto.Movie;
import io.anhkhue.more.models.mining.report.Movies;
import io.anhkhue.more.models.mining.report.Report;
import io.anhkhue.more.models.mining.report.Vendor;
import io.anhkhue.more.repositories.VendorRepository;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorService {

    private String XSL_FO_SOURCES_PATH = "static/xsl/movie-prediction.xsl";
    private String FOP_CONFIG_PATH = "static/xsl/fop.xconf";

    private final VendorRepository vendorRepository;

    private final AccountService accountService;

    private final MiningService miningService;

    public VendorService(VendorRepository vendorRepository,
                         AccountService accountService,
                         MiningService miningService) {
        this.vendorRepository = vendorRepository;
        this.accountService = accountService;
        this.miningService = miningService;
    }

    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    public Vendor findById(int id) {
        return vendorRepository.findById(id).orElse(null);
    }

    @Transactional
    public int addVendor(VendorWrapper vendorWrapper) {
        String[] name = vendorWrapper.getName().split(" ");
        String firstName = name[0];
        String lastName = String.join(" ", Arrays.copyOfRange(name, 1, name.length));

        Account account = Account.builder()
                                 .username(vendorWrapper.getUsername())
                                 .password(vendorWrapper.getPassword())
                                 .firstName(firstName)
                                 .lastName(lastName)
                                 .role(2)
                                 .build();

        Vendor vendor = Vendor.builder()
                              .name(vendorWrapper.getName())
                              .address1(vendorWrapper.getAddress1())
                              .address2(vendorWrapper.getAddress2())
                              .email(vendorWrapper.getEmail())
                              .image("")
                              .tel(vendorWrapper.getTel())
                              .build();
        vendorRepository.save(vendor);
        vendorRepository.flush();

        int newId = vendor.getId();

        account.setVendorId(newId);
        int status = accountService.signUp(account);

        return status;
    }

    public byte[] generatePdf(StreamSource streamSource) throws SAXException, TransformerException, IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            File xsltFile = new ClassPathResource(XSL_FO_SOURCES_PATH).getFile();

            FopFactory fopFactory = FopFactory.newInstance();
            fopFactory.setUserConfig(new ClassPathResource(FOP_CONFIG_PATH).getFile().getAbsolutePath());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            Result result = new SAXResult(fop.getDefaultHandler());

            transformer.transform(streamSource, result);
            return out.toByteArray();
        }
    }

    public Report getPredictionReport(int vendorId) {
        YearMonth currentYearMonth = YearMonth.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        String reportDate = currentYearMonth.format(formatter);

        String reportKey = "coming-" + reportDate;

        Report report = null;

        report = getReportFromCache(vendorId, reportKey);

        if (report == null) {
            Vendor vendor = this.findById(vendorId);
            List<Movie> movieList = miningService.getRankedPredictionForComingMovies(vendor.getName());

            List<io.anhkhue.more.models.mining.report.Movie> reportMovies =
                    movieList.stream()
                             .map(movie -> io.anhkhue.more.models.mining.report.Movie
                                     .builder()
                                     // Uppercase first letter
                                     .title(movie.getTitle().substring(0, 1).toUpperCase() + movie.getTitle().substring(1))
                                     .director(movie.getDirector())
                                     .year(movie.getYear())
                                     .build())
                             .collect(Collectors.toList());


            io.anhkhue.more.models.mining.report.Movies movies = Movies.builder()
                                                                       .movie(reportMovies)
                                                                       .build();

            report = Report.builder()
                           .movies(movies)
                           .vendor(vendor)
                           .date(reportDate)
                           .logo("images/Logo_K.png")
                           .type("sắp chiếu")
                           .build();

            VendorReportCache.cache(vendorId, reportKey, report);
        }

        return report;
    }

    public Report getRankingReport(int vendorId) {
        YearMonth currentYearMonth = YearMonth.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        String reportDate = currentYearMonth.format(formatter);

        String reportKey = "showing-" + reportDate;

        Report report = null;

        report = getReportFromCache(vendorId, reportKey);

        if (report == null) {
            Vendor vendor = this.findById(vendorId);
            List<Movie> movieList = miningService.getRankingForShowingMovies(vendor.getName());

            List<io.anhkhue.more.models.mining.report.Movie> reportMovies =
                    movieList.stream()
                             .map(movie -> io.anhkhue.more.models.mining.report.Movie
                                     .builder()
                                     // Uppercase first letter
                                     .title(movie.getTitle().substring(0, 1).toUpperCase() + movie.getTitle().substring(1))
                                     .director(movie.getDirector())
                                     .year(movie.getYear())
                                     .build())
                             .collect(Collectors.toList());

            io.anhkhue.more.models.mining.report.Movies movies = Movies.builder()
                                                                       .movie(reportMovies)
                                                                       .build();

            report = Report.builder()
                           .movies(movies)
                           .vendor(vendor)
                           .date(reportDate)
                           .logo("images/Logo_K.png")
                           .type("đang chiếu")
                           .build();

            VendorReportCache.cache(vendorId, reportKey, report);
        }

        return report;
    }

    private Report getReportFromCache(Integer vendorId, String reportKey) {
        if (VendorReportCache.getReportCachedMap().containsKey(vendorId)) {
            if (VendorReportCache.getReportCachedMap().get(vendorId).containsKey(reportKey)) {
                return VendorReportCache.getReportCachedMap()
                                        .get(vendorId)
                                        .get(reportKey);
            }
        }

        return null;
    }
}
