package org.example.testserver.service;

import org.example.testserver.aggregate.entity.Issue;
import org.example.testserver.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class IssueService {

    private final IssueRepository issueRepository;

    @Autowired
    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public ArrayList<Issue> findIssueByProjectId(int projectId) {
        return issueRepository.selectIssueByProjectId(projectId);
    }

    public Issue addIssue(int projectId, String name, String status, String content, int managerId, int writerId) {
        return issueRepository.insertIssue(projectId, name, status, content, managerId, writerId);
    }

    public Issue modifyIssue(int projectId, int no, String name, String status, String content, int managerId, int writerId) {
        return issueRepository.updateIssue(projectId, no, name, status, content, managerId, writerId);
    }

    public boolean removeIssue(int projectId, int no) {
        return issueRepository.deleteIssue(projectId, no);
    }
}
