package com.jardvcode.bot.report.dto.report;

public final class ResponseDTO {

    private String group;
    private String item;
    private String status;
    private String observation;

    public ResponseDTO(String group, String item, String status, String observation) {
        this.group = group;
        this.item = item;
        this.status = status;
        this.observation = observation;
    }

    public String getGroup() {
        return group;
    }

    public String getItem() {
        return item;
    }

    public String getStatus() {
        return status;
    }

    public String getObservation() {
        return observation;
    }
}
