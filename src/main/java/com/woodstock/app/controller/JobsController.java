package com.woodstock.app.controller;

import com.woodstock.app.entity.Jobs;
import com.woodstock.app.entity.JobsEnum;
import com.woodstock.app.models.request.jobs.JobsRequest;
import com.woodstock.app.models.response.SuccessResponse;
import com.woodstock.app.service.impelment.JobsServiceImpl;
import com.woodstock.app.utils.constants.RouteAppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(RouteAppConstant.BASE_V1 + RouteAppConstant.JOBS)
public class JobsController {

    private final JobsServiceImpl jobsService;

    @Autowired
    public JobsController(JobsServiceImpl jobsService) {
        this.jobsService = jobsService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<SuccessResponse<?>> getAlljob(){

        SuccessResponse<?> result = SuccessResponse.builder()
                .status(HttpStatus.OK)
                .message("success get all jobs")
                .data(jobsService.findAll())
                .build();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<SuccessResponse<?>> getJobsId(@PathVariable String id){
        SuccessResponse<?> result = SuccessResponse.builder()
                .status(HttpStatus.OK)
                .message("success get jobs")
                .data(jobsService.findbyId(UUID.fromString(id)))
                .build();

        return ResponseEntity.ok(result);
    }

}
