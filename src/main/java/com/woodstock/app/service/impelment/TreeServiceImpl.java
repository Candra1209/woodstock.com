package com.woodstock.app.service.impelment;

import com.woodstock.app.entity.*;
import com.woodstock.app.models.request.tree.TreeRequest;
import com.woodstock.app.repositorty.TreeRepository;
import com.woodstock.app.utils.exception.global.InvalidJobAssignmentException;
import com.woodstock.app.utils.exception.jobs.ForbidenJobRoleAccessException;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

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

    public Tree saveNewTree(TreeRequest request){

        //find account

        UUID scallerId = UUID.fromString(request.getScaller_id());
        AccountInfo scaller = accountInfoService.findbyId(scallerId);

        //check if account job is valid
        boolean isScaller = scaller.getJobs().stream().anyMatch(job -> job.getName() == JobsEnum.SCALLER);

        if (!isScaller){
            throw new InvalidJobAssignmentException("please enter id with account who already assign as scaller");
        }

        UUID fallerId = UUID.fromString(request.getFaller_id());
        AccountInfo faller = accountInfoService.findbyId(fallerId);
        boolean isFaller = faller.getJobs().stream().anyMatch(job -> job.getName() == JobsEnum.FALLER);

        if (!isFaller){
            throw new InvalidJobAssignmentException("please enter id with account who already assign as faller");
        }

        //find tree type

        UUID typeId = UUID.fromString(request.getTree_type_id());
        TreeType treeType = treeTypeService.findbyId(typeId);

        //find location
        UUID locationId = UUID.fromString(request.getLocation_id());
        Location location = locationService.findbyId(locationId);

        //calculate volume

        double avgDiameter = request.getBottom_diameter() + request.getTop_diameter() / 2;

        double volume = (0.7854 * (avgDiameter * avgDiameter) * request.getLength())/10000;

        double volumeFix = new BigDecimal(volume).setScale(3, RoundingMode.HALF_DOWN).doubleValue();

        //generate tree
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
        return save(newTree);
    }

    public Tree deleteTree(UUID id, String username) {

        Account account = accountService.findByUsername(username);

        boolean isAdmin = account.getRoles().stream().anyMatch(roles -> roles.getName() == RoleEnum.ROLE_ADMIN);

        if (!isAdmin) {
            AccountInfo scaller = accountInfoService.getAccountInfoByAccountUsername(account.getUsername());
            boolean isScaller = scaller.getJobs().stream().anyMatch(jobs -> jobs.getName() == JobsEnum.SCALLER);

            if (!isScaller) {
                throw new ForbidenJobRoleAccessException("invalid job access : only scaller or admin can deleted tree");
            }

        }
        return delete(id);

    }

}
