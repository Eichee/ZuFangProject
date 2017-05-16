package com.zufang.front.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zufang.dto.CityDTO;
import com.zufang.front.utils.CacheManager;
import com.zufang.service.CityService;
import com.zufang.service.HouseAppointmentService;

public class IndexServlet extends BasicServlet {

	private static final String citiesKey="Cities";
	private static final String houseAppTotalCountKey="HouseAppTotalCount";
	
	public void index(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		CacheManager cacheManager=CacheManager.getManager();
		CityDTO[] cities=(CityDTO[])cacheManager.getValue(citiesKey, CityDTO[].class);
		if (cities==null) {
			CityService cityService=new CityService();
			cities=cityService.getAll();
			cacheManager.setValue(citiesKey, cities, 60*60);
		}
		req.setAttribute("cities", cities);
		
		Long houseAppTotalCount=(Long)cacheManager.getValue(houseAppTotalCountKey, Long.class);
		if (houseAppTotalCount==null) {
			HouseAppointmentService houseAppService=new HouseAppointmentService();
			houseAppTotalCount=houseAppService.getTotalCount();
			cacheManager.setValue(houseAppTotalCountKey, houseAppTotalCount, 60);
		}
		req.setAttribute("houseAppTotalCount", houseAppTotalCount);
		req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
	}
	
}
