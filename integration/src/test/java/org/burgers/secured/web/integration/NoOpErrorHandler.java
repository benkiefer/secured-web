package org.burgers.secured.web.integration;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

public class NoOpErrorHandler implements ErrorHandler {
    @Override
    public void warning(CSSParseException e) throws CSSException { }

    @Override
    public void error(CSSParseException e) throws CSSException { }

    @Override
    public void fatalError(CSSParseException e) throws CSSException { }
}
