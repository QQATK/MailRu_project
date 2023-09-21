package sileniumTest.mailTest;

import automation.PropertyLoader;
import automation.pageObjects.mail.BoxPageObject;
import automation.pageObjects.mail.MailHomePageObject;

import automation.pageObjects.mail.MailViewPageObject;
import io.qameta.allure.Step;
import org.junit.Test;
import sileniumTest.BaseDriverClass;

import java.util.Random;


public class MailTest extends BaseDriverClass {

    static private final String mailSubject = "Mail subject test";
    static private final String mailText = "Hello world!";

    @Test
    public void mailTest(){
        String mailUrlHome = PropertyLoader.loadProperty("mailHomeUrl");
        String login = PropertyLoader.loadProperty("login");
        String password = PropertyLoader.loadProperty("password");

        signIn(mailUrlHome, login, password);
        createNewMail();
        fillMailData(login, mailSubject, mailText);
        sendMail();
        closeSentMessageFrame();
        goToMyselfMail();
        openLastRecievedMail();
        checkMailSubjectAndText();

        GeneralSettingsTest st = new GeneralSettingsTest();
        st.goToGeneralSettings();

        String newSignName = "Имя отправителя №" + new Random().nextInt(100);
        String newSignText = "This is new sign text" + new Random().nextInt(100);
        st.editDefaultSign(newSignName, newSignText);

        goToInboxPage();
        createNewMail();
        checkNewMailSign(newSignText);

    }

    /**
     * Открыть страницу почтового ящика (после авторизации)
     */
    @Step
    public void goToInboxPage(){
        String inboxUrl = PropertyLoader.loadProperty("inboxUrl");
        openUrl(inboxUrl);
    }

    /**
     * Ввести логин пароль, авторизоваться и перейти в ящик.
     * Шаг 1-2.
     */
    @Step
    public void signIn(final String mailUrlHome,
                       final String login,
                       final String password){
        MailHomePageObject homePage = new MailHomePageObject(getDriver());
        openUrl(mailUrlHome);
        homePage
                .clickSignIn()
                .switchToSignInIframe()
                .enterLogin(login)
                .clickToEnterPassword()
                .enterPassword(password)
                .submitSignIn()
                .assertSucceededSignIn(login);
    }

    /**
     * Нажать на кнопку создания нового письма.
     * Шаг 3-4.
     */
    @Step
    public void createNewMail(){
        BoxPageObject boxPage = new BoxPageObject(getDriver());
        boxPage
                .clickToCreateMessageButton()
                .assertCreatingMessageFormIsOpen();
    }

    /**
     * Заполнить поля Кому, Тема, Текст письма. Шаг 5.1.
     * @param addressee Адресат письма, поле "Кому"
     * @param subject Тема письма
     * @param mailText Текст письма
     */
    @Step
    public void fillMailData(final String addressee,
                             final String subject,
                             final String mailText){

        BoxPageObject boxPage = new BoxPageObject(getDriver());
        boxPage
                .setNewMailContact(addressee)
                .setNewMailSubject(subject)
                .setNewMailText(mailText);
    }

    /**
     * Нажать на кнопку создания нового письма.
     * Шаг 5.2.
     */
    @Step
    public void sendMail(){
        BoxPageObject boxPage = new BoxPageObject(getDriver());
        boxPage
                .sendNewMail()
                .assertSuccessSent();
    }

    /**
     * Нажать на кнопку создания нового письма.
     * Шаг 5.2.
     */
    @Step
    public void closeSentMessageFrame(){
        BoxPageObject boxPage = new BoxPageObject(getDriver());
        boxPage
                .closeSuccessMailFrame();
    }

    /**
     * Нажать на кнопку "Входящие" и проверяем, что отправленное на 5 шаге письмо пришло к нам.
     * Шаг 6
     */
    @Step
    public void goToMyselfMail(){
        BoxPageObject boxPage = new BoxPageObject(getDriver());
        boxPage
                .goToMyselfMail()
                .assertLastRecievedMailSubject(mailSubject);
    }

    /**
     * Открываем последнее полученное письмо
     * Шаг 7.1
     */
    @Step
    public void openLastRecievedMail(){
        BoxPageObject boxPage = new BoxPageObject(getDriver());
        boxPage
                .openMailToVeiw();
    }

    /**
     * Проверяем тему и текст письма
     * Шаг 7.2
     */
    @Step
    public void checkMailSubjectAndText(){
        MailViewPageObject mailViewPage = new MailViewPageObject(getDriver());
        mailViewPage
                .assertMailSubject(mailSubject)
                .assertMailText(mailText);
    }

    /**
     * Проверяем, что подпись при создании письма соответствует ожидаемому.
     * Проверяется текст подписи.
     * @param expectedSignText ожидаемый текст подписи
     */
    @Step
    public void checkNewMailSign(final String expectedSignText){
        BoxPageObject boxPage = new BoxPageObject(getDriver());
        boxPage
                .assertNewMailSign(expectedSignText);
    }



}
