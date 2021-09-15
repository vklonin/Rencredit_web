package pages.utils;

public enum MainNavigationMenuItem {

    CREDITS("Кредиты"),
    CARDS("Карты"),
    DEPOSITS("Вклады"),
    INVESTMENTS("Инвестиции"),
    SERVICES("Сервисы");

    private final String itemName;

    public String getItemName() {
        return itemName;
    }

    MainNavigationMenuItem(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return itemName;
    }

}

