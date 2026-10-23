package com.jsp.wds.service;

import com.jsp.wds.dto.AppResponseDTO;
import com.jsp.wds.dto.WorkflowDTO;

import java.util.List;

public interface WorkflowService {


    AppResponseDTO processUpdateModelData(WorkflowDTO workflowDTO);

    AppResponseDTO processActivate(String wfCode, String wfId);

    String processGetBpmnXML(int id);
}
