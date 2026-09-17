package org.example.service;

import org.example.dao.RecordDao;
import org.example.entity.Record;
import org.example.entity.RecordStatus;
import org.example.entity.dto.RecordsContainerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.entity.RecordStatus.ACTIVE;
import static org.example.entity.RecordStatus.DONE;

@Service
public class RecordService {
    private final RecordDao recordDao;

    @Autowired
    public RecordService(RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    public RecordsContainerDto findAllRecords(String filterMode){
        List<Record> records = recordDao.findAllRecords();
        int numberOfDoneRecords = (int) records.stream().filter(record -> record.getStatus() == DONE).count();
        int numberOfActiveRecords = (int) records.stream().filter(record -> record.getStatus() == ACTIVE).count();
        if(filterMode == null || filterMode.isBlank()){
            return new RecordsContainerDto(records, numberOfDoneRecords, numberOfActiveRecords);
        }

        String filterModeInUpperCase = filterMode.toUpperCase();
        List<String> allowedFilterModes =  Arrays.stream(RecordStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        if (allowedFilterModes.contains(filterModeInUpperCase)){
            List<Record> filteredRecords =  records.stream()
                    .filter(record -> record.getStatus() == RecordStatus.valueOf(filterModeInUpperCase))
                    .collect(Collectors.toList());
            return new RecordsContainerDto(filteredRecords, numberOfDoneRecords, numberOfActiveRecords);
        }
        else{
            return new RecordsContainerDto(records, numberOfDoneRecords, numberOfActiveRecords);
        }
    }

    public void saveRecord(String title){
        if(title !=null && !title.isBlank()){
            recordDao.saveRecord(new Record(title));
        }
    }

    public void updateRecordStatus(int id, RecordStatus newStatus){
        recordDao.updateRecordStatus(id, newStatus);
    }

    public void deleteRecord(int id){
        recordDao.deleteRecord(id);
    }
}
