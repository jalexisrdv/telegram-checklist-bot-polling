package com.jardvcode.bot.checklist.repository;

import com.jardvcode.bot.checklist.entity.SectionViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<SectionViewEntity, Long>, JpaSpecificationExecutor {

    List<SectionViewEntity> findByAssignmentIdOrderByOptionNumberAsc(Long assignmentId);

    Optional<SectionViewEntity> findByAssignmentIdAndOptionNumber(Long assignmentId, Long optionNumber);

}
