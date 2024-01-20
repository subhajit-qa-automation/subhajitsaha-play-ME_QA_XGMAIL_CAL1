package demo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;




public class TestCases {
    ChromeDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.INFO);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);


        // Connect to the chrome-window running on debugging port
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        driver.manage().window().maximize();
    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }


    public void testCase01() throws InterruptedException {
        System.out.println("Start Test case: testCase01");
        driver.get("https://calendar.google.com/");
        Thread.sleep(3000);

        String currentUrl = driver.getCurrentUrl();

        if (!currentUrl.contains("calendar")) {
            System.out.println("The URL of the Calendar homepage do not contains \"calendar\"");
        } else {
            System.out.println("The URL of the Calendar homepage contains \"calendar\"");
        }

        System.out.println("end Test case: testCase01");
    }


    public void testCase02_Verify_Calendar_Navigation_and_Add_Task() throws InterruptedException {

        System.out.println("Start Test case: testCase02");
        driver.get("https://calendar.google.com/");
        Thread.sleep(3000);
        WebElement month = driver.findElement(By.xpath("(//span[text()='Month'])[1]"));
        month.click();
        Thread.sleep(3000);
        WebElement selectMonth = driver.findElement(By.xpath("(//span[text()='Month'])[2]"));
        selectMonth.click();
        Thread.sleep(2000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement monthView = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()='Month'])[1]")));
        String actualMonth = monthView.getText();
        String expectedView = "Month";
        if (!actualMonth.equals(expectedView)) {
            System.out.println("Failed switching to the month view");
        } else {
            System.out.println("switching to the month view successfully");
        }

        WebElement clickCal = driver.findElement(By.xpath("(//div[@role='gridcell'])[19]"));
        clickCal.click();
        Thread.sleep(3000);
        WebElement selectTask = driver.findElement(By.xpath("(//button[@id='tabTask']//div)[2]"));
        selectTask.click();
        Thread.sleep(4000);

        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[4]/div[1]/div[1]/div[2]/span[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[2]/label[1]/input[1]")));
            title.clear();
            title.sendKeys("Crio INTV Task Automation");
        } catch (TimeoutException e) {

            System.err.println("Element not found within timeout: " + e.getMessage());
        }

        Thread.sleep(2000);
        WebElement description = driver.findElement(By.xpath("//textarea[@class='VfPpkd-fmcmS-wGMbrd TaTzUd']"));
        description.clear();
        description.sendKeys("Crio INTV Calendar Task Automation");
        Thread.sleep(2000);
        WebElement saveTask = driver.findElement(By.xpath("//button[@data-idom-class='nCP5yc AjY5Oe DuMIQc LQeN7 pEVtpe']//span[1]"));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].click();", saveTask);
        driver.navigate().refresh();
        Thread.sleep(3000);


        //saveTask.click();

    }

    public void testCase03_Verify_The_Task_Updation() throws InterruptedException {

        System.out.println("Start Test case: testCase03");

        if (driver.switchTo().alert() != null) {
            Alert alert = driver.switchTo().alert();

            alert.accept(); // Or alert.dismiss();
        }

        driver.get("https://calendar.google.com/");
        driver.navigate().refresh();

        //driver.get(driver.getCurrentUrl());
        Thread.sleep(4000);
        // Handle potential alert before proceeding
        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();  // Dismiss the alert if it's not relevant
        } catch (NoAlertPresentException e) {
            // Continue execution if no alert is present
        }
//        WebElement dialog = driver.switchTo().activeElement();
//        Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement crio = driver.findElement(By.className("g3dbUc KCIIIb"));
//        crio.click();
        WebElement crioTitle = driver.findElement(By.xpath("//span[text()='Crio INTV Task Automation']"));
        crioTitle.click();
        Thread.sleep(2000);
        WebElement editButton = driver.findElement(By.xpath("(//span[contains(@class,'VfPpkd-kBDsod meh4fc')])[3]"));
        editButton.click();
        Thread.sleep(2000);
        WebElement updateDesc = driver.findElement(By.xpath("//textarea[@class='VfPpkd-fmcmS-wGMbrd vRGQ0d']"));
        updateDesc.clear();
        updateDesc.sendKeys("Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application");
        Thread.sleep(3000);
        WebElement saveTask = driver.findElement(By.xpath("(//div[@class='VfPpkd-RLmnJb']/following-sibling::span)[3]"));
        saveTask.click();
        Thread.sleep(3000);
        WebElement checkTitle = driver.findElement(By.xpath("//span[text()='Crio INTV Task Automation']"));
        checkTitle.click();
        Thread.sleep(3000);

        // WebElement updatedDesc = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toUqff D29CYb")));

        Thread.sleep(3000);
        WebElement updatedDescription = driver.findElement(By.xpath("//div[@class='toUqff D29CYb']"));
        String actualDescription = updatedDescription.getText();
        Thread.sleep(3000);
        String expectedDescription = "Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application";

        if (!actualDescription.contains(expectedDescription)) {
            System.out.println("Task description update verification failed.");
        } else {
            System.out.println("Task description successfully updated and verified.");
        }
    }

    public void testCase04_Verify_The_Task_deletion() throws InterruptedException {

        System.out.println("Start Test case: testCase04");
        driver.get("https://calendar.google.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement existingTask = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Crio INTV Task Automation']")));
        existingTask.click();

        WebElement verifyTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='rAECCd']")));
        String actualTitle = verifyTitle.getText();
        String expectedTitle = "Crio INTV Task Automation";

        if (!actualTitle.equals(expectedTitle)) {
            System.out.println("The title of the task is not verified.");
        } else {
            System.out.println("The title of the task is successfully verified..");
        }

        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='pPTZAe']//button)[2]")));
        deleteButton.click();
        //Thread.sleep(3000);
       // WebElement taskDeleted = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='YrbPvc']//div)[1]")));

//        try {
//      if (!taskDeleted.isDisplayed()) {
//          System.out.println("Task deletion confirmation not found. Deletion may have failed.");
//      } else {
//          System.out.println("Task deleted");
//      }
//        } catch (NoAlertPresentException e) {
//            System.out.println("Task deletion confirmation not found as an alert. Deletion may have failed.");
//        }
//    }
//}

//        try {
//
//            String alertText = driver.switchTo().alert().getText();
//            if (alertText.equals("Task deleted")) {
//                System.out.println("Task deletion confirmation found. Task successfully deleted and verified.");
//            } else {
//                System.out.println("Unexpected alert text: " + alertText);
//            }
//
//            driver.switchTo().alert().accept();
//        } catch (NoAlertPresentException e) {
//            System.out.println("Task deletion confirmation not found. Deletion may have failed.");
//        }
//    }
//}
        try {
            WebElement taskDeleted = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[text()='Task deleted'])[1]")));
            String taskdel = taskDeleted.getText();
            if (taskdel.contains("Task deleted")) {
                System.out.println("Task deleted");
            }
        } catch (TimeoutException e) {
            System.out.println("Task deletion confirmation not found. Deletion may have failed.");
        }

    }
}

