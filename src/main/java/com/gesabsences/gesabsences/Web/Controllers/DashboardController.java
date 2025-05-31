package com.gesabsences.gesabsences.Web.Controllers;

import com.gesabsences.gesabsences.Web.Dto.Response.DashboardStatsResponse;
import com.gesabsences.gesabsences.Web.Dto.Response.AbsenceResponse;
import com.gesabsences.gesabsences.data.Services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public DashboardStatsResponse getDashboardStats() {
        return dashboardService.getDashboardStats();
    }

    @GetMapping("/absents-aujourdhui")
    public List<AbsenceResponse> getAbsentsAujourdhui() {
        return dashboardService.getAbsentsDuJour();
    }

    @GetMapping("/absences-filtres")
    public List<AbsenceResponse> getAbsencesFiltres(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String matiere,
            @RequestParam(required = false) String statut) {
        return dashboardService.getAbsencesFiltres(date, matiere, statut);
    }
}
