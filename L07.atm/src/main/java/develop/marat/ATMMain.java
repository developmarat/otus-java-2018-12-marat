package develop.marat;


import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ATMMain {

    public static void main(String... args) {

        ATM atm = new ATM();
        atm.put(Note.RUB_50, 10);
        atm.put(Note.RUB_100, 10);
        atm.put(Note.RUB_500, 10);
        atm.put(Note.RUB_1000, 10);
        atm.put(Note.RUB_5000, 10);

        System.out.println("------------------");
        System.out.println("RUB");
        System.out.println("------------------");
        System.out.println(atm.getRemainderSum(Currency.RUB));


        getAmount(atm, Currency.RUB, 6850);
        getAmount(atm, Currency.RUB, 3200);
        getAmount(atm, Currency.RUB, 500);


        atm.put(Note.USD_10, 100);
        atm.put(Note.USD_50, 100);
        atm.put(Note.USD_100, 100);

        getAmount(atm, Currency.USD, 370);
        getAmount(atm, Currency.USD, 1050);
    }

    private static void getAmount(@NotNull final ATM atm, final Currency currency, final int amount) {
        System.out.println("Get " +  amount + " " + currency);
        Map<Note, Integer> result = atm.pull(currency, amount);
        System.out.println("Result " + result);
        System.out.println("Remainder " + atm.getRemainderSum(currency));
    }
}
