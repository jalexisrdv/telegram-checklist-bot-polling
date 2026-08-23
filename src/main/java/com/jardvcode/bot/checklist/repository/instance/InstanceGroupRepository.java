package com.jardvcode.bot.checklist.repository.instance;

import com.jardvcode.bot.checklist.entity.instance.InstanceGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface InstanceGroupRepository extends JpaRepository<InstanceGroupEntity, Long>, JpaSpecificationExecutor {

    List<InstanceGroupEntity> findByAssignmentIdOrderByOptionNumberAsc(Long assignmentId);

    Optional<InstanceGroupEntity> findByAssignmentIdAndOptionNumber(Long assignmentId, Long optionNumber);

}
