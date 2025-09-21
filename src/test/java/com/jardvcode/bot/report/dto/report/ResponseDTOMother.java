package com.jardvcode.bot.report.dto.report;

import com.jardvcode.bot.checklist.entity.instance.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class ResponseDTOMother {

    public static List<ResponseDTO> withResponses(List<ResponseEntity> responses) {
        return responses.stream().map((response) -> {
            return new ResponseDTO(
                    response.getItem().getGroup().getName(),
                    response.getItem().getDescription(),
                    response.getStatus(), response.getObservation()
            );
        }).collect(Collectors.toList());
    }

}
