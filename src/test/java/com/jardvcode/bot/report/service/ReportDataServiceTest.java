package com.jardvcode.bot.report.service;

import com.jardvcode.bot.checklist.entity.instance.InstanceEntity;
import com.jardvcode.bot.checklist.entity.instance.InstanceEntityMother;
import com.jardvcode.bot.checklist.entity.instance.ResponseEntity;
import com.jardvcode.bot.checklist.entity.instance.ResponseEntityMother;
import com.jardvcode.bot.checklist.repository.instance.InstanceRepository;
import com.jardvcode.bot.checklist.repository.instance.ResponseRepository;
import com.jardvcode.bot.report.dto.report.ReportDTO;
import com.jardvcode.bot.report.dto.report.ReportDTOMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportDataServiceTest {

    @Mock
    private InstanceRepository instanceRepository;

    @Mock
    private ResponseRepository responseRepository;

    @InjectMocks
    private ReportDataService reportDataService;

    @Test
    void shouldReturnReportDataWhenChecklistExists() {
        Long checklistId = 1L;
        InstanceEntity instance = InstanceEntityMother.withCompletedStatus();
        List<ResponseEntity> responseEntities = ResponseEntityMother.checklistResponses();
        ReportDTO expectedReportDTO = ReportDTOMother.withResponses(responseEntities);

        when(instanceRepository.findById(checklistId)).thenReturn(Optional.of(instance));
        when(responseRepository.findByInstanceIdOrderByItemGroupId(checklistId)).thenReturn(responseEntities);
        ReportDTO reportDTO = reportDataService.findByInstanceId(checklistId);

        assertEquals(expectedReportDTO, reportDTO);
    }

}