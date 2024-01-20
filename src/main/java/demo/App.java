package demo;
import java.net.MalformedURLException;


public class App {
    public void getGreeting() throws InterruptedException, MalformedURLException {
        
        // This is to remove unnecessary warnings from your console
        System.setProperty("java.util.logging.config.file", "logging.properties");
        
        TestCases tests = new TestCases(); // Initialize your test class

        //TODO: call your test case functions one after other here

       tests.testCase01();
       tests.testCase02_Verify_Calendar_Navigation_and_Add_Task();
       tests.testCase03_Verify_The_Task_Updation();
       tests.testCase04_Verify_The_Task_deletion();

        //END Tests


        tests.endTest(); // End your test by clearning connections and closing browser
    }

    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        new App().getGreeting();
    }
}
