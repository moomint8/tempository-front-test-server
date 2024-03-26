package org.example.testserver.service;

import org.example.testserver.aggregate.entity.Issue;
import org.example.testserver.repository.ProjectIssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProjectIssueService {

    private final ProjectIssueRepository projectIssueRepository;

    @Autowired
    public ProjectIssueService(ProjectIssueRepository projectIssueRepository) {
        this.projectIssueRepository = projectIssueRepository;
    }

    public ArrayList<Issue> findIssueByProjectId(int projectId) {
        return projectIssueRepository.selectIssueByProjectId(projectId);
    }
}
