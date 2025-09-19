package com.jardvcode.bot.checklist.repository.instance;

import com.jardvcode.bot.checklist.entity.instance.ResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResponseRepository extends JpaRepository<ResponseEntity, Long> {
    List<ResponseEntity> findByInstanceIdOrderByGroupId(Long instanceId);
    List<ResponseEntity> findByInstanceIdAndGroupIdOrderByOptionNumberAsc(Long instanceId, Long groupId);
    Optional<ResponseEntity> findByInstanceIdAndGroupIdAndOptionNumber(Long instanceId, Long groupId, Long userItemNumber);
}
