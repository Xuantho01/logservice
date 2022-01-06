package com.vpbank.logservice.repository;

import com.vpbank.logservice.model.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, String> {

    @Query(value = "SELECT * FROM VPBSddGateway.dbo.VPB_SDD_audit_log AL where AL.wi_name = :wiName order by start_time desc ", nativeQuery = true)
    List<AuditLog> getListAuditLogByWiName(@Param("wiName") String wiName);

}
