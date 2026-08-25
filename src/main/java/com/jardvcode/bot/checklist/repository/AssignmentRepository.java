package com.jardvcode.bot.checklist.repository;

import com.jardvcode.bot.checklist.entity.AssignmentViewEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<AssignmentViewEntity, Long>, JpaSpecificationExecutor {

    List<AssignmentViewEntity> findByMechanicUserIdAndStatusNotOrderByDateDesc(Long mechanicUserId, String status);

    Optional<AssignmentViewEntity> findByMechanicUserIdAndOptionNumber(Long mechanicUserId, Long optionNumber);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
            UPDATE
                checklist_assignments assignment
            SET
                status = 'COMPLETADO'
            WHERE
                assignment.id = :assignmentId
                AND NOT EXISTS (
                    SELECT
                        1
                    FROM
                        assignment_responses response
                    WHERE
                        response.assignment_id = assignment.id AND response.status IS NULL
                )
            """)
    void updateStatus(Long assignmentId);

}
