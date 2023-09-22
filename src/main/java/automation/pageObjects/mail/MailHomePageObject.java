package automation.pageObjects.mail;

import automation.steps.Base;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * Стартовая страница (до входа)
 */
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
    public void signIn(final String login,
                       final String password) {
        clickSignInButton();
        focusLoginPasswordIFrame();
        enterLogin(login);
        clickEnterPasswordNextButton();
        enterPassword(password);
        clickSubmitSignInButton();
        assertSuccessSignIn(login);
    }

    ///// =======================================
    /////       АТОМАРНЫЕ МЕТОДЫ
    ///// =======================================

    /**
     * Нажать кнопку 'Войти' на стартовой странице.
     */
    @Step("Нажать кнопку 'Войти' на стартовой странице.")
    public void clickSignInButton() {
        click(signInButton);
    }

    /**
     * Переключиться на iframe ввода логина и пароля.
     * Необходимо выполнить перед тем, как вводить логин и пароль.
     */
    @Step("Переключиться на iframe ввода логина и пароля")
    public void focusLoginPasswordIFrame() {
        waitUntilElementVisible(loginIframe);
        driver.switchTo().frame(loginIframe);
    }

    /**
     * Заполнить поле ввода логина при входе.
     * @param login Логин для входа.
     */
    @Step("Заполнить поле ввода логина при входе. Логин = {login}")
    public void enterLogin(final String login) {
        setText(usernameInput, login);
    }

    /**
     * Нажать кнопку 'Войти' после ввода логина, чтобы перейти к вводу пароля.
     */
    @Step("Нажать кнопку 'Войти' после ввода логина, чтобы перейти к вводу пароля.")
    public void clickEnterPasswordNextButton() {
        click(enterPasswordNextButton);
    }


    /**
     * Заполнить поле ввода пароля при входе.
     * @param password Пароль для входа
     */
    @Step("Заполнить поле ввода пароля при входе. Пароль = {password}")
    public void enterPassword(final String password) {
        setText(passwordInput, password);
    }


    /**
     * Нажать кнопку "Войти" для подтверждения логина и пароля и входа в почту.
     */
    @Step("Нажать кнопку 'Войти' для подтверждения логина и пароля и входа в почту.")
    public void clickSubmitSignInButton() {
        // Нажать кнопку "Войти" для подтверждения логина и пароля и входа в почту.
        click(submitSignInButton);
    }

    /**
     * Проверка успешного входа - справа вверху должен отображается аккаунт.
     * @param login Логин, под которым производился вход
     */
    @Step("Проверка успешного входа - справа вверху должен отображается аккаунт.")
    public void assertSuccessSignIn(final String login) {
        // Проверка успешного входа - справа вверху отображается аккаунт
        String xpath = "//div[@aria-label='" + login + "']";
        Assert.assertTrue(
                waitUntilElementVisible(xpath));
    }

}
