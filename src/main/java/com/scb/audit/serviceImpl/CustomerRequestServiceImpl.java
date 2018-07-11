package com.scb.audit.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scb.audit.dao.CustomerDataReposatory;
import com.scb.audit.dao.CustomerErrorRepo;
import com.scb.audit.dao.CustomerAuditRepo;
import com.scb.audit.model.AuditLog;
import com.scb.audit.model.CustomerRequestData;
import com.scb.audit.model.CustomerResponse;
import com.scb.audit.model.MsErrorLog;
import com.scb.audit.service.CustomerRequestService;
import com.scb.audit.utils.SCBCommonMethods;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CustomerRequestServiceImpl implements CustomerRequestService {
	@Autowired
	private CustomerDataReposatory customerDataReposatory;
	@Autowired
	private SCBCommonMethods commonMethods;
	@Autowired
	private CustomerAuditRepo customerAuditRepo;
	@Autowired
	private CustomerErrorRepo customerErrorRepo;

	@Override
	@Transactional
	public CustomerResponse customerRequestHandleService(CustomerRequestData customerRequestData) {
		
		List<CustomerRequestData> customerList = customerDataReposatory
				.findByCorelationId(customerRequestData.getCorelationId());
		if (customerList.isEmpty()) {
			log.info("Unique request");
			CustomerRequestData customerRequestDataResponse = customerDataReposatory.save(customerRequestData);
			return commonMethods.getSuccessResponse(customerRequestDataResponse);
		} else {
			log.info("Duplicate request ");
			return commonMethods.getErrorResponse("Duplicate User");
		}

	}
	@Override
	@Transactional
	public AuditLog customerAuditRequestHandleService(AuditLog auditLog) {
		AuditLog auditLogResponse = customerAuditRepo.save(auditLog);
		return auditLogResponse;
	}
	@Override
	@Transactional
	public MsErrorLog customerErrorRequestHandleService(MsErrorLog msErrorLog) {
		MsErrorLog errorLogResponse = customerErrorRepo.save(msErrorLog);
		return errorLogResponse;
	}

}