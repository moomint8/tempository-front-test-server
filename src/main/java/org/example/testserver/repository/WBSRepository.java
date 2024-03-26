package org.example.testserver.repository;

import org.example.testserver.aggregate.entity.WBS;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Repository
public class WBSRepository {
    private final ArrayList<WBS> wbsList = new ArrayList<>();

    public WBSRepository() {
        wbsList.add(new WBS(1, 1, "요구사항분석", "진행완료",
                changeDate("2023-01-01"), changeDate("2023-01-02"), 1, 1));
        wbsList.add(new WBS(2, 2, "요구사항 명세서 작성", "진행완료",
                changeDate("2023-01-02"), changeDate("2023-01-03"), 1, 1));
        wbsList.add(new WBS(3, 3, "테이블 정의서 작성", "진행완료",
                changeDate("2023-01-03"), changeDate("2023-01-05"), 1, 1));
        wbsList.add(new WBS(4, 4, "시스템 설계", "진행완료",
                changeDate("2023-01-16"), changeDate("2023-02-05"), 1, 1));
        wbsList.add(new WBS(5, 5, "프론트엔드 개발", "진행완료",
                changeDate("2023-02-06"), changeDate("2023-03-05"), 1, 1));
        wbsList.add(new WBS(6, 6, "백엔드 개발", "진행중",
                changeDate("2023-03-06"), changeDate("2023-04-05"), 1, 1));
        wbsList.add(new WBS(7, 7, "품질 보증 및 테스트", "진행예정",
                changeDate("2023-04-06"), changeDate("2023-04-30"), 1, 1));
    }

    public ArrayList<WBS> selectWBSByProjectId(int projectId) {
        ArrayList<WBS> result = new ArrayList<>();

        for (WBS wbs : wbsList) {
            if (wbs.getProjectId() == projectId) {
                result.add(wbs);
            }
        }

        return result;
    }

    public WBS insertWBS(int projectId, String content, String status, String startDate, String endDate, int managerId) {

        int id = wbsList.size() + 1;
        int no = 1;

        for (WBS wbs : wbsList) {
            if (wbs.getProjectId() == projectId) {
                no++;
            }
        }

        WBS newWBS = new WBS(id, no, content, status, changeDate(startDate), changeDate(endDate), managerId, projectId);
        wbsList.add(newWBS);

        return newWBS;
    }

    public WBS updateWBS(int projectId, int no, String content, String status, String startDate, String endDate,
                         int managerId) throws Exception {
        for (WBS wbs : wbsList) {
            if (wbs.getProjectId() == projectId && wbs.getNo() == no) {
                if (content != null) {
                    wbs.setContent(content);
                }
                if (status != null) {
                    wbs.setStatus(status);
                }
                if (startDate != null) {
                    wbs.setStartDate(changeDate(startDate));
                }
                if (endDate != null) {
                    wbs.setEndDate(changeDate(endDate));
                }
                if (managerId != 0) {
                    wbs.setManagerId(managerId);
                }

                return wbs;
            }
        }

        throw new Exception();
    }

    private LocalDate changeDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }
}
