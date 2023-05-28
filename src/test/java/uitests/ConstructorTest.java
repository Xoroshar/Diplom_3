package uitests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
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
    private WebDriver driver;

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Переходы к разным разделам конструктора")
    @Description("Проверка, что при переходе к разделу, цвет текста текущего раздела становится белым и снизу появляется синяя подсветка ")
    public void constructorTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\yandexdriver.exe");
        driver = new ChromeDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        MainPage objMainPage = new MainPage(driver);
        objMainPage.clickOnSouse();
        objMainPage.waitFontToChange();
        assertNotEquals("Цвет текста слова Булки не должен быть белым", WHITE_COLOR, objMainPage.getBulkiColor());
        assertEquals("Цвет текста слова Соус должен быть белым", WHITE_COLOR, objMainPage.getSouseColor());
        assertNotEquals("Цвет текста слова Начинка не должен быть белым", WHITE_COLOR, objMainPage.getNachinkaColor());
        assertEquals("Выбранная область должна быть с синей полоской снизу", BOX_SHADOW_COLOR, objMainPage.getBoxShadowSouse());

        objMainPage.clickOnNachinka();
        objMainPage.waitFontToChange();
        assertNotEquals("Цвет текста слова Булки не должен быть белым", WHITE_COLOR, objMainPage.getBulkiColor());
        assertNotEquals("Цвет текста слова Соус не должен быть белым", WHITE_COLOR, objMainPage.getSouseColor());
        assertEquals("Цвет текста слова Начинка должен быть белым", WHITE_COLOR, objMainPage.getNachinkaColor());
        assertEquals("Выбранная область должна быть с синей полоской снизу", BOX_SHADOW_COLOR, objMainPage.getBoxShadowNachinka());

        objMainPage.clickOnBulka();
        objMainPage.waitFontToChange();
        assertEquals("Цвет текста слова Булки должен быть белым", WHITE_COLOR, objMainPage.getBulkiColor());
        assertNotEquals("Цвет текста слова Соус не должен быть белым", WHITE_COLOR, objMainPage.getSouseColor());
        assertNotEquals("Цвет текста слова Начинка не должен быть белым", WHITE_COLOR, objMainPage.getNachinkaColor());
        assertEquals("Выбранная область должна быть с синей полоской снизу", BOX_SHADOW_COLOR, objMainPage.getBoxShadowBulki());
    }
}
