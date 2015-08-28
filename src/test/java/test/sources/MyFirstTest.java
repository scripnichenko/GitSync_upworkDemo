package test.sources;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
            Thread.sleep(1000);
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
    public void webSiteTest() throws InterruptedException {

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

        // 2. Create a service keeping all the default values
        WebElement saveServiceGoToPreview = FindElementBy(By.id("save_and_go_to_preview"));
        if (saveServiceGoToPreview != null) {
            saveServiceGoToPreview.click();
        }
        else {
            fail("There is no button to Save new Service");
        }


        String path4 = new StringBuilder("./screenshots/").append(MyFirstTest.driver.getTitle()).append(".png").toString();
        SaveScreenshot(path4);


        //3 After the service is created, scroll down until you see the "discount options"

        JavascriptExecutor jse = (JavascriptExecutor)MyFirstTest.driver;
        jse.executeScript("window.scrollBy(0,1000)", "");

        WebElement createDiscount = FindElementBy(By.xpath("//*[@id=\"edit_product_discounts_container\"]/div[1]/a/div"));

        if (createDiscount!= null) {
            createDiscount.click();
        }
        else {
            fail("There is no button to create Discount");
        }

        String path5 = new StringBuilder("./screenshots/").append(MyFirstTest.driver.getWindowHandle()).append(".png").toString();
        SaveScreenshot(path5);


        // 4 Create a 10% discount applicable to all ticket types (leave blank all the other conditions)

        WebElement setDiscountValue = FindElementBy(By.xpath("//*[@name=\"discount_create[value]\"]"));
        setDiscountValue.sendKeys("10");


        WebElement CreateDiscountValue = FindElementBy(By.xpath("//*[@class=\"discount submit4\"]"));
        CreateDiscountValue.click();

        //5 Then, scroll up again to see the "Service Preview" (which is an iframe), click on the right arrow to select the following month and click on the second monday
       // JavascriptExecutor jse2 = (JavascriptExecutor)MyFirstTest.driver;
      //  jse2.executeScript("window.scrollTo(0,0)", "");

       // WebElement clickNextMonth = FindElementBy(By.cssSelector("#eventCalendarDefault > div.eventsCalendar-slider > a.arrow.next"));
        //clickNextMonth.click();

        //search frames block
        /*List<WebElement> frameset = driver.findElements(By.tagName("iframe"));
        for (WebElement framename : frameset)

            System.out.println("frameid: " + framename.getAttribute("id"));*/


        JavascriptExecutor jseUp = (JavascriptExecutor)MyFirstTest.driver;
        jseUp.executeScript("window.scrollTo(0,0)", "");

        MyFirstTest.driver.switchTo().frame("product_preview");


            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement clickArrauNextMonth = FindElementBy(By.xpath("//*[contains(@class,'arrow next')]"));
            wait.until(ExpectedConditions.elementToBeClickable(clickArrauNextMonth));

            clickArrauNextMonth.click();

            // xpath- $x ("//@class='arrow next'")
		// xpath $x ("//*[contains(@class,'arrow next')]")
        // //*[@id="eventCalendarDefault"]/div[1]/a[2]/span
       // WebElement clickArrauNextMonth = FindElementBy(By.cssSelector("*[class$='next']"));

        List<WebElement> calendarEmptyValuesAmount = driver.findElements(By.cssSelector("*[class$='empty']"));

        if (calendarEmptyValuesAmount.size() == 0){
            MyFirstTest.driver.findElement(By.xpath("//*[@id=\"dayList_8\"]")).click();
			        } else {
            String xpathForDate = new StringBuilder("//*[@id=\"dayList_").append(14 - calendarEmptyValuesAmount.size() + 1).append("\"]").toString();

            MyFirstTest.driver.findElement(By.xpath(xpathForDate)).click();
        }

        //- 6 Click on the time 11:00

        MyFirstTest.driver.findElement(By.xpath("//*[@id=\"23\"]")).click();

        //- 7 After clicking on the time the widget will show the price applicable to that date and time.
        // There will be two tickets (adult and child) with the full price crossed and the deducted price besides.
        // You have to make 4 assert to make sure the prices are correct.


        WebElement adultSelector = FindElementBy(By.id("ticket_type_count_16035"));
        WebDriverWait waitString = new WebDriverWait(driver, 10);
        waitString.until(ExpectedConditions.elementToBeClickable(adultSelector));

        Select findSelectionAdult = new Select(FindElementBy(By.id("ticket_type_count_16035")));
        findSelectionAdult.selectByVisibleText("1");


        Select findSelectionChild = new Select(FindElementBy(By.id("ticket_type_count_16036")));
        findSelectionChild.selectByVisibleText("2");





       /* WebElement select = driver.findElement(By.id("selection"));
        List<WebElement> options = select.findElements(By.tagName("option"));
        for (WebElement option : options) {
            if("Germany".equals(option.getText()))
                option.click();
        }*/

        //*[@id="ticket_type_count_16094"]
        //*[@id="buy-now-1"]/div[3]/div[2]/table/tbody/tr[1]/td[1]/div

       /* WebElement findButtonnext = FindElementBy());
        Thread.sleep(1000);
        findButtonnext.click();*/

        //Return to default frame
        MyFirstTest.driver.switchTo().defaultContent();

        //*[@id="eventCalendarDefault"]/div[1]/a[2]
        //14 The automated test finishes by deleting the service (scroll down, click on "advance actions" and then on the red button "Delete Service"

        WebElement deleteService = FindElementBy(By.xpath("//*[@id=\"edit_product_advanced_container\"]"));
        if (deleteService!= null) {
            deleteService.click();
        }
        else {
            fail("There is no button to create Delete button");
        }


        String path6 = new StringBuilder("./screenshots/").append(MyFirstTest.driver.getTitle()).append(".png").toString();
        SaveScreenshot(path6);



        WebElement deleteServices = FindElementBy(By.xpath("//*[@id=\"product_delete\"]/input"));
        deleteServices.submit();

        String path7 = new StringBuilder("./screenshots/").append(MyFirstTest.driver.getTitle()).append(".png").toString();
        SaveScreenshot(path7);

        //*[@id="product_discounts"]
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

