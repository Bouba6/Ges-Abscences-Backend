package com.gesabsences.gesabsences.Web.Dto.Response;

public class DashboardStatsResponse {
    private int totalAbsencesToday;
    private int totalRetardsToday;
    private int justificationsEnAttente;

    public int getTotalAbsencesToday() {
        return totalAbsencesToday;
    }

    public void setTotalAbsencesToday(int totalAbsencesToday) {
        this.totalAbsencesToday = totalAbsencesToday;
    }

    public int getTotalRetardsToday() {
        return totalRetardsToday;
    }

    public void setTotalRetardsToday(int totalRetardsToday) {
        this.totalRetardsToday = totalRetardsToday;
    }

    public int getJustificationsEnAttente() {
        return justificationsEnAttente;
    }

    public void setJustificationsEnAttente(int justificationsEnAttente) {
        this.justificationsEnAttente = justificationsEnAttente;
    }
}
