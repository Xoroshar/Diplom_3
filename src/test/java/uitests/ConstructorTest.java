package uitests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.MainPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ConstructorTest {
    private final String WHITE_COLOR = "rgba(255, 255, 255, 1)";
    private final String BOX_SHADOW_COLOR = "rgb(76, 76, 255) 0px -2px 0px 0px inset";
    private final String colorShouldNotBeWhiteMessage = "Цвет текста не должен быть белым";
    private final String colorShouldBeWhiteMessage = "Цвет текста должен быть белым";
    private final String colorShouldBeBlueMessage = "Выбранная область должна быть с синей полоской снизу";
    private MainPage objMainPage;
    private WebDriver driver;

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\yandexdriver.exe");
        driver = new ChromeDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        objMainPage = new MainPage(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Step("Проверка цвета шрифта и подсветки")
    public void checkColors(String shouldBeWhite, String shouldNotBeWhite1, String shouldNotBeWhite2, String shouldBeBlue) {
        assertNotEquals(colorShouldBeWhiteMessage, WHITE_COLOR, shouldNotBeWhite1);
        assertEquals(colorShouldNotBeWhiteMessage, WHITE_COLOR, shouldBeWhite);
        assertNotEquals(colorShouldNotBeWhiteMessage, WHITE_COLOR, shouldNotBeWhite2);
        assertEquals(colorShouldBeBlueMessage, BOX_SHADOW_COLOR, shouldBeBlue);
    }

    @Test
    @DisplayName("Переход к разделу Соусы")
    @Description("Проверка, что при переходе к разделу Соусы, цвет текста текущего раздела становится белым и снизу появляется синяя подсветка")
    public void clickOnSouseTab() throws InterruptedException {
        objMainPage.clickOnSouse();
        objMainPage.waitFontToChange();
        checkColors(objMainPage.getSouseColor(),
                objMainPage.getBulkiColor(),
                objMainPage.getNachinkaColor(),
                objMainPage.getBoxShadowSouse());
    }

    @Test
    @DisplayName("Переход к разделу Начинки")
    @Description("Проверка, что при переходе к разделу Начинки, цвет текста текущего раздела становится белым и снизу появляется синяя подсветка")
    public void clickOnNachinkaTab() throws InterruptedException {
        objMainPage.clickOnNachinka();
        objMainPage.waitFontToChange();
        checkColors(objMainPage.getNachinkaColor(),
                objMainPage.getSouseColor(),
                objMainPage.getBulkiColor(),
                objMainPage.getBoxShadowNachinka());
    }

    @Test
    @DisplayName("Переход к разделу Булки")
    @Description("Проверка, что при переходе к разделу Булки, цвет текста текущего раздела становится белым и снизу появляется синяя подсветка")
    public void clickOnBulkaTab() throws InterruptedException {
        objMainPage.clickOnNachinka();
        objMainPage.waitFontToChange();
        objMainPage.clickOnBulka();
        objMainPage.waitFontToChange();
        checkColors(objMainPage.getBulkiColor(),
                objMainPage.getNachinkaColor(),
                objMainPage.getSouseColor(),
                objMainPage.getBoxShadowBulki());
    }
}
