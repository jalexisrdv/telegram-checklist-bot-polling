package com.jardvcode.bot.report.dto.report;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ResponseDTO that = (ResponseDTO) o;
        return Objects.equals(group, that.group) && Objects.equals(item, that.item) && Objects.equals(status, that.status) && Objects.equals(observation, that.observation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, item, status, observation);
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "group='" + group + '\'' +
                ", item='" + item + '\'' +
                ", status='" + status + '\'' +
                ", observation='" + observation + '\'' +
                '}';
    }

}
