package org.example.testserver.controller;

import org.example.testserver.aggregate.entity.Requirement;
import org.example.testserver.aggregate.vo.requirement.RequestRequirementVO;
import org.example.testserver.aggregate.vo.requirement.ResponseRequirementListVO;
import org.example.testserver.aggregate.vo.requirement.ResponseRequirementVO;
import org.example.testserver.service.RequirementService;
import org.example.testserver.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/requirement")
public class RequirementController {

    private final RequirementService requirementService;
    private final SessionService sessionService;

    public RequirementController(RequirementService requirementService, SessionService sessionService) {
        this.requirementService = requirementService;
        this.sessionService = sessionService;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ResponseRequirementListVO> findRequirementListByProjectId(
            @PathVariable("projectId") String projectId) {

        ResponseRequirementListVO response = new ResponseRequirementListVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ArrayList<Requirement> requirementList = requirementService.findRequirementByProjectId(Integer.parseInt(projectId));
        ArrayList<ResponseRequirementVO> requirementVOS = new ArrayList<>();

        for (Requirement requirement : requirementList) {
            requirementVOS.add(changeRequirementToResponseRequirementVO(requirement));
        }

        response.setMessage("조회 성공");
        response.setRequirementVOList(requirementVOS);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add/{projectId}")
    public ResponseEntity<ResponseRequirementVO> addRequirement(@PathVariable("projectId") String projectId,
                                                                @RequestBody RequestRequirementVO request) {
        ResponseRequirementVO response = new ResponseRequirementVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response = changeRequirementToResponseRequirementVO(
                requirementService.addRequirement(Integer.parseInt(projectId), request.getSeparate(), request.getName(),
                        request.getContent(), request.getNote()));

        response.setMessage("추가 성공");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/modify/{projectId}")
    public ResponseEntity<ResponseRequirementVO> modifyRequirement(@PathVariable("projectId") String projectId,
                                                                   @RequestBody RequestRequirementVO request) {
        ResponseRequirementVO response = new ResponseRequirementVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            response = changeRequirementToResponseRequirementVO(requirementService.modifyRequirement(
                    Integer.parseInt(projectId), request.getNo(), request.getSeparate(), request.getName(), request.getContent(), request.getNote()
            ));
            response.setMessage("수정 성공");

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            response.setMessage("수정 실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    private ResponseRequirementVO changeRequirementToResponseRequirementVO(Requirement requirement) {
        ResponseRequirementVO requirementVO = new ResponseRequirementVO();

        requirementVO.setId(requirement.getId());
        requirementVO.setNo(requirement.getNo());
        requirementVO.setSeparate(requirement.getSeparate());
        requirementVO.setName(requirement.getName());
        requirementVO.setContent(requirement.getContent());
        requirementVO.setNote(requirement.getNote());
        requirementVO.setProjectId(requirement.getProjectId());

        return requirementVO;
    }
}
