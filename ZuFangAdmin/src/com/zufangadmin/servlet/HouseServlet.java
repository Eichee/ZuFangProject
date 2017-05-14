package com.zufangadmin.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.zufang.dao.HouseDAO;
import com.zufang.dto.AttachmentDTO;
import com.zufang.dto.HouseDTO;
import com.zufang.dto.IdNameDTO;
import com.zufang.dto.RegionDTO;
import com.zufang.service.AttachmentService;
import com.zufang.service.HouseService;
import com.zufang.service.IdNameService;
import com.zufang.service.RegionService;
import com.zufangadmin.utils.AdminUtils;

@WebServlet("/house")
public class HouseServlet extends BasicServlet {

	@HasPermission("House.Query")
	public void list(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long adminUserCityId = AdminUtils.getAdminUserCityId(req);
		if (adminUserCityId == null) {
			AdminUtils.showError(req, resp, "总部的人不能管理房源");
			return;
		}
		long typeId = Long.parseLong(req.getParameter("typeId"));
		long pageIndex = Long.parseLong(req.getParameter("pageIndex"));
		req.setAttribute("typeId", typeId);
		req.setAttribute("pageIndex", pageIndex);

		HouseService houseService = new HouseService();
		long totalCount = houseService.getTotalCount(adminUserCityId, typeId);
		req.setAttribute("totalCount", totalCount);

		HouseDTO[] houses = houseService.getPagedData(adminUserCityId, typeId,
				10, pageIndex);
		req.setAttribute("houses", houses);
		req.setAttribute("ctxPath", req.getContextPath());
		req.getRequestDispatcher("/WEB-INF/house/houseList.jsp").forward(req,
				resp);
	}
	
	@HasPermission("House.Query")
	public void add(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		long typeId=Long.parseLong(req.getParameter("typeId"));
		req.setAttribute("typeId", typeId);
		Long cityId=AdminUtils.getAdminUserCityId(req);
		if (cityId==null) {
			AdminUtils.showError(req, resp, "总部的人不能管理房源");
			return;
		}
		fillEditAddRequest(req,cityId);
		
		req.getRequestDispatcher("/WEB-INF/house/houseAdd.jsp").forward(req, resp);
	}

	private void fillEditAddRequest(HttpServletRequest req, Long cityId) {
		RegionService regionService=new RegionService();
		RegionDTO[] regions=regionService.getAll(cityId);
		req.setAttribute("regions", regions);
		
		IdNameService idNameService=new IdNameService();
		IdNameDTO[] roomTypes=idNameService.getAll("户型");
		IdNameDTO[] status=idNameService.getAll("房屋状态");
		IdNameDTO[] decorateStatus=idNameService.getAll("装修状态");
		
		AttachmentService attachmentService=new AttachmentService();
		AttachmentDTO[] attchaments=attachmentService.getAll();
		
		req.setAttribute("roomTypes", roomTypes);
		req.setAttribute("status", status);
		req.setAttribute("decoreteStatus", decorateStatus);
		req.setAttribute("attachments", attchaments);
		
		Date now=new Date();
		req.setAttribute("now", DateFormatUtils.format(now, "yyyy-MM-dd"));
		
	
	}
	

}
