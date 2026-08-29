package com.jardvcode.bot.checklist.service;

import com.jardvcode.bot.checklist.domain.overview.AssignmentOverview;
import com.jardvcode.bot.checklist.domain.overview.ProgressOverview;
import com.jardvcode.bot.checklist.domain.overview.SectionOverview;
import com.jardvcode.bot.checklist.entity.AssignmentViewEntity;
import com.jardvcode.bot.checklist.entity.SectionViewEntity;
import com.jardvcode.bot.checklist.repository.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public final class AssignmentOverviewService {

    private final AssignmentRepository repository;

    public AssignmentOverviewService(AssignmentRepository repository) {
        this.repository = repository;
    }

    public AssignmentOverview getOverview(Long id) {
        try {
            List<SectionOverview> sectionOverviews = new ArrayList<>();
            int completed = 0;
            int total = 0;

            AssignmentViewEntity assignment = repository.findWithSectionsAndItemsAndResponseById(id).orElseThrow();

            Set<SectionViewEntity> sections = assignment.getSections();

            for (SectionViewEntity section: sections) {
                int totalItems = section.totalItems();
                int respondedItems = (int) section.getItems().stream().filter(item -> item.hasResponse()).count();

                SectionOverview sectionOverview = new SectionOverview(section.getName(), respondedItems, totalItems);
                sectionOverviews.add(sectionOverview);

                completed += respondedItems;
                total += totalItems;
            }

            return new AssignmentOverview(
                    assignment.getTemplateName(),
                    assignment.getUnitNumber().toString(),
                    assignment.getOperatorFullName(),
                    assignment.getMechanicFullName(),
                    assignment.getDate(),
                    sectionOverviews,
                    new ProgressOverview(completed, total)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
