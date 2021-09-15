package pages;

import com.codeborne.selenide.Condition;
import pages.utils.MainNavigationMenuItem;

import static com.codeborne.selenide.Selenide.$$;

public class MainNavigationMenu {

    public void navigateToMenuItem(MainNavigationMenuItem navigationMenuItem) {
        $$(".nav__item_parent").find(Condition.text(navigationMenuItem.getItemName())).click();
    }


}
