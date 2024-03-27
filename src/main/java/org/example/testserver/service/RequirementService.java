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

    public Requirement addRequirement(int projectId, String separate, String name, String content, String note) {
        return requirementRepository.insertRequirement(projectId, separate, name, content, note);
    }

    public Requirement modifyRequirement(int projectId, int no, String separate, String name, String content, String note) throws Exception {
        return requirementRepository.updateRequirement(projectId, no, separate, name, content, note);
    }

    public boolean removeRequirement(int projectId, int no) {
        return requirementRepository.removeRequirement(projectId, no);
    }
}
