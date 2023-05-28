package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class LocalStorage {
    private final JavascriptExecutor js;

    public LocalStorage(WebDriver webDriver) {
        this.js = (JavascriptExecutor) webDriver;
    }

    public void setItemInLocalStorage(String item, String value) {
        js.executeScript(String.format(
                "window.localStorage.setItem('%s','%s');", item, value));
    }

}