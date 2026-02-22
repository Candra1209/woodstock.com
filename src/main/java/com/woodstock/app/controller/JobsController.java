package com.woodstock.app.controller;

import com.woodstock.app.entity.Jobs;
import com.woodstock.app.models.response.SuccessResponse;
import com.woodstock.app.service.impelment.JobsServiceImpl;
import com.woodstock.app.utils.constants.RouteAppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(RouteAppConstant.BASE_V1 + RouteAppConstant.JOBS)
public class JobsController {

    private final JobsServiceImpl jobsService;

    @Autowired
    public JobsController(JobsServiceImpl jobsService) {
        this.jobsService = jobsService;
    }

    @GetMapping("/all")
    public ResponseEntity<SuccessResponse<?>> getAlljob(){

        SuccessResponse<?> result = SuccessResponse.builder()
                .status(HttpStatus.OK)
                .message("success get all jobs")
                .data(jobsService.findAll())
                .build();

        return ResponseEntity.ok(result);
    }
}
