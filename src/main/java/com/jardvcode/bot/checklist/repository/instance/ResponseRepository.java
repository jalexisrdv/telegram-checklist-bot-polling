package com.jardvcode.bot.checklist.repository.instance;

import com.jardvcode.bot.checklist.entity.instance.ResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResponseRepository extends JpaRepository<ResponseEntity, Long> {
    List<ResponseEntity> findByInstanceIdOrderByItemGroupId(Long instanceId);
    List<ResponseEntity> findByInstanceIdAndItemGroupIdOrderByOptionNumberAsc(Long instanceId, Long groupId);
    Optional<ResponseEntity> findByInstanceIdAndItemGroupIdAndOptionNumber(Long instanceId, Long groupId, Long userItemNumber);
}
