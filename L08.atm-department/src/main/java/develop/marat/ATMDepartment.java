package develop.marat;

import java.util.LinkedHashSet;
import java.util.Set;

public class ATMDepartment {

    private String title;
    private Set<ATM> ATMs = new LinkedHashSet<>();

    public ATMDepartment(String title) {
        this.title = title;
    }

    public boolean addATM(ATM atm) {
        return ATMs.add(atm);
    }

    public Set<ATM> getATMs() {
        return ATMs;
    }

    public boolean removeATM(ATM atm) {
        return ATMs.remove(atm);
    }

    public int getRemainderSum(final Currency currency) {
        return ATMs.stream().mapToInt(a -> a.getRemainderSum(currency)).sum();
    }

    public void restoreInitState() {
        ATMs.forEach(a -> a.restoreInitState());
    }

    @Override
    public String toString() {
        return title;
    }
}
