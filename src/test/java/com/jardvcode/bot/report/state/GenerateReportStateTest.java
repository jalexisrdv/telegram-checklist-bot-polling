package com.jardvcode.bot.report.state;

import com.jardvcode.bot.report.dto.ChecklistDTO;
import com.jardvcode.bot.report.dto.ChecklistDTOMother;
import com.jardvcode.bot.report.dto.report.ReportDTO;
import com.jardvcode.bot.report.dto.report.ReportDTOMother;
import com.jardvcode.bot.report.service.PdfReportGenerator;
import com.jardvcode.bot.report.service.ReportDataService;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.user.service.BotSessionDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenerateReportStateTest {

    @Mock
    private BotContext botContext;

    @Mock
    private BotSessionDataService sessionDataService;

    @Mock
    private ReportDataService reportDataService;

    @Mock
    private PdfReportGenerator pdfReportGenerator;

    @InjectMocks
    private GenerateReportState state;

    @Test
    void shouldSendReport() throws Exception {
        Long userId = 1L;
        ChecklistDTO checklistDTO = ChecklistDTOMother.create();
        ReportDTO reportDTO = ReportDTOMother.create();
        byte[] pdfReport = new byte[0];
        String fileName = "reporte_de_unidad_" + checklistDTO.unitNumber() + ".pdf";

        when(botContext.getSystemUserId()).thenReturn(userId);
        when(sessionDataService.findByUserId(userId, ChecklistDTO.class)).thenReturn(checklistDTO);
        when(reportDataService.findByInstanceId(checklistDTO.id())).thenReturn(reportDTO);
        when(pdfReportGenerator.generate(reportDTO)).thenReturn(pdfReport);
        Decision decision = state.onBotMessage(botContext);

        verify(botContext, times(1)).sendText("En un momento recibirás tu reporte.");
        verify(botContext, times(1)).sendDocument(pdfReport, fileName);
        assertNull(decision.nextState());
    }

}