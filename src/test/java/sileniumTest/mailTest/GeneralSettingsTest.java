package sileniumTest.mailTest;

import automation.PropertyLoader;
import automation.pageObjects.mail.GeneralSettingsPageObject;
import io.qameta.allure.Step;
import sileniumTest.BaseDriverClass;

import java.util.Random;

public class GeneralSettingsTest extends BaseDriverClass {

    /**
     * Открыть страницу общих настроек почты и аккаунта
     */
    @Step
    public void goToGeneralSettings(){
        String generalSettingsUrl = PropertyLoader.loadProperty("generalSettingsUrl");
        openUrl(generalSettingsUrl);
    }

    /**
     * Изменить подпись по умолчанию
     */
    @Step
    public void editDefaultSign(final String signName,
                                final String signText){
        GeneralSettingsPageObject settingPage = new GeneralSettingsPageObject(getDriver());
        settingPage
                .clickToEditDefaultSignButton()
                .setSignName(signName)
                .setSignText(signText)
                .clickSaveEditSignButton();
    }

}
