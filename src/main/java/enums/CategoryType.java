package enums;

import lombok.Getter;

public enum CategoryType {
    FOOD("Food", 1),
    ELECTRONIC("Electronic", 2),
    FURNITURE("Food", 3);

    @Getter
    private final String title;
    @Getter
    private final Integer id;

    CategoryType(String title, Integer id) {
        this.title = title;
        this.id = id;
    }
}
