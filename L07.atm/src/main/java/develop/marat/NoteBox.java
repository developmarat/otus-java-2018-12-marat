package develop.marat;

import org.jetbrains.annotations.NotNull;

public class NoteBox {
    private Note note;
    private int count;

    NoteBox(@NotNull Note note) {
        this.note = note;
    }

    public Note getNote() {
        return note;
    }

    public void put(int putCount) {
        count += putCount;
    }

    public void pull(int pullCount) {
        if(pullCount > count) {
            throw new RuntimeException("Pull note count from NoteBox "+ this.toString() +" is not correct!");
        }

        count -= pullCount;
    }

    public int getCount() {
        return count;
    }

    public int getTotal() {
        return count * note.getValue();
    }

    public String toString() {
        return note + " (" + count + ") ";
    }
}
