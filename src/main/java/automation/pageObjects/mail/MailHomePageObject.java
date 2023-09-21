package automation.pageObjects.mail;

import automation.steps.Base;
import io.qameta.allure.Step;
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
     * Ввести логин пароль, авторизоваться и перейти в ящик.
     */
    @Step
    public void signIn(final String mailUrlHome,
                       final String login,
                       final String password) {

        // Нажать кнопку "Войти" на стартовой странице.
        click(signInButton);

        // Переключиться на iframe ввода логина и пароля.
        // Необходимо выполнить перед тем, как вводить логин и пароль.
        waitUntilElementVisible(loginIframe);
        driver.switchTo().frame(loginIframe);

        // Заполнить поле ввода логина при входе.
        waitUntilElementVisible(usernameInput);
        usernameInput.sendKeys(login);

        // Нажать кнопку "Войти" после ввода логина, чтобы перейти к вводу пароля.
        click(enterPasswordNextButton);

        // Заполнить поле ввода пароля при входе.
        waitUntilElementVisible(passwordInput);
        passwordInput.sendKeys(password);

        // Нажать кнопку "Войти" для подтверждения логина и пароля и входа в почту.
        click(submitSignInButton);

        // Проверка успешного входа - справа вверху отображается аккаунт
        String xpath = "//div[@aria-label='" + login + "']";
        Assert.assertTrue(
                waitUntilElementVisible(xpath));
    }



}
