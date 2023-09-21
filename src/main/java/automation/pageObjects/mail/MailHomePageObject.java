package automation.pageObjects.mail;

import automation.steps.Base;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;



public class MailHomePageObject extends Base {

    @FindBy(xpath = ".//iframe[@class='ag-popup__frame__layout__iframe']")
    WebElement loginIframe;

    @FindBy(xpath = ".//button[@data-testid='enter-mail-primary']")
    WebElement signInButton;

    @FindBy(xpath = ".//input[@name='username']")
    WebElement usernameInput;

    @FindBy(xpath = ".//button[@data-test-id='next-button']")
    WebElement enterPasswordNextButton;

    @FindBy(xpath = ".//input[@name='password']")
    WebElement passwordInput;

    @FindBy(xpath = ".//button[@data-test-id='submit-button']")
    WebElement submitSignInButton;


    public MailHomePageObject(WebDriver driver) {
        super(driver);
    }

    /**
     * Переключиться на iframe ввода логина и пароля.
     * Необходимо выполнить перед тем, как вводить логин и пароль.
     */
    public MailHomePageObject switchToSignInIframe(){
        waitUntilElementVisible(loginIframe);
        driver.switchTo().frame(loginIframe);
        return this;
    }

    /**
     * Нажать кнопку "Войти" на стартовой странице.
     */
    public MailHomePageObject clickSignIn() {
        click(signInButton);
        return this;
    }

    /**
     * Заполнить поле ввода логина при входе.
     */
    public MailHomePageObject enterLogin(String login)   {
        waitUntilElementVisible(usernameInput);
        usernameInput.sendKeys(login);
        return this;
    }

    /**
     * Заполнить поле ввода пароля при входе.
     */
    public MailHomePageObject enterPassword(String password) {
        waitUntilElementVisible(passwordInput);
        passwordInput.sendKeys(password);
        return this;
    }

    /**
     * Нажать кнопку "Войти" после ввода логина, чтобы перейти к вводу пароля.
     */
    public MailHomePageObject clickToEnterPassword() {
        click(enterPasswordNextButton);
        return this;
    }

    /**
     * Нажать кнопку "Войти" для подтверждения логина и пароля и входа в почту.
     */
    public MailHomePageObject submitSignIn() {
        click(submitSignInButton);
        return this;
    }

    public void assertSucceededSignIn(String login){
        String xpath = "//div[@aria-label='" + login + "']";
        Assert.assertTrue(
                waitUntilElementVisible(xpath));
    }

}
