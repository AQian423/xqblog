package com.qinweizhao.blog.controller.admin.api;

import com.qinweizhao.blog.model.dto.StatisticDTO;
import com.qinweizhao.blog.model.dto.StatisticWithUserDTO;
import com.qinweizhao.blog.service.StatisticService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Statistic controller.
 *
 * @author ryanwang
 * @date 2019-12-16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/statistics")
public class StatisticController {

    private final StatisticService statisticService;


    @GetMapping
    @ApiOperation("Gets blog statistics.")
    public StatisticDTO statistics() {
        return statisticService.getStatistic();
    }

    @GetMapping("user")
    @ApiOperation("Gets blog statistics with user")
    public StatisticWithUserDTO statisticsWithUser() {
        return statisticService.getStatisticWithUser();
    }
}
