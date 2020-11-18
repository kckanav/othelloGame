import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlCanvas;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.jauntium.Browser;
import com.jauntium.Element;
import com.jauntium.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.internal.EventFiringMouse;

import java.sql.Connection;
import java.util.List;


public class OnlinePlayer {

    public static void main(String args[]) {
//        for (String j = "h"; j.length() < 5; j = j.substring(0, j.length() - 1)) {
//            System.out.println(j);
//        }
//        try {
//            System.setProperty("webdriver.chrome.driver", "C:/Users/cance/Downloads/chromedriver_win32/chromedriver.exe");
//            // Browser browser = new Browser(new ChromeDriver());
//
//            WebDriver driver = new ChromeDriver();//create new browser window
//            driver.get("https://eothello.com");
//            // browser.visit("https://eothello.com");
//            // List<WebElement> allButtons = browser.doc.findElement(By.id("canvas")).findElements(By.tagName("a"));
//            ;//get child text of title element.
//            List<WebElement> btn = driver.findElements(By.cssSelector("p"));
//            System.out.println(btn.size());
//            Point p = driver.findElement(By.id("canvas")).getLocation();
//            System.out.println(p.x + ", " + p.y);
//            Dimension d = driver.findElement(By.id("canvas")).getSize();
//            System.out.println(d.height + ", " + d.width);
//
//            EventFiringMouse mclick = new EventFiringMouse(driver, new AbstractWebDriverEventListener() {
//            });
//
//           // mclick.click(new );
//
//            Coordinates g = new Coordinates() {
//                @Override
//                public Point onScreen() {
//                    return null;
//                }
//
//                @Override
//                public Point inViewPort() {
//                    return null;
//                }
//
//                @Override
//                public Point onPage() {
//                    return null;
//                }
//
//                @Override
//                public Object getAuxiliary() {
//                    return null;
//                }
//            };
//            Actions act = new Actions(driver);
//            driver.findElement(By.className("dropdown-toggle")).click();
////            act.click().build().perform();
//            for (WebElement e: btn) {
//                System.out.println(e.getText());
//                if (e.getText().equals("Register")) {
//                    Point c = e.getLocation();
//                    System.out.println("Found it");
//                    e.click();
//                    System.out.println(c.x + ", " + c.y);
//                    // act.click(e).build().perform();
//                }
//            }
//
////            Actions act = new Actions(driver);
////            for (WebElement e: allButtons) {
////                if (e.getText().equals("d3 empty")) {
////                    System.out.println("Found it");
////                    act.click(e).build().perform();
////                }
////            }
//
//
//
//
//            //System.out.println("Heroku's website title: " + title);          //print the title
//            // System.out.println(browser.doc.outerHTML());         //print the HTML
//            // browser.quit();
//            // homePage();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
