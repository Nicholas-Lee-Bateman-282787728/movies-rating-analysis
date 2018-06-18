package io.anhkhue.more.crawlservice.xml.validators;

import lombok.extern.slf4j.Slf4j;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

@Slf4j
public class ValidationErrorHandler implements ErrorHandler {

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        log.info("Validation Error Handler - WARNING: " + exception.getMessage() + " at " +
                 "line " + exception.getLineNumber() + ", " +
                 "column " + exception.getColumnNumber() + exception.getMessage());
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        log.info("Validation Error Handler - ERROR: " + exception.getMessage() + " at " +
                 "line " + exception.getLineNumber() + ", " +
                 "column " + exception.getColumnNumber() + exception.getMessage());
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        log.info("Validation Error Handler - FATAL ERROR: " + exception.getMessage() + " at " +
                 "line " + exception.getLineNumber() + ", " +
                 "column " + exception.getColumnNumber() + exception.getMessage());
    }
}
