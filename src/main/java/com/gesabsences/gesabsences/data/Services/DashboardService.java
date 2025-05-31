package com.gesabsences.gesabsences.data.Services;

import com.gesabsences.gesabsences.Web.Dto.Response.DashboardStatsResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.AbsenceResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    public DashboardStatsResponse getDashboardStats() {
        DashboardStatsResponse stats = new DashboardStatsResponse();
        stats.setTotalAbsencesToday(27);
        stats.setTotalRetardsToday(58);
        stats.setJustificationsEnAttente(17);
        return stats;
    }

    public List<AbsenceResponse> getAbsentsDuJour() {
        List<AbsenceResponse> absences = new ArrayList<>();
        absences.add(new AbsenceResponse("Ismael B.", "L1 IAGE", "2025-05-20", "Cisco", "Absence", null, "13:15", true));
        return absences;
    }

    public List<AbsenceResponse> getAbsencesFiltres(String date, String matiere, String statut) {
        List<AbsenceResponse> all = getAbsentsDuJour();
        return all;
    }
}