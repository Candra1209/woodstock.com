package com.woodstock.app.specification;

import com.woodstock.app.entity.TreeType;
import com.woodstock.app.models.params.TreeTypeSearch;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.LinkedList;
import java.util.List;

@Slf4j
public class TreeTypeSpecification {

    public static Specification<TreeType> getSpecification(TreeTypeSearch treeTypeSearch){
        return (root, query, criteriaBuilder) -> {
            log.trace("get tree-type specification");
            List<Predicate> listPredicate =new LinkedList<>();

            if (treeTypeSearch.getName() != null && !treeTypeSearch.getName().isEmpty()) {

                log.info("build criteria builder for field name contains");
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%"+treeTypeSearch.getName().toLowerCase()+"%" );

                listPredicate.add(namePredicate);

            }

            if (treeTypeSearch.getCode() != null && !treeTypeSearch.getCode().isEmpty()){

                log.info("build criteria builder for field typeCode contains");
                Predicate codePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("typeCode")), "%"+treeTypeSearch.getCode().toLowerCase()+"%" );

                listPredicate.add(codePredicate);

            }

            if (treeTypeSearch.getInclude_deleted() != null && !treeTypeSearch.getInclude_deleted()){
                log.info("include_deleted = false");
                Predicate includeDeleted = criteriaBuilder.isFalse(root.get("isDeleted"));

                listPredicate.add(includeDeleted);

            }else{
                log.info("include_deleted = true");
            }

//

            return criteriaBuilder.and(listPredicate.toArray(new Predicate[0]));
        };
    }

}
