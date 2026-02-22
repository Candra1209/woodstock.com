package com.woodstock.app.specification;

import com.woodstock.app.entity.AccountInfo;
import com.woodstock.app.entity.Jobs;
import com.woodstock.app.entity.JobsEnum;
import com.woodstock.app.models.params.AccountInfoSearch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AccountInfoSpecification {
    public static Specification<AccountInfo> getSpecification(AccountInfoSearch search){

        return (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();

            if (search.getFullname() != null && !search.getFullname().isEmpty()){
                Predicate fullnamePredicate = cb.like(cb.lower(root.get("fullname")), "%"+search.getFullname().toLowerCase()+"%");

                list.add(fullnamePredicate);
            }

            if (search.getEmail() != null && !search.getEmail().isEmpty()){

                Predicate emailPredicate = cb.like(cb.lower(root.get("email")), "%"+search.getEmail().toLowerCase()+"%");

                list.add(emailPredicate);

            }

            if (search.getContact() != null && !search.getContact().isEmpty()){

                Predicate contactPredicate = cb.like(root.get("contact"), "%"+search.getContact()+"%");
                list.add(contactPredicate);
            }

            if (search.getJobs() != null && !search.getJobs().isEmpty()){

                Join<AccountInfo, Jobs> jobsJoin = root.join("jobs", JoinType.INNER);

                List<JobsEnum> enumList = search.getJobs().stream()
                        .map(String::toUpperCase)
                        .map(JobsEnum::valueOf)
                        .toList();

                Predicate isContainJobPredicate = jobsJoin.get("name").in(enumList);

                list.add(isContainJobPredicate);

            }

            return cb.and(list.toArray(new Predicate[0]));
        };

    }
}
