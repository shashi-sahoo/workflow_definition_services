package com.jsp.wds.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "wf_node")
public class NodeDetailsModel {

    @Id
    @Column(name = "alt_key")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int altKey;

    @Column(name = "node_id")
    private String nodeId;

    @Column(name = "node_type")
    private String nodeType;

    @Column(name = "wf_id")
    private String wfId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "incoming_nodes")
    private List<String> incomingNodes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "outgoing_nodes")
    private List<String> outgoingNodes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "node_properties")
    private Map<String, Object> nodeProperties;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_date")
    private Date modifiedDate;
}
