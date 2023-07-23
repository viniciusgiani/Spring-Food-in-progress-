package com.spring.data.api.v1.controller;

import com.spring.data.api.v1.FoodLinks;
import com.spring.data.api.v1.openapi.controller.StatisticsControllerOpenApi;
import com.spring.data.core.security.CheckSecurity;
import com.spring.data.domain.filter.DailySaleFilter;
import com.spring.data.domain.model.dto.DailySale;
import com.spring.data.domain.service.SaleQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/statistics")
public class StatisticsController implements StatisticsControllerOpenApi {

    @Autowired
    private SaleQueryService saleQueryService;

    @Autowired
    private SaleReportService saleReportService;

    @Autowired
    private FoodLinks foodLinks;

    @Override
    @CheckSecurity.Statistics.CanQuery
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public StatisticsModel statistics() {
        var statisticsModel = new StatisticsModel();

        statisticsModel.add(foodLinks.linkToStatisticsDailySales("daily-sales"));

        return statisticsModel;
    }

    @Override
    @CheckSecurity.Statistics.CanQuery
    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailySale> queryDailySales(DailySaleFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return saleQueryService.queryDailySales(filter, timeOffset);
    }

    @Override
    @CheckSecurity.Statistics.CanQuery
    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> queryDailySalesPdf(DailySaleFilter dailySaleFilter,
                                                     @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        byte[] bytesPdf = saleReportService.emitDailySales(dailySaleFilter, timeOffset);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);

    }
    public static class StatisticsModel extends RepresentationModel<StatisticsModel>{}
}
