package org.example.testserver.service;

import org.example.testserver.aggregate.entity.WBS;
import org.example.testserver.repository.ProjectWBSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProjectWBSService {

    private final ProjectWBSRepository projectWBSRepository;

    @Autowired
    public ProjectWBSService(ProjectWBSRepository projectWBSRepository) {
        this.projectWBSRepository = projectWBSRepository;
    }

    public ArrayList<WBS> findWBSListByProjectId(int projectId) {
        return projectWBSRepository.selectWBSByProjectId(projectId);
    }
}
