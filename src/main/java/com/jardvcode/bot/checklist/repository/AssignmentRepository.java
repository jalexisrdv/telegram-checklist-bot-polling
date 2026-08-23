package com.jardvcode.bot.checklist.repository;

import com.jardvcode.bot.checklist.entity.AssignmentViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<AssignmentViewEntity, Long>, JpaSpecificationExecutor {

    List<AssignmentViewEntity> findByMechanicUserIdAndStatusNotOrderByDateDesc(Long mechanicUserId, String status);

    Optional<AssignmentViewEntity> findByMechanicUserIdAndOptionNumber(Long mechanicUserId, Long optionNumber);

}
