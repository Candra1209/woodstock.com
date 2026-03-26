package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.AccountInfo;
import com.woodstock.app.entity.JobsEnum;
import com.woodstock.app.entity.Location;
import com.woodstock.app.entity.mongo_entity.Tree;
import com.woodstock.app.models.request.tree.TreeRequest;
import com.woodstock.app.models.response.PagingResponse;
import com.woodstock.app.models.response.tree.TreeResponseV2;
import com.woodstock.app.models.response.tree_type.TreeTypeResponse;
import com.woodstock.app.repositorty.mongo_repository.TreeRepositoryMongo;
import com.woodstock.app.utils.calculation.LumberCalculation;
import com.woodstock.app.utils.exception.global.InvalidJobAssignmentException;
import com.woodstock.app.utils.exception.jobs.ForbidenJobRoleAccessException;
import com.woodstock.app.utils.exception.location.LocationNotFoundException;
import com.woodstock.app.utils.exception.tree.TreeNotFoundExeption;
import com.woodstock.app.utils.exception.tree_type.TreeTypeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TreeServiceImplV2 {

    private final TreeRepositoryMongo treeRepositoryMongo;
    private final TreeTypeService treeTypeService;
    private final LocationService locationService;
    private final AccountInfoServiceImpl accountInfoService;

    @Autowired
    public TreeServiceImplV2(TreeRepositoryMongo treeRepositoryMongo, TreeTypeService treeTypeService, LocationService locationService, AccountInfoServiceImpl accountInfoService) {
        this.treeRepositoryMongo = treeRepositoryMongo;
        this.treeTypeService = treeTypeService;
        this.locationService = locationService;
        this.accountInfoService = accountInfoService;
    }

    public TreeResponseV2 addNewTree(TreeRequest request){


        UUID locationId = UUID.fromString(request.getLocation_id());
        if(!locationService.isExists(locationId)){
            throw new LocationNotFoundException("there no location with id " + locationId + " registered");
        }

        UUID treeTypeId = UUID.fromString(request.getTree_type_id());
        if (!treeTypeService.isExists(treeTypeId)) {
            throw new TreeTypeNotFoundException("there no tree type with id " + treeTypeId + " registered");
        }

        UUID scalerId = UUID.fromString(request.getScaller_id());
        AccountInfo scaler = accountInfoService.getReferenceById(scalerId);
        if (!scaler.getJobs().stream().anyMatch(job -> job.getName() == JobsEnum.SCALLER)){
            throw new ForbidenJobRoleAccessException(String.format("Account with id %s is not scaler", scaler.getId()));
        }

        UUID fallerId = UUID.fromString(request.getFaller_id());
        AccountInfo faller = accountInfoService.getReferenceById(fallerId);
        if (!faller.getJobs().stream().anyMatch(job -> job.getName() == JobsEnum.FALLER)){
            throw new ForbidenJobRoleAccessException(String.format("Account with id %s is not faller", faller.getId()));
        }

        double avgDiameter = LumberCalculation.getAvgDiameter(request.getTop_diameter(), request.getBottom_diameter());

        double volume = LumberCalculation.getVolumePerCubic(avgDiameter, request.getLength());

        double volumeRound = LumberCalculation.roundDown(volume);

        Tree tree = Tree.builder()
                .length(request.getLength())
                .bottomDiameter(request.getBottom_diameter())
                .topDiameter(request.getTop_diameter())
                .avgDiameter(avgDiameter)
                .volume(volumeRound)
                .locationId(locationId.toString())
                .treeTypeId(treeTypeId.toString())
                .scalerId(scalerId.toString())
                .fallerId(fallerId.toString())
                .isDeleted(false)
                .build();

        tree = treeRepositoryMongo.save(tree);

        return toFullResponse(tree);
    }

    public List<TreeResponseV2> getAllTreeFullResponse(){

        return treeRepositoryMongo.findAllByIsDeletedFalse().stream().map((this::toFullResponse)).toList();
    }

    public Page<TreeResponseV2> getAllTreeFullResponse(Pageable pageable){
        return treeRepositoryMongo.findAllByIsDeletedFalse(pageable).map(this::toFullResponse);
    }

    public TreeResponseV2 toFullResponse(Tree tree) {

        TreeTypeResponse type = treeTypeService.getById(UUID.fromString(tree.getTreeTypeId()));

        Location location = locationService.findbyId(UUID.fromString(tree.getLocationId()));

        AccountInfo scaler = accountInfoService.findbyId(UUID.fromString(tree.getScalerId()));

        AccountInfo faller = accountInfoService.findbyId(UUID.fromString(tree.getScalerId()));


        return TreeResponseV2.builder()
                .id(tree.getId())
                .type(type)
                .length(tree.getLength())
                .bottomDiameter(tree.getBottomDiameter())
                .topDiameter(tree.getTopDiameter())
                .avgDiameter(tree.getAvgDiameter())
                .volume(tree.getVolume())
                .location(location.toResponse())
                .scaller(scaler.toResponse())
                .faller(faller.toResponse())
                .build();
    }

    public TreeResponseV2 deleteTree(String id){

        Tree target = treeRepositoryMongo.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new TreeNotFoundExeption("there no tree with id " + id)
        );

        target.setDeleted(true);

        Tree updatedTree = treeRepositoryMongo.save(target);


        return toFullResponse(updatedTree);
    }

    public PagingResponse<TreeResponseV2> getDataPagging(Pageable pageable) {

        Page<TreeResponseV2> page = treeRepositoryMongo.findAll(pageable).map(this::toFullResponse);

        PagingResponse<TreeResponseV2> response = PagingResponse.<TreeResponseV2>builder()
                .size(page.getSize())
                .totalData(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .page(page.getNumber() + 1)
                .content(page.getContent())
                .build();

        return response;

    }
}
