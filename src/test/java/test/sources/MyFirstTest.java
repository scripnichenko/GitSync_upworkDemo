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
    private static final String LOGINMAIL = "andrii.skrypnychenko@ukr.net";
    private static final String PASSWORD = "Adrianu2015";
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
    public  void Deleteservice(){

        WebElement deleteFirstService = FindElementBy(By.xpath("//*[@id=\"edit_product_advanced_container\"]"));
        if (deleteFirstService!= null) {
            deleteFirstService.click();
        }
        else {
            fail("There is no button to create Delete button");
        }


        WebElement deleteFirst1Services = FindElementBy(By.xpath("//*[@id=\"product_delete\"]/input"));
        deleteFirst1Services.submit();
    }
    public void SaveScreenshot() {
        String paths = new StringBuilder("./screenshots/").append(MyFirstTest.driver.getTitle()).append(".png").toString();
        File screenshot = ((TakesScreenshot) MyFirstTest.driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(paths));
        }
        catch (IOException e) {
            fail("Unable to save the screenshot to '" + paths + "'");
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

         SaveScreenshot();

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

        //String path2 = new StringBuilder("./screenshots/").append(MyFirstTest.driver.getTitle()).append(".png").toString();
        SaveScreenshot();

        //2 - Navigate to Categories and Create a service keeping all the default values

        //WebElement createService = FindElementBy(By.xpath(MyFirstTest.CREATESERVICE));
        WebElement navigateToCategories = FindElementBy(By.partialLinkText("Services"));
        navigateToCategories.click();

        //Qty validation (not more 10)
        List<WebElement> serviceQty = driver.findElements(By.xpath("//tr"));
        if (serviceQty.size()> 9){
        WebElement firstService = FindElementBy(By.xpath("//tr[1]/td[2]/a"));
        firstService.click();
        Deleteservice();

        }
        // ----- End validation

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

        SaveScreenshot();

        //3 After the service is created, scroll down until you see the "discount options"

        JavascriptExecutor jse = (JavascriptExecutor)MyFirstTest.driver;
        jse.executeScript("window.scrollBy(0,1000)", "");

       // WebElement createDiscount = FindElementBy(By.xpath("//*[@id=\"edit_product_discounts_container\"]/div[1]/a/div"));
        WebElement createDiscount = FindElementBy(By.xpath("//*[contains(text(),'Pricing & discounts')]"));

        if (createDiscount!= null) {
            createDiscount.click();
        }
        else {
            fail("There is no button to create Discount");
        }

         SaveScreenshot();

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

        WebDriverWait wait13 = new WebDriverWait(driver,10);
        wait13.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("product_preview"));

       //System.out.println(driver.getPageSource());
        //System.out.println(driver.getPageSource().compareTo("arrow next"));
       // MyFirstTest.driver.switchTo().frame(driver.findElement(By.xpath("//*[contains(@class,'arrow next')]")));

      /*  WebDriverWait wait = new WebDriverWait(driver, 100000000);
        SaveScreenshot();

        driver.manage().window().maximize();
        WebElement clickArrauNextMonth = driver.findElement(By.xpath("//*[contains(@class,'arrow next')]/span"));
        clickArrauNextMonth.click();
       */

        //WebElement clickArrauNextMonth = driver.findElement(By.xpath("/html/body/div[2]/form/div[2]/div/div/a[2]/span"));
       // driver.findElement(By.xpath("//a[@onclick='showLoading(\"next month click\");']")).click();
        //driver.findElement(By.xpath(" //a[contains(@class,'arrow next')]")).click();

//--------------
        //WA from https://code.google.com/p/chromedriver/issues/detail?id=28


     //   WebDriverWait wait = new WebDriverWait(driver, 100);
    //    WebElement elem = driver.findElement(By.xpath("//a[@onclick='showLoading(\"next month click\");']"));
       // ((JavascriptExecutor) driver).executeScript("contentWindow.document.getElementsByClassName('arrow next')[0].click()");
         Thread.sleep(2000);

  //      ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('arrow next')[0].click()");
        ((JavascriptExecutor) driver).executeScript("document.getElementById('product_preview').contentWindow.document.getElementsByClassName('arrow next')[0].click()");

//        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('arrow next')[0].click()");
//        ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName('arrow next')[0].click()");

//a[@onclick='showLoading("next month click");']

        /*
        WebElement elem1 = driver.findElement(By.xpath("//*[contains(@class,'arrow next')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem1);
        WebElement elem2 = driver.findElement(By.xpath("//*[contains(@class,'arrow next')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem2);
        */
//--------------
/* WA from https://code.google.com/p/selenium/issues/detail?id=2766 Find an element and define it
        WebElement elementToClick = driver.findElement(By.xpath("//*[contains(@class,'arrow next')]"));
// Scroll the browser to the element's Y position
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+elementToClick.getLocation().y + ")");
// Click the element
        elementToClick.click();
*/
        //wait.until(ExpectedConditions.elementToBeClickable(clickArrauNextMonth));
        //WebElement clickArrauNextMonthCss = FindElementBy(By.cssSelector("#eventCalendarDefault > div.eventsCalendar - slider > a.arrow.next > span"));
      //  System.out.println(driver.getPageSource());
       // System.out.print(clickArrauNextMonth.getTagName());
       // clickArrauNextMonth.click();
        //clickArrauNextMonth.click();

        //System.out.println(driver.getPageSource());




