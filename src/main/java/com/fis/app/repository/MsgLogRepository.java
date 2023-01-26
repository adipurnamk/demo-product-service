package com.fis.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fis.app.entity.MsgLog;

@Repository
public interface MsgLogRepository extends JpaRepository<MsgLog,Long >{

	@Modifying
	@Query("update MsgLog a set a.status=:status, a.err=:err, a.count=:count, a.lastUpdateDate=now() where a.id=:id")
	public void updateData(Long id, String status, String err, Integer count);

	@Modifying
	@Query("update MsgLog a set a.status=:status, a.lastUpdateDate=now() where a.id=:id")
	public void updateData(Long id, String status);
	
	@Query("select o from MsgLog o where o.status=:status and o.count<=:count")
	public List<MsgLog> getMsgLogByStatus(String status, Integer count);
	
	@Query("select o from MsgLog o where o.status=:status ")
	public List<MsgLog> getMsgLogByStatus(String status);
	
}
