package io.anhkhue.more.crawlservice.xml.validators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

@Slf4j
public class SchemaValidator {

    public void validate(Object jaxbObj, String schemaPath) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // Set up Schema for validation
            Schema schema = schemaFactory.newSchema(getSchema(schemaPath));
            // Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(jaxbObj.getClass());

            System.out.println("<!--- Generating XML Output --->");
            // Instantiate marshaller
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setSchema(schema);

            // Test write to System.out
            marshaller.marshal(jaxbObj, System.out);
//            marshaller.marshal(jaxbContext, new File(XML_FILE));
        } catch (SAXException | IOException | JAXBException e) {
            log.info(this.getClass().getName() + "_" + e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private File getSchema(String schemaPath) throws IOException {
        return new ClassPathResource(schemaPath).getFile();
    }
}