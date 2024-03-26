package org.example.testserver.service;

import org.example.testserver.aggregate.entity.Requirement;
import org.example.testserver.repository.RequirementRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RequirementService {
    private final RequirementRepository requirementRepository;

    public RequirementService(RequirementRepository requirementRepository) {
        this.requirementRepository = requirementRepository;
    }

    public ArrayList<Requirement> findRequirementByProjectId(int projectId) {
        return requirementRepository.selectRequirementByProjectId(projectId);
    }
}
