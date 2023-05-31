package uitests;

import api.clients.UserClient;
import api.user.User;
import api.user.UserGenerator;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.MainPage;
import pageobjects.ProfilePage;
import utilities.LocalStorage;

import static org.junit.Assert.assertTrue;

public class GoToMainPageFromPersonalPage {
    private final UserClient userClient = new UserClient();
    private WebDriver driver;
    private String accessToken, refreshToken;
    private User user;

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        user = UserGenerator.getRandom();
        System.out.println("name = " + user.getName() + ", email= " + user.getEmail() + ", password = " + user.getPassword());
        ValidatableResponse response = userClient.create(user);
        accessToken = response.extract().path("accessToken");
        refreshToken = response.extract().path("refreshToken");
    }

    @After
    public void tearDown() {
        driver.quit();
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор")
    @Description("Проверка перехода по клику на «Конструктор» и на логотип Stellar Burgers.")
    public void goToMainPageFromPersonalPageTest() {
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\yandexdriver.exe");
        driver = new ChromeDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        authorization();
        MainPage objMainPage = new MainPage(driver);
        objMainPage.pressOnPersonalAccount();
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickOnConstructor();
        assertTrue("В окне конструктора есть надпись Соберите бургер", objMainPage.checkBurgerTitleAppeared());
        objMainPage.pressOnPersonalAccount();
        profilePage.clickOnLogo();
        assertTrue("В окне конструктора есть надпись Соберите бургер", objMainPage.checkBurgerTitleAppeared());
    }

    @Step("Добавлениие информации об авторизованном пользователе в локал стораж")
    public void authorization() {
        LocalStorage localStorage = new LocalStorage(driver);
        localStorage.setItemInLocalStorage("accessToken", accessToken);
        localStorage.setItemInLocalStorage("refreshToken", refreshToken);
    }
}
