package sileniumTest.mailTest;

import automation.PropertyLoader;
import automation.pageObjects.mail.BoxPageObject;
import automation.pageObjects.mail.GeneralSettingsPageObject;
import automation.pageObjects.mail.MailHomePageObject;

import automation.pageObjects.mail.MailViewPageObject;
import io.qameta.allure.Description;
import org.junit.Test;
import sileniumTest.BaseDriverClass;

import java.util.Random;


public class MailTest extends BaseDriverClass {

    static private final String mailSubject1 = "Delete this mail 1";
    static private final String mailSubject2 = "Delete this mail 2";
    static private final String mailText = "mailText";

    static private final String mailUrlHome = PropertyLoader.loadProperty("mailHomeUrl");
    static private final String inboxUrl = PropertyLoader.loadProperty("inboxUrl");
    static private final String generalSettingsUrl = PropertyLoader.loadProperty("generalSettingsUrl");

    static private final String login = PropertyLoader.loadProperty("login");
    static private final String password = PropertyLoader.loadProperty("password");


    @Test
    @Description("Тест кейс для автоматизации mail.ru")
    public void mailTest() {

        MailHomePageObject homePage = new MailHomePageObject(getDriver());
        BoxPageObject boxPage = new BoxPageObject(getDriver());
        GeneralSettingsPageObject generalSettingsPage = new GeneralSettingsPageObject(getDriver());
        MailViewPageObject mailViewPage = new MailViewPageObject(getDriver());

        openUrl(mailUrlHome);

        homePage
                // Входим в почту
                .signIn(login, password);

        boxPage
                // Создаем и отправляем новое письмо
                .createNewMail()
                .assertIsNewMailOpened()
                .fillMailData(login, mailSubject1, mailText)
                .sendMail()
                .assertSuccessMailSendFormIsOpen()
                .closeSuccessMailFrame()

                // Переходим в папку "Письма самому себе" и проверяем наличие там только что отправленного письма
                .goToMyselfMail()
                .assertLastReceivedMailSubject(mailSubject1)

                // Открываем его на просмотр
                .openLastRecievedMailToVeiw();

        mailViewPage
                // Проверяем, что текст и тема сообщения соответствует тому, что мы указывали при отправке
                .assertMailSubject(mailSubject1)
                .assertMailText(mailText);

        // Открыть страницу общих настроек почты и аккаунта
        openUrl(generalSettingsUrl);

        // Изменить подпись по умолчанию
        String newSignName = "Имя отправителя №" + new Random().nextInt(100);
        String newSignText = "This is new sign text" + new Random().nextInt(100);
        generalSettingsPage
                .editDefaultSign(newSignName, newSignText);

        // Вернуться в почтовый ящик
        openUrl(inboxUrl);

        boxPage
                // Создать новое письмо и проверить, что измененная подпись корректно отображается
                .createNewMail()
                .assertNewMailSign(newSignText)
                // Отправить новое письмо
                .fillMailData(login, mailSubject2, mailText)
                .sendMail()
                .assertSuccessMailSendFormIsOpen()
                .closeSuccessMailFrame()

                // Открыть его на просмотр
                .goToMyselfMail()
                .assertLastReceivedMailSubject(mailSubject2)
                .openLastRecievedMailToVeiw();

        mailViewPage
                // Проверить, что текст, тема и подпись письма соответствует отправленному
                .assertMailText(mailText)
                .assertMailSubject(mailSubject2)
                .assertViewMailSign(newSignName, newSignText);

        boxPage
                .goToMyselfMail();

        boxPage
                // Удаляем оба отправленных во время теста письма
                .removeMailFromBox(mailSubject1)
                .removeMailFromBox(mailSubject2)
                // И проверяем, что их больше нет в папке
                .assertMailIsNotInBox(mailSubject1)
                .assertMailIsNotInBox(mailSubject2);
    }
}
