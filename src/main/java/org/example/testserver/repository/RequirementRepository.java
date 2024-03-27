package org.example.testserver.repository;

import org.example.testserver.aggregate.entity.Requirement;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class RequirementRepository {
    private final ArrayList<Requirement> requirementList = new ArrayList<>();

    public RequirementRepository() {
        requirementList.add(new Requirement(1, 1, "사용자 관리", "회원가입 기능",
                "사용자가 회원가입할 수 있는 기능을 제공해야 함",
                "회원가입 페이지에 이메일, 비밀번호, 닉네임을 입력하는 폼이 있어야 함", 1));
        requirementList.add(new Requirement(2, 2, "사용자 관리", "로그인 기능",
                "회원이 등록한 이메일과 비밀번호를 입력하여 로그인할 수 있는 기능을 제공해야 함",
                "로그인 페이지에 이메일과 비밀번호를 입력하는 폼이 있어야 함", 1));
        requirementList.add(new Requirement(3, 3, "상품 관리", "상품 목록 조회 기능",
                "사용자가 상품 목록을 조회할 수 있는 기능을 제공해야 함",
                "상품 목록 페이지에 상품 이름, 가격, 이미지 등이 표시되어야 함", 1));
        requirementList.add(new Requirement(4, 4, "상품 관리", "상품 상세 조회 기능",
                "사용자가 상품 상세 정보를 조회할 수 있는 기능을 제공해야 함",
                "상품 상세 페이지에 상품의 상세 정보와 이미지가 표시되어야 함", 1));
        requirementList.add(new Requirement(5, 5, "결제 관리", "상품 구매 기능",
                "사용자가 상품을 구매할 수 있는 기능을 제공해야 함",
                "장바구니에 담긴 상품을 결제할 수 있는 페이지로 이동할 수 있어야 함", 1));
    }


    public ArrayList<Requirement> selectRequirementByProjectId(int projectId) {
        ArrayList<Requirement> requirements = new ArrayList<>();

        for (Requirement requirement : requirementList) {
            if (requirement.getProjectId() == projectId) {
                requirements.add(requirement);
            }
        }

        return requirements;
    }

    public Requirement insertRequirement(int projectId, String separate, String name, String content, String note) {
        int id = requirementList.size() + 1;
        int no = 1;

        for (Requirement requirement : requirementList) {
            if (requirement.getProjectId() == projectId) {
                no++;
            }
        }

        Requirement requirement = new Requirement(id, no, separate, name, content, note, projectId);

        requirementList.add(requirement);

        return requirement;
    }

    public Requirement updateRequirement(int projectId, int no, String separate, String name, String content, String note) throws Exception {
        for (Requirement requirement : requirementList) {
            if (requirement.getProjectId() == projectId && requirement.getNo() == no) {
                if (separate != null) {
                    requirement.setSeparate(separate);
                }
                if (name != null) {
                    requirement.setName(name);
                }
                if (content != null) {
                    requirement.setContent(content);
                }
                if (note != null) {
                    requirement.setNote(note);
                }

                return requirement;
            }
        }

        throw new Exception();
    }


}
