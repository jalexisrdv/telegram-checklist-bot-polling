package com.jardvcode.bot.checklist.repository.template;

import com.jardvcode.bot.checklist.entity.template.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findByGroupId(Long groupId);
}
