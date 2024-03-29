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
                changeDate("2023-01-02"), changeDate("2023-01-03"), 2, 1));
        wbsList.add(new WBS(3, 3, "테이블 정의서 작성", "진행완료",
                changeDate("2023-01-03"), changeDate("2023-01-05"), 3, 1));
        wbsList.add(new WBS(4, 4, "시스템 설계", "진행완료",
                changeDate("2023-01-16"), changeDate("2023-02-05"), 1, 1));
        wbsList.add(new WBS(5, 5, "프론트엔드 개발", "진행완료",
                changeDate("2023-02-06"), changeDate("2023-03-05"), 5, 1));
        wbsList.add(new WBS(6, 6, "백엔드 개발", "진행중",
                changeDate("2023-03-06"), changeDate("2023-04-05"), 4, 1));
        wbsList.add(new WBS(7, 7, "품질 보증 및 테스트", "진행예정",
                changeDate("2023-04-06"), changeDate("2023-04-30"), 3, 1));
        wbsList.add(new WBS(8, 8, "배포 준비", "진행예정",
                changeDate("2023-05-01"), changeDate("2023-05-10"), 3, 1));
        wbsList.add(new WBS(9, 9, "운영 및 모니터링", "진행예정",
                changeDate("2023-05-11"), changeDate("2023-06-30"), 2, 1));
        wbsList.add(new WBS(10, 10, "유지보수", "진행예정",
                changeDate("2023-07-01"), changeDate("2023-12-31"), 1, 1));
        wbsList.add(new WBS(11, 11, "성능 최적화", "진행예정",
                changeDate("2023-08-01"), changeDate("2023-09-30"), 4, 1));
        wbsList.add(new WBS(12, 12, "보안 강화", "진행예정",
                changeDate("2023-10-01"), changeDate("2023-11-30"), 1, 1));
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

    public boolean deleteWBS(int projectId, int no) {

        boolean flag = false;
        WBS target = null;

        for (WBS wbs : wbsList) {
            if (flag) {
                wbs.setId(wbs.getId() - 1);
            }
            if (wbs.getProjectId() == projectId) {
                if (flag) {
                    wbs.setNo(wbs.getNo() - 1);
                } else if (wbs.getNo() == no) {
                    flag = true;
                    target = wbs;
                }
            }
        }

        wbsList.remove(target);

        return flag;
    }

    private LocalDate changeDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }
}
