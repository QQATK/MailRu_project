package automation.steps;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Data
public class Base {

    protected WebDriver driver;

    public Base(final WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }


    /**
     * Явное ожидание видимости элемента на форме
     * @param element экземпляр WebElement, появление которого ожидаем
     * @return WebElement
     */
    public boolean waitUntilElementVisible(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Явное ожидание видимости элемента на форме
     * @param xpath локатор элемента
     * @return WebElement
     */
    public boolean waitUntilElementVisible(String xpath){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Явное ожидание перехода на определенную страницу
     * @param url адрес страницы, переход на которую ожидается
     * @return WebElement
     */
    public boolean waitUntilUrlToBe(String url){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        try {
            wait.until(ExpectedConditions.urlToBe(url));
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public void click(WebElement element){
        waitUntilElementVisible(element);
        element.click();
    }

    public void setText(WebElement element, String txt){
        waitUntilElementVisible(element);
        element.sendKeys(txt);
    }

}
