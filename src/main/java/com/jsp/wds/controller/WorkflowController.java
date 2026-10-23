package com.jsp.wds.controller;

import com.jsp.wds.dto.AppResponseDTO;
import com.jsp.wds.dto.WorkflowDTO;
import com.jsp.wds.service.WorkflowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/definition")
public class WorkflowController {

    WorkflowService workflowService;

    @PostMapping(value = "/activate/{wfCode}/{wfId}")
    public @ResponseBody AppResponseDTO activate(@PathVariable("wfCode") String wfCode, @PathVariable("wfId") String wfId) {
        return workflowService.processActivate(wfCode, wfId);
    }

    @PostMapping(value = "/insertData")
    public @ResponseBody AppResponseDTO updateWorkFlowModel(@RequestBody WorkflowDTO workflowDTO) {
        return workflowService.processUpdateModelData(workflowDTO);
    }

    @GetMapping(value = "/getxml/{id}")
    public @ResponseBody String getBpmnXML(@PathVariable("id") int id) {
        return workflowService.processGetBpmnXML(id);
    }

}
