package com.jardvcode.bot.checklist.repository.instance;

import com.jardvcode.bot.checklist.entity.instance.InstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstanceRepository extends JpaRepository<InstanceEntity, Long> {
    List<InstanceEntity> findByUserIdAndStatusNotOrderByDateDesc(Long id, String status);
    Optional<InstanceEntity> findByUserIdAndOptionNumber(Long userId, Long optionNumber);
}
