package com.jardvcode.bot.report.service;

import com.jardvcode.bot.report.dto.report.ReportDTO;
import com.jardvcode.bot.shared.domain.exception.UnexpectedException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public final class PdfReportGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PdfReportGenerator.class);

    public byte[] generate(ReportDTO report) {
        try {
            Map<String, Object> parameters = new HashMap<>();

            parameters.put("HEADER", report.header().toMap());
            parameters.put("RESPONSES", report.responses());

            InputStream inputStream = getClass().getResourceAsStream("/reports/checklist.jasper");

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
            JRDataSource dataSource = new JREmptyDataSource();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch(JRException  e) {
            LOGGER.error("Failed to generate report for checklistId={}", report.checklistId(), e);
            throw new UnexpectedException();
        } catch (Exception e) {
            LOGGER.error("Unexpected error while generating report for checklistId={}", report.checklistId(), e);
            throw new UnexpectedException();
        }
    }

}
