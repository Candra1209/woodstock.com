package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.*;
import com.woodstock.app.models.request.tree.TreeRequest;
import com.woodstock.app.models.response.tree.TreeResponse;
import com.woodstock.app.repositorty.TreeRepository;
import com.woodstock.app.utils.calculation.LumberCalculation;
import com.woodstock.app.utils.exception.account.AccountUserNotFoundException;
import com.woodstock.app.utils.exception.global.InvalidJobAssignmentException;
import com.woodstock.app.utils.exception.jobs.ForbidenJobRoleAccessException;
import com.woodstock.app.utils.exception.location.LocationNotFoundException;
import com.woodstock.app.utils.exception.tree_type.TreeTypeNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import static java.rmi.server.LogStream.log;

@Service
@Slf4j
public class TreeServiceImpl extends BaseServiceImpl<TreeRepository, Tree>{

    private final TreeTypeServiceV2 treeTypeService;
    private final AccountInfoServiceImpl accountInfoService;
    private final AccountServiceImpl accountService;
    private final LocationService locationService;

    protected TreeServiceImpl(TreeRepository repository, EntityManager entityManager, TreeTypeServiceV2 treeTypeService, AccountInfoServiceImpl accountInfoService, AccountServiceImpl accountService, LocationService locationService) {
        super(repository, entityManager);
        this.treeTypeService = treeTypeService;
        this.accountInfoService = accountInfoService;
        this.accountService = accountService;
        this.locationService = locationService;
    }

    @Transactional
    public Tree saveNewTree(TreeRequest request, String username){

        //check access permision
        log.info("check if account {} have access to add new tree", username);
        if(!isHaveAccess(username)){
            log.warn("account with username {} do not have access to create new tree", username);
            throw new ForbidenJobRoleAccessException("invalid job access : only scaller or admin can add new tree");
        }

        //find account
        log.info("find account with id : {}", request.getScaller_id());
        UUID scallerId = UUID.fromString(request.getScaller_id());
        AccountInfo scaller = accountInfoService.findbyId(scallerId);


        //check if account job is valid
        log.info("check if account with id : {} has role as scaller", request.getScaller_id());
        boolean isScaller = scaller.getJobs().stream().anyMatch(job -> job.getName() == JobsEnum.SCALLER);

        if (!isScaller){
            log.warn("account with username {} do not job as scaller", scaller.getAccount().getUsername());
            throw new InvalidJobAssignmentException("please enter id with account who already assign as scaller");
        }

        log.info("find account with id : {}", request.getScaller_id());
        UUID fallerId = UUID.fromString(request.getFaller_id());
        AccountInfo faller = accountInfoService.findbyId(fallerId);

        log.info("check if account with id : {} has role as faller", request.getScaller_id());
        boolean isFaller = faller.getJobs().stream().anyMatch(job -> job.getName() == JobsEnum.FALLER);

        if (!isFaller){
            log.warn("account with username {} do not job as scaller", faller.getAccount().getUsername());
            throw new InvalidJobAssignmentException("please enter id with account who already assign as faller");
        }

        //find tree type

        log.info("find tree type by id {}", request.getTree_type_id());
        UUID typeId = UUID.fromString(request.getTree_type_id());
        TreeType treeType = treeTypeService.findbyId(typeId);

        //find location
        log.info("find location by id {}", request.getLocation_id());
        UUID locationId = UUID.fromString(request.getLocation_id());
        Location location = locationService.findbyId(locationId);

        //calculate volume
        double avgDiameter = LumberCalculation.getAvgDiameter(request.getBottom_diameter(), request.getTop_diameter());
        log.info("calculate average diameter : {}", avgDiameter);

        double volume = LumberCalculation.getVolumePerCubic(avgDiameter, request.getLength());
        log.info("calculate volume per cubic : {}", volume);

        double volumeFix = LumberCalculation.roundDown(volume);
        log.info("round down volume to {}", volumeFix);

        //generate tree
        log.info("generate new tree");
        Tree newTree = Tree.builder()
                .scaller(scaller)
                .faller(faller)
                .location(location)
                .treeType(treeType)
                .length(request.getLength())
                .topDiameter(request.getTop_diameter())
                .bottomDiameter(request.getBottom_diameter())
                .avgDiameter(avgDiameter)
                .volume(volumeFix)
                .build();

        //save
        log.info("persist new tree to database");
        return save(newTree);
    }

    public Tree deleteTree(UUID id, String username) {

       if(!isHaveAccess(username)) {
           throw new ForbidenJobRoleAccessException("invalid job access : only scaller or admin can deleted tree");
       }
           return delete(id);

    }

    @Transactional
    public Tree updateTree(UUID id, TreeRequest request, String username){

        log.info("check if account requesting have access to updated tree");
        if (!isHaveAccess(username)) {
            log.warn("account {} do not have access to update tree", username);
            throw new ForbidenJobRoleAccessException("invalid job access : only scaller or admin can updated tree");
        }

        log.info("trying get tree with id {}", id);
        Tree tree = findbyId(id);

        double avgDiameter = LumberCalculation.getAvgDiameter(request.getBottom_diameter(), request.getTop_diameter());

        double volume = LumberCalculation.getVolumePerCubic(avgDiameter, request.getLength());

        double volumeFix = LumberCalculation.roundDown(volume);

        log.info("new avg diameter : {}, new volume : {}, for tree id : {}",avgDiameter, volumeFix, tree.getId());

        log.info("try updating tree with new value");
        tree.setLength(request.getLength());
        tree.setBottomDiameter(request.getBottom_diameter());
        tree.setTopDiameter(request.getTop_diameter());
        tree.setAvgDiameter(avgDiameter);
        tree.setVolume(volumeFix);

        log.info(" try get tree type with id {}", request.getTree_type_id());
        UUID treeId = UUID.fromString(request.getTree_type_id());
        TreeType type = treeTypeService.findbyId(treeId);
        log.info("new type tree : {}", type.toString());
        tree.setTreeType(type);

        log.info("try get location with id {}", request.getLocation_id());
        UUID locationId = UUID.fromString(request.getLocation_id());
        Location location = locationService.findbyId(locationId);
        log.info("new location : {}", location.toString());
        tree.setLocation(location);


        log.info("try get scaller with id {}", request.getScaller_id());
        UUID scallerId = UUID.fromString(request.getScaller_id());
        AccountInfo scaller = accountInfoService.findbyId(scallerId);
        log.info("new scaller : {}", scaller.toString());
        tree.setScaller(scaller);

        log.info("try get faller with id {}", request.getFaller_id());
        UUID fallerId = UUID.fromString(request.getFaller_id());
        AccountInfo faller = accountInfoService.findbyId(fallerId);
        log.info("new scaller : {}",faller.toString());
        tree.setFaller(faller);

        Tree updatedTree = repository.save(tree);

        return updatedTree;
    }

    private boolean isHaveAccess(String username){

        //make sure only role admin and user with scaller job can manipulate data

        Account account = accountService.findByUsername(username);
        boolean isAdmin = account.getRoles().stream().anyMatch(roles -> roles.getName() == RoleEnum.ROLE_ADMIN);

        if (!isAdmin) {
            AccountInfo scaller = accountInfoService.getAccountInfoByAccountUsername(account.getUsername());
            boolean isScaller = scaller.getJobs().stream().anyMatch(jobs -> jobs.getName() == JobsEnum.SCALLER);

            if (!isScaller) {
                return false;
            }

        }

        return true;

    }

}
