package pages;

import com.codeborne.selenide.Condition;
import pages.utils.MainNavigationMenuItem;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.sleep;

public class NavigationSubMenu {

    public void navigateToMenuItem(String menuName) {
        $$(".nav__link").find(Condition.text(menuName)).click();
    }


}
