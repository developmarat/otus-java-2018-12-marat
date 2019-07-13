package develop.marat;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class ATM {
    private Map<Note, NoteBox> boxes = new HashMap<>();
    private Map<Currency, List<NoteBox>> boxesByCurrency = new HashMap<>();

    private Memento initState;

    public ATM() {
        for (Note note : Note.values()) {
            NoteBox box = new NoteBox(note);
            boxes.put(note, box);
        }

        boxesByCurrency = createBoxesByCurrency(boxes);
        sortBoxesByCurrency(boxesByCurrency);

        initState = getCurrentState();
    }

    public ATM(final ATM atm) {
        restoreState(atm.getCurrentState());
        initState = new Memento(atm.initState);
    }

    private static Map<Note, NoteBox> copyBoxes(final Map<Note, NoteBox> originalBoxes) {
        Map<Note, NoteBox> copyBoxes = new HashMap<>();

        for (final Map.Entry<Note, NoteBox> originalBox: originalBoxes.entrySet()) {
            final Note note = originalBox.getKey();
            final NoteBox originalNoteBox = originalBox.getValue();
            NoteBox noteBox = new NoteBox(originalNoteBox);
            copyBoxes.put(note, noteBox);
        }

        return copyBoxes;
    }

    private static Map<Currency, List<NoteBox>> createBoxesByCurrency(final Map<Note, NoteBox> boxes) {
        Map<Currency, List<NoteBox>> boxesByCurrency = new HashMap<>();

        for (Map.Entry<Note, NoteBox> box: boxes.entrySet()) {
            final Note note = box.getKey();
            final NoteBox noteBox = box.getValue();

            List<NoteBox> boxByCurrency = boxesByCurrency.computeIfAbsent(note.getCurrency(), v -> new ArrayList<>());
            boxByCurrency.add(noteBox);
        }

        return boxesByCurrency;
    }

    // Sort by desc note value
    private static void sortBoxesByCurrency(@NotNull Map<Currency, List<NoteBox>> boxesByCurrency) {
        boxesByCurrency.keySet().forEach(key -> {
            List<NoteBox> boxesCurrentCurrency = boxesByCurrency.get(key);
            List<NoteBox> sortBoxesCurrentCurrency = boxesCurrentCurrency
                    .stream()
                    .sorted(Comparator.comparingInt(b -> -b.getNote().getValue())).collect(Collectors.toList());
            boxesByCurrency.put(key, sortBoxesCurrentCurrency);
        });
    }

    public void put(Note note, int count) {
        boxes.get(note).put(count);
    }

    public Map<Note, Integer> pull(final Currency currency, final int amount) {
        List<NoteBox> boxesCurrentCurrency = boxesByCurrency.get(currency);

        NoteBox[] availableBoxes = boxesCurrentCurrency
                .stream()
                .filter(b -> b.getCount() > 0 && b.getNote().getValue() <= amount)
                .toArray(NoteBox[]::new);

        if(availableBoxes.length > 0) {
            Map<Note, Integer> result = new HashMap<>();

            for(int i = 0; i < availableBoxes.length; i++) {
                int tempAmount = 0;
                for(int j = i; j < availableBoxes.length; j++) {
                    NoteBox currentNoteBox = availableBoxes[j];
                    int count = (amount - tempAmount) / currentNoteBox.getNote().getValue();
                    int availableCount = Math.min(count, currentNoteBox.getCount());
                    if(availableCount > 0) {
                        tempAmount += availableCount * currentNoteBox.getNote().getValue();
                        result.put(currentNoteBox.getNote(), availableCount);

                        if(tempAmount >= amount) {
                            break;
                        }
                    }
                }

                if(tempAmount == amount){
                    for (Map.Entry<Note, Integer> entry : result.entrySet()) {
                        boxes.get(entry.getKey()).pull(entry.getValue());
                    }
                    return result;
                }
                else {
                    result.clear();
                }

            }

        }

        throw new RuntimeException("Requested amount is not available");
    }

    public int getRemainderSum(final Currency currency) {
        int remainderSum = 0;
        List<NoteBox> boxesCurrentCurrency = boxesByCurrency.get(currency);
        for (NoteBox box: boxesCurrentCurrency) {
            remainderSum += box.getTotal();
        }
        return remainderSum;
    }

    public static class Memento {
        private final Map<Note, NoteBox> boxes;

        public Memento(@NotNull ATM atm) {
            boxes = copyBoxes(atm.boxes);
        }

        public Memento(@NotNull Memento memento) {
            boxes = copyBoxes(memento.boxes);
        }
    }

    public Memento getCurrentState() {
        return new Memento(this);
    }

    private void restoreState(@NotNull final Memento memento) {
        Map<Note, NoteBox> oldBoxes = boxes;
        Map<Currency, List<NoteBox>> oldBoxesByCurrency = boxesByCurrency;

        boxes = copyBoxes(memento.boxes);
        boxesByCurrency = createBoxesByCurrency(boxes);

        oldBoxes.clear();
        oldBoxesByCurrency.clear();
    }

    public void setInitState(@NotNull Memento initState) {
        this.initState = initState;
    }

    public void restoreInitState() {
        restoreState(this.initState);
    }

}
