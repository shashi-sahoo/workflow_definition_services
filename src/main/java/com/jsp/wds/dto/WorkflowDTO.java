package com.jsp.wds.dto;

import lombok.Data;

@Data
public class WorkflowDTO {

    private int uniqueField;
    private String tenantId;
    private String entityName;
    private String workFlowTypeCode;
    private String workFlowDescription;
    private String workFlowCode;
    private String bpmnXml;
    private String statusFlag;

}
