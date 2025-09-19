package com.jardvcode.bot.report.service;

import com.jardvcode.bot.checklist.entity.instance.InstanceEntity;
import com.jardvcode.bot.checklist.entity.instance.ResponseEntity;
import com.jardvcode.bot.checklist.repository.instance.InstanceRepository;
import com.jardvcode.bot.checklist.repository.instance.ResponseRepository;
import com.jardvcode.bot.report.dto.report.HeaderDTO;
import com.jardvcode.bot.report.dto.report.ReportDTO;
import com.jardvcode.bot.report.dto.report.ResponseDTO;
import com.jardvcode.bot.shared.domain.exception.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public final class ReportDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportDataService.class);

    private final InstanceRepository instanceRepository;
    private final ResponseRepository responseRepository;

    public ReportDataService(InstanceRepository instanceRepository, ResponseRepository responseRepository) {
        this.instanceRepository = instanceRepository;
        this.responseRepository = responseRepository;
    }

    public ReportDTO findByInstanceId(Long instanceId) {
        try {
            InstanceEntity instance = instanceRepository.findById(instanceId).orElseThrow(() -> new DataNotFoundException());
            List<ResponseEntity> responseEntities = responseRepository.findByInstanceIdOrderByGroupId(instanceId);

            HeaderDTO header = new HeaderDTO(
                    instance.getUnitNumber().toString(),
                    instance.getTemplate().getName(),
                    instance.getOperatorName().toUpperCase(),
                    instance.getMechanic().toUpperCase(),
                    instance.getMileage().toUpperCase(),
                    instance.getNextService().toUpperCase(),
                    instance.getTimeIn().toString(),
                    instance.getTimeOut().toString(),
                    instance.getDate().toString()
            );

            List<ResponseDTO> responses = responseEntities.stream().map((entity) -> {
                return new ResponseDTO(
                        entity.getGroup().getName().toUpperCase(),
                        entity.getItem().getDescription().toUpperCase(),
                        Optional.ofNullable(entity.getStatus()).orElse("").toUpperCase(),
                        Optional.ofNullable(entity.getObservation()).orElse("").toUpperCase()
                );
            }).collect(Collectors.toList());

            return new ReportDTO(instanceId, header, responses);
        } catch(DataNotFoundException e) {
            LOGGER.error("Report data not found for instanceId={}", instanceId, e);
            throw e;
        }
    }

}
