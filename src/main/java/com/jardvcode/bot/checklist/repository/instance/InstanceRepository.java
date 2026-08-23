package com.jardvcode.bot.checklist.repository.instance;

import com.jardvcode.bot.checklist.entity.instance.InstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface InstanceRepository extends JpaRepository<InstanceEntity, Long>, JpaSpecificationExecutor {

    List<InstanceEntity> findByUserIdAndStatusNotOrderByDateDesc(Long userId, String status);

    Optional<InstanceEntity> findByUserIdAndOptionNumber(Long userId, Long optionNumber);

}
