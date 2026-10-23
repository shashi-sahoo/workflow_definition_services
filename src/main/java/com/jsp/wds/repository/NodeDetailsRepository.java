package com.jsp.wds.repository;

import com.jsp.wds.model.NodeDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NodeDetailsRepository extends JpaRepository<NodeDetailsModel, Integer> {

    @Query(value = "select wfId from NodeDetailsModel")
    List<String> findAllNodes();
}
