package org.example.deadlock;

public class Record {
    private final int id;

    public Record(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
