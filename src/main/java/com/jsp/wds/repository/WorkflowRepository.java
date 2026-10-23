package com.jsp.wds.repository;

import com.jsp.wds.model.WorkflowModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface WorkflowRepository extends JpaRepository<WorkflowModel, BigInteger> {

    @Query(value = "from WorkflowModel where wfCode= :wfCode and tenantId= :tenantId")
    List<WorkflowModel> findWorkflowByWfCodeAndTenantId(String wfCode, String tenantId);

    @Query(value = "from WorkflowModel where wfCode= :wfCode and wfId= :wfId")
    WorkflowModel findWorkflowModelByWfCodeAndWfId(String wfCode, String wfId);

    @Query(value = "from WorkflowModel where wfCode=:wfCode")
    List<WorkflowModel> findWorkflowByWorkflowCode(String wfCode);

    @Query(value = "select max(workflowVersion) from WorkflowModel where wfCode=:wfCode")
    int findMaximumWorkflowVersionByWorkflowCode(String wfCode);

    @Query(value = "select bpmnXml from WorkflowModel where id=:id")
    String findBpmnXMLById(int id);
}
