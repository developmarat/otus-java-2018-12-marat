package develop.marat;

public enum Note {
    RUB_50(Currency.RUB, 50),
    RUB_100(Currency.RUB, 100),
    RUB_200(Currency.RUB, 200),
    RUB_500(Currency.RUB, 500),
    RUB_1000(Currency.RUB, 1000),
    RUB_2000(Currency.RUB, 2000),
    RUB_5000(Currency.RUB, 5000),

    USD_1(Currency.USD, 1),
    USD_2(Currency.USD, 2),
    USD_5(Currency.USD, 5),
    USD_10(Currency.USD, 10),
    USD_50(Currency.USD, 50),
    USD_100(Currency.USD, 100);

    private Currency currency;
    private int value;

    Note(Currency currency, int value) {
        this.currency = currency;
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue() + " " + currency;
    }
}
