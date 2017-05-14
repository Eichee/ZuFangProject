package com.zufang.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zufang.dao.AttachmentDAO;
import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.AttachmentDTO;

public class AttachmentService {

	private AttachmentDAO dao=new AttachmentDAO();
	/**
	 * 查询所有设施
	 * @return
	 */
	public AttachmentDTO[] getAll(){
		return dao.getAll();
	}
	
	/**
	 * 获取房子HoseId有用的设施
	 * @param houseId
	 * @return
	 */
	public AttachmentDTO[] getAttachments(long houseId){
		return dao.getAttachments(houseId);
	}
	
}
