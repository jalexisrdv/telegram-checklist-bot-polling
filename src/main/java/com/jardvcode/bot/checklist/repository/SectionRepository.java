package com.jardvcode.bot.checklist.repository;

import com.jardvcode.bot.checklist.domain.StatusEnum;
import com.jardvcode.bot.checklist.entity.SectionViewEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<SectionViewEntity, Long>, JpaSpecificationExecutor {

    List<SectionViewEntity> findByAssignmentIdOrderByOptionNumberAsc(Long assignmentId);

    Optional<SectionViewEntity> findByAssignmentIdAndOptionNumber(Long assignmentId, Long optionNumber);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
            UPDATE
                assignment_sections sections
            SET
                status = :status
            WHERE
                sections.assignment_id = :assignmentId
                AND NOT EXISTS (
                    SELECT
                        1
                    FROM
                        assignment_responses responses
                        INNER JOIN assignment_items AS items ON items.id = responses.item_id
                    WHERE
                        responses.assignment_id = sections.assignment_id
                        AND items.section_id = sections.id
                        AND responses.status IS NULL
                )
            """)
    void updateStatus(Long assignmentId, String status);

}
