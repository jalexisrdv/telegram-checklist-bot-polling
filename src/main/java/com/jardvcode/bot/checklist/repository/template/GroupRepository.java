package com.jardvcode.bot.checklist.repository.template;

import com.jardvcode.bot.checklist.entity.template.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    List<GroupEntity> findByTemplateId(Long id);
}
