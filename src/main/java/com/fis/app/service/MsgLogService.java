package com.fis.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fis.app.entity.MsgLog;
import com.fis.app.repository.MsgLogRepository;

@Service
public class MsgLogService {

	public static final String DRAFT 		= "01";
	public static final String COMPLETED 	= "02";
	public static final String ERR 			= "03";
	public static final String RETRY 		= "01";
	
	@Autowired
	private MsgLogRepository msgLogRepository;
	
	
	public List<MsgLog> getLogByStatus(String status){
		return msgLogRepository.getMsgLogByStatus(status);
	}
	
	public List<MsgLog> getLogByStatusAndCount(String status, Integer count){
		return msgLogRepository.getMsgLogByStatus(status, count);
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public MsgLog saveLog(MsgLog msgLog) {
		msgLogRepository.saveAndFlush(msgLog);
		return msgLog;
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateLog(Long id, String status, String err, Integer count) throws Exception {
		
		if(id==null) throw new Exception("id cant null");
		
		if(status==null) throw new Exception("status cant null");
		
		msgLogRepository.updateData(id, status, err, count);
		
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateLog(Long id, String status) throws Exception {
		
		if(id==null) throw new Exception("id cant null");
		
		if(status==null) throw new Exception("status cant null");
	
		msgLogRepository.updateData(id, status);
		
	}
	
}
