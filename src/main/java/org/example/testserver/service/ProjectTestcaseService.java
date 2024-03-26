package org.example.testserver.service;

import org.example.testserver.aggregate.entity.ProjectTestcase;
import org.example.testserver.repository.ProjectTestcaseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProjectTestcaseService {
    private final ProjectTestcaseRepository projectTestcaseRepository;

    public ProjectTestcaseService(ProjectTestcaseRepository projectTestcaseRepository) {
        this.projectTestcaseRepository = projectTestcaseRepository;
    }

    public ArrayList<ProjectTestcase> findProjectTestcaseByProjectId(int projectId) {
        return projectTestcaseRepository.selectProjectTestcaseByProjectId(projectId);
    }
}
