package io.anhkhue.more.services;

import io.anhkhue.more.models.custom.VendorWrapper;
import io.anhkhue.more.models.dto.Account;
import io.anhkhue.more.models.mining.report.Vendor;
import io.anhkhue.more.repositories.VendorRepository;
import org.apache.fop.apps.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    private final AccountService accountService;

    public VendorService(VendorRepository vendorRepository, AccountService accountService) {
        this.vendorRepository = vendorRepository;
        this.accountService = accountService;
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

        int status = accountService.signUp(account);

        if (status == AccountService.CREATED) {
            Vendor vendor = Vendor.builder()
                                  .name(vendorWrapper.getName())
                                  .address1(vendorWrapper.getAddress1())
                                  .address2(vendorWrapper.getAddress2())
                                  .email(vendorWrapper.getEmail())
                                  .image(vendorWrapper.getLogo().getName())
                                  .tel(vendorWrapper.getTel())
                                  .build();
            vendorRepository.save(vendor);
            vendorRepository.flush();
            int newId = vendor.getId();

            account.setVendorId(newId);
            accountService.save(account);

            return newId;
        }

        return status;
    }

    public byte[] generatePdf(StreamSource streamSource) throws SAXException, TransformerException, IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            File xsltFile = new File("moviePrediction.xsl");

            FopFactory fopFactory = FopFactory.newInstance();
            fopFactory.setUserConfig("fop.xconf");
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            // That's where the XML is first transformed to XSL-FO and then
            // PDF is created
            transformer.transform(streamSource, res);
            return out.toByteArray();
        }
    }
}
