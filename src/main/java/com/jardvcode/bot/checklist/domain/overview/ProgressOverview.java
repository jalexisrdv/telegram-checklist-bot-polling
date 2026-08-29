package com.jardvcode.bot.checklist.domain.overview;

public class ProgressOverview {

    private final int completed;
    private final int total;

    public ProgressOverview(int completed, int total) {
        this.completed = completed;
        this.total = total;
    }

    public int getCompleted() {
        return completed;
    }

    public int getTotal() {
        return total;
    }

    public int percentage() {
        return total == 0 ? 0 : (completed * 100) / total;
    }

}