//        MyFirstTest.driver.switchTo().frame("product_preview");

//        new Actions(MyFirstTest.driver).moveToElement(clickArrauNextMonth).click().perform();

            // xpath- $x ("//@class='arrow next'")
		// xpath $x ("//*[contains(@class,'arrow next')]")
        // //*[@id="eventCalendarDefault"]/div[1]/a[2]/span
       // WebElement clickArrauNextMonth = FindElementBy(By.cssSelector("*[class$='next']"));
        MyFirstTest.driver.switchTo().defaultContent();
        WebDriverWait wait14 = new WebDriverWait(driver,10);
        wait14.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("product_preview"));
        Thread.sleep(1200);


        List<WebElement> calendarEmptyValuesAmount = driver.findElements(By.xpath("//*[contains(@class,'empty')]"));

            if (calendarEmptyValuesAmount.size() == 0){


            MyFirstTest.driver.findElement(By.xpath("//*[@id=\"dayList_8\"]")).click();
			        } else {


            String xpathForDate = new StringBuilder("//*[@id=\"dayList_").append(14 - calendarEmptyValuesAmount.size() + 1).append("\"]").toString();

            WebDriverWait waitForDate = new WebDriverWait(driver, 10);
            waitForDate.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathForDate)));
                WebElement ckickADay = FindElementBy(By.xpath(xpathForDate));
                driver.findElement(By.xpath(xpathForDate)).click();
                new Actions(MyFirstTest.driver).moveToElement(ckickADay).click().perform();

            }

        //- 6 Click on the time 11:00
        MyFirstTest.driver.switchTo().defaultContent();
        WebDriverWait wait145 = new WebDriverWait(driver,10);
        wait145.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("product_preview"));
        Thread.sleep(1200);
//        WebElement click2300 = driver.findElement(By.xpath("//input[contains(@value, '11:00') and contains(@class, 'select')]"));
        WebElement click2300 = driver.findElement(By.xpath("//*[contains(@onclick, \"11:00\")]"));

        click2300.click();
        new Actions(MyFirstTest.driver).moveToElement(click2300).click().perform();

        //- 7 After clicking on the time the widget will show the price applicable to that date and time.
        // There will be two tickets (adult and child) with the full price crossed and the deducted price besides.
        // You have to make 4 assert to make sure the prices are correct.


        WebElement adultVerification = FindElementBy(By.xpath("//div[@class='booking-box-ticket-type']/table/tbody/tr/td[1]/div"));
        WebDriverWait waitStringAdult = new WebDriverWait(driver, 10);
        waitStringAdult.until(ExpectedConditions.textToBePresentInElement(adultVerification, "Adult"));
        System.out.println("Ticket type [1]: " + adultVerification.getText());
        System.out.println("Ticket OldPrice [1]: " + FindElementBy(By.xpath("//div[@class='booking-box-ticket-type']/table/tbody/tr/td[2]/span[contains(@id, 'oldprice')]")).getText());
        System.out.println("Ticket CurrentPrice [1]: " + FindElementBy(By.xpath("//div[@class='booking-box-ticket-type']/table/tbody/tr/td[2]/span[contains(@id, '_price')]")).getText());


        Select findSelectionAdult = new Select(FindElementBy(By.xpath("//div[@class='booking-box-ticket-type']/table/tbody/tr/td[3]/select [contains(@id, 'count')]")));
        findSelectionAdult.selectByVisibleText("1");


        WebElement childVerification = FindElementBy(By.xpath("//div[@class='booking-box-ticket-type']/table/tbody/tr[2]/td[1]/div"));
        WebDriverWait waitStringChild = new WebDriverWait(driver, 10);
        waitStringChild.until(ExpectedConditions.textToBePresentInElement(childVerification, "Child"));
        System.out.println("Ticket type [2]: " + childVerification.getText());
        System.out.println("Ticket OldPrice [2]: " + FindElementBy(By.xpath("//div[@class='booking-box-ticket-type']/table/tbody/tr[2]/td[2]/span[contains(@id, 'oldprice')]")).getText());
        System.out.println("Ticket CurrentPrice [2]: " + FindElementBy(By.xpath("//div[@class='booking-box-ticket-type']/table/tbody/tr[2]/td[2]/span[contains(@id, '_price')]")).getText());


        Select findSelectionChild = new Select(FindElementBy(By.xpath("//div[@class='booking-box-ticket-type']/table/tbody/tr[2]/td[3]/select [contains(@id, 'count')]")));
        findSelectionChild.selectByVisibleText("2");

        //Total Prices
        WebElement oldTotalPriceVerification = FindElementBy(By.xpath("//*[@id=\"total_price_old\"]"));
        System.out.println("Old Total Price: " + oldTotalPriceVerification.getText());

        WebElement totalPriceVerification = FindElementBy(By.xpath("//*[@id=\"total_price\"]"));
        System.out.println("Total Price: " + totalPriceVerification.getText());


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

        Deleteservice();
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

