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

    public Issue addIssue(int projectId, String name, String status, String content, int managerId, int writerId) {
        return projectIssueRepository.insertIssue(projectId, name, status, content, managerId, writerId);
    }

    public Issue modifyIssue(int projectId, int no, String name, String status, String content, int managerId, int writerId) {
        return projectIssueRepository.updateIssue(projectId, no, name, status, content, managerId, writerId);
    }

    public boolean removeIssue(int projectId, int no) {
        return projectIssueRepository.deleteIssue(projectId, no);
    }
}
