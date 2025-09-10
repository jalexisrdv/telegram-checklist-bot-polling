package com.jardvcode.bot.checklist.repository.instance;

import com.jardvcode.bot.checklist.entity.instance.InstanceGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstanceGroupRepository extends JpaRepository<InstanceGroupEntity, Long> {
    List<InstanceGroupEntity> findByInstanceIdOrderByOptionNumberAsc(Long id);
    Optional<InstanceGroupEntity> findByInstanceIdAndOptionNumber(Long userId, Long optionNumber);
}
