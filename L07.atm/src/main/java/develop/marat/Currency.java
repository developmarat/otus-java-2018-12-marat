package develop.marat;

public enum Currency {
    RUB("руб"),
    USD("usd");

    private String name;

    Currency(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
