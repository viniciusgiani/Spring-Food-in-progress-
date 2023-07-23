package com.spring.data.domain.service;

import com.spring.data.domain.filter.DailySaleFilter;
import com.spring.data.domain.model.dto.DailySale;

import java.util.List;

public interface SaleQueryService {

    List<DailySale> queryDailySales(DailySaleFilter filter, String timeOffset);

}
