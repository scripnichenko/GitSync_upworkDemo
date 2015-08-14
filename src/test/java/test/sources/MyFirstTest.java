package test.sources;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.fail;

/**
 * Created by ansk0413 on 13.08.15.
 */
public class MyFirstTest
{
    private static WebDriver driver;
    private static final String LOGINMAIL = "scrip.a.v@gmail.com";
    private static final String PASSWORD = "myhMf6c3";
    private static final String LOGIN = "id('header-nav-link-login')";
    private static final String USERFIELD = "id('user')";
    private static final String PASSFIELD = "id('password')";
    private static final String CREATESERVICE = "id('body')/x:div[1]/x:a[1]/x:span";

    @BeforeClass
    public static void openAndMaximizeBrowser() {
        System.setProperty("webdriver.chrome.driver", "./webdriver/chromedriver.exe");
        MyFirstTest.driver = new ChromeDriver();
        MyFirstTest.driver.manage().window().maximize();
        /**
         * Takes the screenshot of the current page and saves it with the specified path
         *
         * @param path  path to be saved
         * @throws java.io.IOException  if the specified path is invalid
         */
    }
    public void SaveScreenshot(String path) {
        File screenshot = ((TakesScreenshot) MyFirstTest.driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(path));
        }
        catch (IOException e) {
            fail("Unable to save the screenshot to '" + path + "'");
        }
    }

    /**
     * Tries to search for an element by specified method
     *
     * //@param parent  parent element
     * @param by  specified method
     * @return  element pointer if the specified element is not found, otherwise null
     */
    public WebElement FindElementBy(By by) {
        try {
            (new WebDriverWait(MyFirstTest.driver, 5)).until(ExpectedConditions.visibilityOfElementLocated(by));
            WebElement element = MyFirstTest.driver.findElement(by);
            Thread.sleep(1000);
            return element;
        }
        catch (InterruptedException e) {
            return null;
        }
    }

    @Test
    public void webSiteTest() {

        //
        // 1) Log in to your previously created account
        //
        MyFirstTest.driver.get("http://www.turitop.com/");

        //1.1 Navigate to LOGIN pop-up
        WebElement vacancyLink = FindElementBy(By.xpath(MyFirstTest.LOGIN));
        if (vacancyLink == null) {
            fail("User can not LOG IN");
        }
        new Actions(MyFirstTest.driver).moveToElement(vacancyLink).click().perform();

        //1.2 Make ScreenShot

        String path = new StringBuilder("./screenshots/").append(MyFirstTest.driver.getTitle()).append(".png").toString();
        SaveScreenshot(path);

        //1.3 Set Log and pass

        WebElement loggingPass = FindElementBy(By.xpath(MyFirstTest.PASSFIELD));
        if (loggingPass != null) {
            loggingPass.sendKeys(PASSWORD);

        }
        else {
             fail("There is no field to Enter Pass");
        }

        WebElement loggingUser = FindElementBy(By.xpath(MyFirstTest.USERFIELD));
        if (loggingUser != null) {
            loggingUser.sendKeys(LOGINMAIL);
            loggingUser.submit();
        }
        else {
            fail("There is no field to Enter LogIn");
        }

       /* WebElement clickLogin = FindElementBy(By.id("login"));
        clickLogin.click();*/


        //1.4 Validate LogIn

        WebElement loggingVerification = FindElementBy(By.className("admin-menu-logout-link"));
        if (loggingVerification == null) {
           fail("LogIn Fail");
        }

        String path2 = new StringBuilder("./screenshots/").append(MyFirstTest.driver.getTitle()).append(".png").toString();
        SaveScreenshot(path2);

        //2 - Navigate to Categories and Create a service keeping all the default values

        //WebElement createService = FindElementBy(By.xpath(MyFirstTest.CREATESERVICE));
        WebElement navigateToCategories = FindElementBy(By.partialLinkText("Services"));
        navigateToCategories.click();

        WebElement saveService = FindElementBy(By.partialLinkText("Add new service"));
        if (saveService != null) {
            saveService.click();
        }
        else {
            fail("There is no button to Add new Service");
        }

        //2.1 Click Save
        WebElement saveServiceGoToPreview = FindElementBy(By.id("save_and_go_to_preview"));
        if (saveServiceGoToPreview != null) {
            saveServiceGoToPreview.click();
        }
        else {
            fail("There is no button to Save new Service");
        }


        String path4 = new StringBuilder("./screenshots/").append(MyFirstTest.driver.getTitle()).append(".png").toString();
        SaveScreenshot(path4);

       /* //2.2 Click Save
        WebElement saveFinalService = FindElementBy(By.xpath("id('body')/x:div[3]/x:div[4]/x:div/x:input"));
        if (saveFinalService!= null) {
            saveFinalService.click();
        }
        else {
            fail("There is no button to Save new Service");
        }


        String path5 = new StringBuilder("./screenshots/").append(MyFirstTest.driver.getTitle()).append(".png").toString();
        SaveScreenshot(path5);      */


        //2.3 PRICING & DISCOUNTS

        WebElement createDiscount = FindElementBy(By.partialLinkText("Pricing & Discounts"));
        if (createDiscount!= null) {
            createDiscount.click();
        }
        else {
            fail("There is no button to create Discount");
        }

       /* //
        // 2) send "NetCracker Su" to the query field
        //
        WebElement query = findElementBy(By.xpath(MyFirstTest.QUERY_XPATH));
        if (query == null) {
            fail("Unable to find Google query field");
        }
        query.sendKeys("NetCracker Su");

        //
        // 3) click on "NetCracker Sumy" in the suggestion list
        //
        WebElement suggestion = findElementBy(By.xpath(MyFirstTest.NC_SUGGESTION_XPATH));
        if (suggestion != null) {
            new Actions(MyFirstTest.driver).moveToElement(suggestion).click().perform();
        }

        //
        // 4) send "NetCracker Sumy" to the query field, if "NetCracker Sumy" was not found in the suggestion list
        //
        else {
            query.clear();
            query.sendKeys("NetCracker Sumy");
            query.submit();
        }

        //
        // 5) go to the link "���� | NetCracker Technology" in search results
        //
        WebElement siteLink = findElementBy(By.linkText("���� | NetCracker Technology"));
        if (siteLink != null) {
            new Actions(WebSiteTest.driver).moveToElement(siteLink).click().perform();
        }

        //
        // 6) if the specified link was not found in search results, go to url "http://www.netcracker.com/ukr/vacancies"
        //
        else {
            WebSiteTest.driver.get("http://www.netcracker.com/ukr/vacancies");
        }

        //
        // 7) Click on "Sumy" in the block "Vacancies" on the left on the NetCracker web-site
        //
        WebElement vacancyLink = findElementBy(By.xpath(WebSiteTest.VACANCY_LINK_XPATH));
        if (vacancyLink == null) {
            fail("The link 'Sumy' in the block 'Vacancies' is missing on the NetCracker web-site");
        }
        new Actions(WebSiteTest.driver).moveToElement(vacancyLink).click().perform();

        //
        // 8) Take the screenshot of the current page and save it with the name of the page title
        //
        String path = new StringBuilder("./").append(convertString(WebSiteTest.driver.getTitle(), WebSiteTest.REGEX, WebSiteTest.REPLACE)).append(".png").toString();
        saveScreenshot(path);

        //
        // 9) get all jobs, group them by departments and store them to HashMap<String, String>
        //
        Map<String, String> actualMap = parseVacanciesToHashMap();

        //
        // 10) Verify in with JUnit, that all vacancies of the site, which stored in HashMap<String, String>, correspond to the expected result
        //
        assertContains(exceptedMap, actualMap);    */
    }


    @AfterClass
    public static void closeBrowser() {
        // Close the Browser window
        MyFirstTest.driver.quit();
    }


}
