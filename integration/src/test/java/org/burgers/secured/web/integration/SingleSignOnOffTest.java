package org.burgers.secured.web.integration;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SingleSignOnOffTest {
    private WebClient webClient;
    private HtmlPage page;

    @Before
    public void setUp() throws GeneralSecurityException {
        webClient = new WebClient();
        webClient.setUseInsecureSSL(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
    }

    @Test
    public void singleSignIn() throws IOException {
        page = webClient.getPage(IntegrationConstants.WEB_BASE_URL);

        andSeesLoginPage();

        page.getElementById("username").setAttribute("value", "p");
        page.getElementById("password").setAttribute("value", "p");

        page = page.getElementByName("submit").click();
        andSeesPage("Main");

        page = webClient.getPage(IntegrationConstants.OTHER_WEB_BASE_URL);

        andSeesPage("Other Main");
    }

    @Test
    public void singleSignOut() throws IOException {
        singleSignIn();

        logOut();
        andSeesLogoutPage();

        page = webClient.getPage(IntegrationConstants.WEB_BASE_URL);
        andSeesLoginPage();

        page = webClient.getPage(IntegrationConstants.OTHER_WEB_BASE_URL);
        andSeesLoginPage();
    }

    private void andSeesPage(String pageName) {
        assertEquals(pageName, page.getTitleText());
    }

    private void andSeesLoginPage() {
        assertTrue(page.asXml().contains("LOGIN"));
    }

    private void andSeesLogoutPage() {
        assertTrue(page.asXml().contains("Logout"));
    }

    private void logOut() throws IOException {
        page = webClient.getPage(IntegrationConstants.AUTHENTICATION_BASE_URL + "/logout");
    }

}
