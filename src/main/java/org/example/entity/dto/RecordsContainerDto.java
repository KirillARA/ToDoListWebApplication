package org.example.entity.dto;

import org.example.entity.Record;

import java.util.List;

public class RecordsContainerDto {
    private List<Record> records;
    private final int numberOfDoneRecords;
    private final int numberOfActiveRecords;

    public RecordsContainerDto(List<Record> records, int numberOfDoneRecords, int numberOfActiveRecords) {
        this.numberOfDoneRecords = numberOfDoneRecords;
        this.numberOfActiveRecords = numberOfActiveRecords;
        this.records = records;
    }

    public List<Record> getRecords() {
        return records;
    }

    public int getNumberOfDoneRecords() {
        return numberOfDoneRecords;
    }

    public int getNumberOfActiveRecords() {
        return numberOfActiveRecords;
    }
}
