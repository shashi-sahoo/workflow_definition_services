package com.jsp.wds.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "wf_workflow")
@Data
public class WorkflowModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "wf_id")
    private String wfId;

    @Column(name = "wf_code")
    private String wfCode;

    @Column(name = "workflow_name")
    private String workflowName;

    @Column(name = "workflow_version")
    private int workflowVersion;

    @Column(name = "status_flag")
    private String statusFlag;

    @Column(name = "wf_description")
    private String wfDescription;

    @Column(name = "wf_type_code")
    private String wfTypeCode;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "unique_field")
    private int uniqueField;

    @Column(name = "bpmn_xml")
    private String bpmnXml;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_date")
    @Temporal(TemporalType.DATE)
    private Date modifiedDate;

}
