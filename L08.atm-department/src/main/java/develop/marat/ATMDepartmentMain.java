package develop.marat;


import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ATMDepartmentMain {

    public static void main(String... args) {

        ATMDepartmentMain atmDepartmentMain = new ATMDepartmentMain();
        atmDepartmentMain.rubDepartment();
        //atmDepartmentMain.usdDepartment();
    }

    private void rubDepartment() {
        int count = 5;
        ATMDepartment westAtmDepartment = new ATMDepartment("Western City Department");
        ATM[] atms = new ATM[count];

        for (int i = 0; i < count; i++) {
            ATM atm = new ATM();
            fillRubATM(atm);
            atm.setInitState(atm.getCurrentState());
            westAtmDepartment.addATM(atm);
            atms[i] = atm;
        }

        atms[2].put(Note.RUB_1000, 10);
        atms[2].setInitState(atms[2].getCurrentState());

        atms[3].put(Note.RUB_5000, 10);
        atms[3].setInitState(atms[3].getCurrentState());

        printDepartment(westAtmDepartment, Currency.RUB);
        getAmount(atms[1], Currency.RUB, 10000);
        getAmount(atms[2], Currency.RUB, 10000);
        printDepartment(westAtmDepartment, Currency.RUB);


        System.out.println("------------------");
        System.out.println("AFTER Restore init state");
        westAtmDepartment.restoreInitState();
        printDepartment(westAtmDepartment, Currency.RUB);

    }

    private void usdDepartment() {
        int count = 5;
        ATMDepartment eastAtmDepartment = new ATMDepartment("Eastern City Department");
        ATM[] atms = new ATM[count];

        for (int i = 0; i < count; i++) {
            ATM atm = new ATM();
            fillUsdAtm(atm);
            atm.setInitState(atm.getCurrentState());
            eastAtmDepartment.addATM(atm);
            atms[i] = atm;
        }

        atms[2].put(Note.USD_50, 10);
        atms[2].setInitState(atms[2].getCurrentState());

        atms[3].put(Note.USD_100, 10);
        atms[3].setInitState(atms[3].getCurrentState());

        printDepartment(eastAtmDepartment, Currency.USD);
        getAmount(atms[1], Currency.USD, 10000);
        getAmount(atms[2], Currency.USD, 10000);
        printDepartment(eastAtmDepartment, Currency.USD);

        System.out.println("------------------");
        System.out.println("AFTER Restore init state");
        eastAtmDepartment.restoreInitState();
        printDepartment(eastAtmDepartment, Currency.USD);
    }

    private void fillRubATM(ATM atm) {
        atm.put(Note.RUB_50, 10);
        atm.put(Note.RUB_100, 10);
        atm.put(Note.RUB_100, 10);
        atm.put(Note.RUB_500, 10);
        atm.put(Note.RUB_1000, 10);
        atm.put(Note.RUB_5000, 10);
    }

    private void fillUsdAtm(ATM atm) {
        atm.put(Note.USD_10, 100);
        atm.put(Note.USD_50, 100);
        atm.put(Note.USD_100, 100);
    }

    private void printDepartment(@NotNull final ATMDepartment atmDepartment, final Currency currency) {
        System.out.println("------------------");
        System.out.println(atmDepartment);
        System.out.println("Remainder Sum = " + atmDepartment.getRemainderSum(currency));

        for (ATM atm: atmDepartment.getATMs()) {
            System.out.println(atm.getRemainderSum(currency) + " " + currency);
        }
        System.out.println("------------------");
    }

    private void getAmount(@NotNull final ATM atm, final Currency currency, final int amount) {
        System.out.println("Remainder " + atm.getRemainderSum(currency));
        System.out.println("Get " +  amount + " " + currency);
        Map<Note, Integer> result = atm.pull(currency, amount);
        System.out.println("Result " + result);
        System.out.println("Remainder " + atm.getRemainderSum(currency));
        System.out.println("------------------");
    }
}
