import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static String credentialsFileName = System.getProperty("user.dir") + "\\credentials.txt";

    private static ArrayList<String> credentials = readFile(credentialsFileName);

    private static String oneLoginUsername = credentials.get(0);
    private static String oneLoginPassword = credentials.get(1);

    public static void main (String[] args) {
        int totalFormSubmissions = 1000;

        System.setProperty ("webdriver.gecko.driver", System.getProperty("user.dir") + "\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();

        driver.get("https://concord.onelogin.com/login");
        driver.findElement(By.id("user_email")).sendKeys(oneLoginUsername, Keys.TAB, oneLoginPassword, Keys.ENTER);

        timeout(5000);

        driver.get("https://accounts.google.com/signin/v2/identifier?hl=en&passive=true&continue=https%3A%2F%2Fwww.google.com%2F%3Fgws_rd%3Dssl%26safe%3Dactive&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
        driver.findElement(By.id("identifierId")).sendKeys("ronit.sinha@concordacademy.org");

        timeout(2000);

        driver.findElement(By.id("identifierNext")).click();

        timeout(2000);

        for (int i = 0; i < totalFormSubmissions; i++) {
            fillForm(driver);
        }

        driver.quit();
    }

    private static ArrayList<String> readFile (String filename) {
        BufferedReader br;
        FileReader fr;

        try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);

            try {
                ArrayList<String> credentials = new ArrayList<String>();

                credentials.add(br.readLine());
                credentials.add(br.readLine());

                return credentials;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void timeout (long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    private static void fillForm(WebDriver driver) {
        driver.get ("http://docs.google.com/forms/d/e/1FAIpQLSftiwEMeQmRtUEzoMzwMNvbirRQtgOTuY-u9d0M4cSmZn0b4Q/viewform?c=0&w=1");

        try {
            driver.findElement(By.id("identifierId")).sendKeys("ronit.sinha@concordacademy.org");

            timeout (500);

            driver.findElement(By.id("identifierNext")).click();

            timeout (2000);

        } catch (NoSuchElementException e) {
            //timeout (1000);

            driver.findElement(By.cssSelector("div[data-value='Hoodie']")).click();
            driver.findElement(By.cssSelector("div[jsname='M2UYVd']")).click();
        }

    }
}
