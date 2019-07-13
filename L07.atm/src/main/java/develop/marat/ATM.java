package develop.marat;

import java.util.*;
import java.util.stream.Collectors;

public class ATM {

    Map<Note, NoteBox> boxes = new HashMap<>();
    Map<Currency, List<NoteBox>> boxesByCurrency = new HashMap<>();

    ATM() {
        //init
        for (Note note : Note.values()) {
            NoteBox box = new NoteBox(note);
            boxes.put(note, box);
            List<NoteBox> boxByCurrency = boxesByCurrency.computeIfAbsent(note.getCurrency(), v -> new ArrayList<>());
            boxByCurrency.add(box);
        }

        //sort by desc note value
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

}
