package org.example.testserver.service;

import org.example.testserver.aggregate.entity.Testcase;
import org.example.testserver.repository.TestcaseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TestcaseService {
    private final TestcaseRepository testcaseRepository;

    public TestcaseService(TestcaseRepository testcaseRepository) {
        this.testcaseRepository = testcaseRepository;
    }

    public ArrayList<Testcase> findProjectTestcaseByProjectId(int projectId) {
        return testcaseRepository.selectProjectTestcaseByProjectId(projectId);
    }

    public Testcase addProjectTestcase(int projectId, String separate,
                                       String content, String expectedValue, String note) {

        return testcaseRepository.insertTestcase(separate, content, expectedValue, "진행전", note, projectId);
    }

    public Testcase modifyProjectTestcase(int projectId, int no, String separate, String content,
                                          String expectedValue, String result, String note) throws Exception {
        return testcaseRepository.updateTestcase(projectId, no, separate, content, expectedValue, result, note);
    }

    public boolean removeTestcase(int projectId, int no) {

        return testcaseRepository.deleteTestcase(projectId, no);
    }
}
