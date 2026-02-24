package com.woodstock.app.controller;

import com.woodstock.app.entity.Location;
import com.woodstock.app.models.params.PageParams;
import com.woodstock.app.models.params.SortParams;
import com.woodstock.app.models.request.location.LocationRequest;
import com.woodstock.app.models.response.PagingResponse;
import com.woodstock.app.models.response.SuccessResponse;
import com.woodstock.app.models.response.account_info.AccountInfoResponse;
import com.woodstock.app.models.response.location.LocationResponse;
import com.woodstock.app.service.impelment.LocationService;
import com.woodstock.app.utils.constants.RouteAppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(RouteAppConstant.BASE_V1 + RouteAppConstant.LOCATION)
public class LocationRepository {

    private final LocationService locationService;

    @Autowired
    public LocationRepository(LocationService locationService) {
        this.locationService = locationService;
    }

    //get-all
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<SuccessResponse<PagingResponse<LocationResponse>>> getAllLocation(
            @ModelAttribute PageParams pageParams,
            @ModelAttribute SortParams sortParams
            ){

        int pageConfig = Math.max(pageParams.getPage()-1, 0);

        Sort sort = Sort.by(Sort.Direction.fromString(sortParams.getOrder()), sortParams.getFilter());

        Pageable pageable = PageRequest.of(pageConfig, pageParams.getSize(), sort);

        Page<LocationResponse> page = locationService.getPagging(pageable).map(Location::toResponse);

        PagingResponse<LocationResponse> paging = PagingResponse.<LocationResponse>builder()
                .content(page.getContent())
                .page(pageParams.getPage())
                .size(page.getSize())
                .totalPage(page.getTotalPages())
                .totalData(page.getTotalElements())
                .build();

        SuccessResponse<PagingResponse<LocationResponse>> result = SuccessResponse.<PagingResponse<LocationResponse>>builder()
                .status(HttpStatus.OK)
                .message("success get all account information from database")
                .data(paging)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    //get-by-id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<SuccessResponse<LocationResponse>> getLocationById(@PathVariable String id){

        LocationResponse response = locationService.findbyId(UUID.fromString(id)).toResponse();

        SuccessResponse<LocationResponse> result = SuccessResponse.<LocationResponse>builder()
                .status(HttpStatus.OK)
                .message("success get location from database")
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //add
    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<SuccessResponse<LocationResponse>> addNewLocation(@RequestBody LocationRequest locationRequest){

        Location newLocation = Location.builder()
                .name(locationRequest.getName())
                .latitude(locationRequest.getLatitude())
                .longitude(locationRequest.getLongitude())
                .build();

        LocationResponse response = locationService.save(newLocation).toResponse();

        SuccessResponse<LocationResponse> result = SuccessResponse.<LocationResponse>builder()
                .status(HttpStatus.CREATED)
                .message("success created new location")
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    //delete
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SuccessResponse<LocationResponse>> deleteLocation(@PathVariable String id){

        LocationResponse response = locationService.delete(UUID.fromString(id)).toResponse();

        SuccessResponse<LocationResponse> result = SuccessResponse.<LocationResponse>builder()
                .status(HttpStatus.OK)
                .message("success deleted new location")
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //update
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<SuccessResponse<LocationResponse>> updateLocation(@RequestBody LocationRequest locationRequest, @PathVariable String id){

        Location newLocation = Location.builder()
                .name(locationRequest.getName())
                .latitude(locationRequest.getLatitude())
                .longitude(locationRequest.getLongitude())
                .build();

        LocationResponse response = locationService.Update(newLocation, UUID.fromString(id)).toResponse();

        SuccessResponse<LocationResponse> result = SuccessResponse.<LocationResponse>builder()
                .status(HttpStatus.CREATED)
                .message("success updating location with id : " + response.getId())
                .data(response)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
