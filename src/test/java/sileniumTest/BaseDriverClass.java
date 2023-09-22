package sileniumTest;

import lombok.Data;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Data
public class BaseDriverClass {

    @Rule
    public TestWatcher watcher;
    protected WebDriver driver;


    /**
     * Создание нового драйвера
     * @return новый драйвер
     */
    public  WebDriver createDriver() {
        System.setProperty("webdriver.chrome.driver", "D:\\Program Files (x86)\\IntelliJ IDEA 2022.1.2\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        return driver;
    }

    /**
     * Перейти на страницу.
     * @param url адрес
     */
    public void openUrl(String url){
        getDriver().get(url);
    }


    {
        watcher = new TestWatcher() {
            @Override
            protected void starting(Description description) {
                System.out.println("Начат тест-кейс: " + description);
                ChromeOptions chOpt = new ChromeOptions();
                chOpt.addArguments("--remote-allow-origins=*");

                URL url;
                try {
                    url = new URL("http://localhost:9515");
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }

                driver = new RemoteWebDriver(url, chOpt);
                driver.manage().window().maximize();
            }

            @Override
            protected void succeeded(Description description) {
                System.out.println("Тест-кейс пройден");
            }

            @Override
            protected void failed(Throwable e, Description description) {
            }

            @Override
            protected void finished(Description description) {
                driver.quit();
                if (driver != null){
                    driver.quit();
                }
            }
        };
    }
}