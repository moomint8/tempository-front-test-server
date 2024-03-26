package org.example.testserver.service;

import org.example.testserver.aggregate.entity.WBS;
import org.example.testserver.repository.WBSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WBSService {

    private final WBSRepository WBSRepository;

    @Autowired
    public WBSService(WBSRepository WBSRepository) {
        this.WBSRepository = WBSRepository;
    }

    public ArrayList<WBS> findWBSListByProjectId(int projectId) {
        return WBSRepository.selectWBSByProjectId(projectId);
    }

    public WBS addWBS(int projectId, String content, String status, String startDate, String endDate, int managerId) {
        return WBSRepository.insertWBS(projectId, content, status, startDate, endDate, managerId);
    }

    public WBS modifyWBS(int projectId, int no, String content, String status,
                         String startDate, String endDate, int managerId) throws Exception {

        return WBSRepository.updateWBS(projectId, no, content, status, startDate, endDate, managerId);
    }

    public boolean removeWBS(int projectId, int no) {
        return WBSRepository.deleteWBS(projectId, no);
    }
}
