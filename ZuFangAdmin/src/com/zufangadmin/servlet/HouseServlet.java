package com.zufangadmin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zufang.dao.HouseDAO;
import com.zufang.dto.HouseDTO;
import com.zufang.service.HouseService;
import com.zufangadmin.utils.AdminUtils;

@WebServlet("/house")
public class HouseServlet extends BasicServlet {

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

}
