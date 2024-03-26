package org.example.testserver.service;

import org.example.testserver.aggregate.entity.Project;
import org.example.testserver.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final SessionService sessionService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, SessionService sessionService) {
        this.projectRepository = projectRepository;
        this.sessionService = sessionService;
    }

    public ArrayList<Project> findMyProjects() {
        return projectRepository.selectMyProjects(sessionService.whoAmI().getId());
    }

    public Project addProject(String name, String content) {
        return projectRepository.insertProject(name, content, sessionService.whoAmI().getId());
    }

    public Project modifyProject(int projectId, String name, String status, String content) throws Exception {
        return projectRepository.updateProject(projectId, name, status, content);
    }
}
