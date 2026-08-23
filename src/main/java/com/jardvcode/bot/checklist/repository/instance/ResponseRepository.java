package com.jardvcode.bot.checklist.repository.instance;

import com.jardvcode.bot.checklist.entity.instance.ResponseEntity;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

public interface ResponseRepository extends JpaRepository<ResponseEntity, Long>, JpaSpecificationExecutor {

    List<ResponseEntity> findByInstanceIdAndItemGroupIdOrderByItemOptionNumberAsc(Long assignmentId, Long sectionId);

    Optional<ResponseEntity> findByInstanceIdAndItemGroupIdAndItemOptionNumber(Long assignmentId, Long sectionId, Long optionNumber);

}
