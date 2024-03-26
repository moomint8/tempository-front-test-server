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

    public ProjectTestcase addProjectTestcase(int projectId, String separate,
                                              String content, String expectedValue, String note) {

        return projectTestcaseRepository.insertTestcase(separate, content, expectedValue, "진행전", note, projectId);
    }

    public ProjectTestcase modifyProjectTestcase(int projectId, int no, String separate, String content,
                                                 String expectedValue, String result, String note) throws Exception {
        return projectTestcaseRepository.updateTestcase(projectId, no, separate, content, expectedValue, result, note);
    }

    public boolean removeTestcase(int projectId, int no) {

        return projectTestcaseRepository.deleteTestcase(projectId, no);
    }
}
