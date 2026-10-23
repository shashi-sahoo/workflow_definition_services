package com.jsp.wds.service;

import com.jsp.wds.dto.AppResponseDTO;
import com.jsp.wds.dto.WorkflowDTO;
import com.jsp.wds.model.NodeDetailsModel;
import com.jsp.wds.model.WorkflowModel;
import com.jsp.wds.parser.BpmnParser;
import com.jsp.wds.repository.NodeDetailsRepository;
import com.jsp.wds.repository.WorkflowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.parsers.SAXParser;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {

    private WorkflowRepository workflowRepository;
    private NodeDetailsRepository nodeDetailsRepository;
    private SAXParser xmlParser;
    private BpmnParser bpmnParser;

    @Override
    public AppResponseDTO processUpdateModelData(WorkflowDTO workflowDTO) {
        AppResponseDTO appResponseDTO = null;
        WorkflowModel model1 = null;

        try {
            List<WorkflowModel> models = workflowRepository.findWorkflowByWfCodeAndTenantId(workflowDTO.getWorkFlowCode(), workflowDTO.getTenantId());
            WorkflowModel draftModel = null;

            for (WorkflowModel model : models) {
                if (model.getStatusFlag().equals("DRAFT")) {
                    draftModel = model;
                    break;
                }
            }

            if (draftModel != null && draftModel.getStatusFlag().equals("DRAFT")) {
                draftModel.setEntityName(workflowDTO.getEntityName());
                draftModel.setUniqueField(workflowDTO.getUniqueField());
                draftModel.setBpmnXml(workflowDTO.getBpmnXml());

//                draftModel.setEntityName("Shashi Sahoo");
//                draftModel.setUniqueField(102);
//                draftModel.setBpmnXml("<?xml version= '1.0' encoding= 'UTF-8 ?>' ");
                workflowRepository.save(draftModel);
            } else {

                model1 = new WorkflowModel();

                model1.setTenantId(workflowDTO.getTenantId());
                model1.setWfId(workflowDTO.getWorkFlowCode() + "_" + 0);
                model1.setWfCode(workflowDTO.getWorkFlowCode());
                model1.setWorkflowName("Sample");
                model1.setWorkflowVersion(0);
                model1.setStatusFlag("DRAFT");
                model1.setWfDescription(workflowDTO.getWorkFlowDescription());
                model1.setWfTypeCode(workflowDTO.getWorkFlowTypeCode());
                model1.setEntityName(workflowDTO.getEntityName());
                model1.setUniqueField(workflowDTO.getUniqueField());
                model1.setBpmnXml(workflowDTO.getBpmnXml());
                model1.setCreatedBy("Shashi");
                model1.setCreatedDate(new Date());
                model1.setModifiedBy("Shashi");
                model1.setModifiedDate(new Date());

                workflowRepository.save(model1);
            }

            appResponseDTO = new AppResponseDTO("SUCCESS", "200", model1 == null ? draftModel : model1, null);

        } catch (Exception e) {
            appResponseDTO = new AppResponseDTO("FAILURE", "500", null, e.getMessage());
        }
        return appResponseDTO;
    }

    @Override
    public AppResponseDTO processActivate(String wfCode, String wfId) {
        AppResponseDTO appResponseDTO = null;
        WorkflowModel model = null;

        try {
            model = workflowRepository.findWorkflowModelByWfCodeAndWfId(wfCode, wfId);

            List<WorkflowModel> modelList = workflowRepository.findWorkflowByWorkflowCode(wfCode);

            if (model.getStatusFlag().equals("DRAFT")) {

                for (WorkflowModel m : modelList) {

                    if (m.getStatusFlag().equals("ACTIVE")) {

                        m.setStatusFlag("INACTIVE");
                        workflowRepository.save(m);
                        break;
                    }
                }

                int maxVersion = workflowRepository.findMaximumWorkflowVersionByWorkflowCode(wfCode);

                model.setStatusFlag("ACTIVE");
                model.setWorkflowVersion(maxVersion + 1);
                model.setWfId(model.getWfCode() + "_" + model.getWorkflowVersion());

                workflowRepository.save(model);

            } else if (model.getStatusFlag().equals("INACTIVE")) {

                for (WorkflowModel m : modelList) {

                    if (m.getStatusFlag().equals("ACTIVE")) {

                        m.setStatusFlag("INACTIVE");
                        workflowRepository.save(m);
                        break;

                    }
                }

                model.setStatusFlag("ACTIVE");

            }

            // ========================== Node creation phase ==========================

            // get the list of all the workflow IDs
            List<String> workFlowIds = nodeDetailsRepository.findAllNodes();
            //System.out.println("IDs: " + workFlowIds);

            String id = model.getWfId();
            // no nodes exist
            if(workFlowIds == null || workFlowIds.isEmpty()) {
                // add a new node into the table
                xmlParser.parse(new ByteArrayInputStream(model.getBpmnXml().getBytes(StandardCharsets.UTF_8)), bpmnParser); // [Credit]: https://stackoverflow.com/a/782183
                List<NodeDetailsModel> nodes = bpmnParser.getNodeList();


                nodes.stream()
                        .map(node -> { node.setWfId(id); return node;})
                        .forEach(node -> nodeDetailsRepository.save(node));

            } else {

                // checking if a node exists for the given workflow code
                List<String> nodesOfThisWorkflowCode =
                        workFlowIds.stream()
                                .filter(code -> code.equals(wfCode.split("_")[0]))
                                .collect(Collectors.toList());
                if(nodesOfThisWorkflowCode == null) { // if no node for the given workflow code doesn't exist then
                    // add a new node for this workflow code
                    xmlParser.parse(new ByteArrayInputStream(model.getBpmnXml().getBytes(StandardCharsets.UTF_8)), bpmnParser);
                    List<NodeDetailsModel> nodes = bpmnParser.getNodeList();

                    nodes.stream()
                            .map(node -> {node.setWfId(id); return node;})
                            .forEach(node -> nodeDetailsRepository.save(node));

                }
            }

            appResponseDTO = new AppResponseDTO("SUCCESS", "200", model, null);
        } catch (Exception e) {
            appResponseDTO = new AppResponseDTO("FAILURE", "500", null, e.getMessage());
        }
        return appResponseDTO;
    }

    @Override
    public String processGetBpmnXML(int id) {
        return workflowRepository.findBpmnXMLById(id);
    }
}
