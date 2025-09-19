package com.jardvcode.bot.report.state;

import com.jardvcode.bot.report.dto.report.ReportDTO;
import com.jardvcode.bot.report.dto.ChecklistDTO;
import com.jardvcode.bot.report.service.PdfReportGenerator;
import com.jardvcode.bot.report.service.ReportDataService;
import com.jardvcode.bot.shared.domain.bot.BotContext;
import com.jardvcode.bot.shared.domain.state.Decision;
import com.jardvcode.bot.shared.domain.state.State;
import com.jardvcode.bot.user.service.BotSessionDataService;
import org.springframework.stereotype.Service;

@Service
public final class GenerateReportState implements State {

    private final BotSessionDataService sessionDataService;
    private final ReportDataService reportDataService;
    private final PdfReportGenerator pdfReportGenerator;

    public GenerateReportState(BotSessionDataService sessionDataService, ReportDataService reportDataService, PdfReportGenerator pdfReportGenerator) {
        this.sessionDataService = sessionDataService;
        this.reportDataService = reportDataService;
        this.pdfReportGenerator = pdfReportGenerator;
    }

    @Override
    public Decision onBotMessage(BotContext botContext) throws Exception {
        botContext.sendText("En un momento recibirás tu reporte.");

        ChecklistDTO checklistDTO = sessionDataService.findByUserId(botContext.getSystemUserId(), ChecklistDTO.class);

        ReportDTO report = reportDataService.findByInstanceId(checklistDTO.id());
        String fileName = "reporte_de_unidad_" + checklistDTO.unitNumber() + ".pdf";

        botContext.sendDocument(pdfReportGenerator.generate(report), fileName);

        return Decision.stay();
    }

    @Override
    public Decision onUserInput(BotContext botContext) throws Exception {
        return Decision.moveTo(SelectReportState.class);
    }
}
