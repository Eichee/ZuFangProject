package com.zufang.front.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;


import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.zufang.dto.HouseSearchOptions;
import com.zufang.dto.HouseSearchOptions.OrderByType;
import com.zufang.dto.HouseDTO;
import com.zufang.dto.HouseSearchResult;
import com.zufang.dto.RegionDTO;
import com.zufang.front.utils.FrontUtils;
import com.zufang.service.CityService;
import com.zufang.service.HouseService;
import com.zufang.service.IdNameService;
import com.zufang.service.RegionService;
import com.zufang.tools.Functions;

@WebServlet("/House")
public class HouseServlet extends BasicServlet{

	public void search(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		long cityId=FrontUtils.getCurrentCityId(req);
		
		StringBuilder query=new StringBuilder();
		query.append("cityId:").append(cityId).append(" ");
		
		String strRegionId=req.getParameter("regionId");
		String strMonthRent=req.getParameter("monthRent");
		String strOrderBy=req.getParameter("orderBy");
		String strTypeId=req.getParameter("typeId");
		String strKeywords=req.getParameter("keywords");
		
		Integer startMonthRent=null;
		Integer endMonthRent=null;
		
		if (!StringUtils.isEmpty(strMonthRent)) {
			query.append(" AND monthRent:[").append(strMonthRent.replace("-", " TO ")).append("]");
			String[] monthRents=strMonthRent.split("-");
			if (!monthRents[0].equals("*")) {
				startMonthRent=Integer.parseInt(monthRents[0]);
			}
			if (!monthRents[1].equals("*")) {
				endMonthRent=Integer.parseInt(monthRents[1]);
			}
		}
		Long regionId=null;
		if (!StringUtils.isEmpty(strRegionId)) {

			regionId=Long.parseLong(strRegionId);
			query.append(" AND regionId:").append(regionId);
		}
		
		Long typeId=null;
		if (!StringUtils.isEmpty(strTypeId)) {
			typeId=Long.parseLong(strTypeId);
			query.append(" AND typeId:").append(typeId);
		}
		
		StringBuilder sbSearchDisplay=new StringBuilder();
		sbSearchDisplay.append(new CityService().getById(cityId).getName()).append(",");//城市
		
		if (regionId!=null) {
			sbSearchDisplay.append(new RegionService().getById(regionId).getName()).append(",");//区
		}
		if (startMonthRent!=null) {
			sbSearchDisplay.append("房租高于").append(startMonthRent).append(",");
		}
		if (endMonthRent!=null) {
			sbSearchDisplay.append("房租低于").append(endMonthRent).append(",");
		}
		if (typeId!=null) {
			sbSearchDisplay.append(new IdNameService().getById(typeId).getName()).append(",");
		}
		if (!StringUtils.isEmpty(strKeywords)) {
			sbSearchDisplay.append(strKeywords).append(",");
			query.append(" AND (communityLocation:").append(strKeywords).append(" OR communityName:").append(strKeywords)
			.append(" OR communityTraffic:").append(strKeywords).append(" OR ").append(" decorateStatusName:").append(strKeywords)
			.append(" OR description:").append(strKeywords).append(" OR regionName:").append(strKeywords).append(")");
		}
		req.setAttribute("searchDispaly", sbSearchDisplay.toString());
		
		Long pageIndex=1L;
		String strPageIndex=req.getParameter("pageIndex");
		if (!StringUtils.isEmpty(strPageIndex)) {
			pageIndex=Long.parseLong(strPageIndex);
		}
		Long pageSize=10L;
		String strPageSize=req.getParameter("pageSize");
		if (!StringUtils.isEmpty(strPageSize)) {
			pageSize=Long.parseLong(strPageSize);
		}
		
		HttpSolrClient.Builder builder = new HttpSolrClient.Builder("http://127.0.0.1:8983/solr/houses");
		HttpSolrClient solrClient = builder.build();
		
		SolrQuery solrQuery=new SolrQuery(query.toString());
		if (!StringUtils.isEmpty(strOrderBy)) {
			solrQuery.setSort(strOrderBy, ORDER.asc);
		}
		solrQuery.setStart((int)((pageIndex-1)*pageSize));
		solrQuery.setRows(new Long(pageSize).intValue());
		QueryResponse queryResponse;
		try{
			queryResponse=solrClient.query(solrQuery);
		}
		catch(SolrServerException e){
			throw new RuntimeException(e);
		}
		SolrDocumentList docList=queryResponse.getResults();
		List<HouseDTO> houses=new ArrayList<>();
		
		HouseService houseService=new HouseService();
		for (SolrDocument	doc : docList) {
			long houseId=Long.parseLong((String)doc.get("id"));
			HouseDTO house=houseService.getById(houseId);
			houses.add(house);
		}
		
		req.setAttribute("houses", houses.toArray(new HouseDTO[houses.size()]));
		
		RegionService regionService=new RegionService();
		RegionDTO[] regions=regionService.getAll(cityId);
		req.setAttribute("regions", regions);
		
		req.setAttribute("queryString", req.getQueryString());
		req.setAttribute("totalCount", docList.getNumFound());
		
		
		String pagerUrlFormat=Functions.addQueryStringPart(req.getQueryString(),"pageIndex","{pageNum}");
		req.setAttribute("pagerUrlFormat", pagerUrlFormat);
		
		req.getRequestDispatcher("/WEB-INF/House/search.jsp").forward(req, resp);
		
		
	}
	
	
	public void search1(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		long cityId=FrontUtils.getCurrentCityId(req);
		
		String strRegionId=req.getParameter("regionId");
		String strMonthRent=req.getParameter("monthRent");
		String strOrderBy=req.getParameter("orderBy");
		String strTypeId=req.getParameter("typeId");
		String strKeywords=req.getParameter("keywords");
		
		Integer startMonthRent=null;
		Integer endMonthRent=null;
		
		if (!StringUtils.isEmpty(strMonthRent)) {
			String[] monthRents=strMonthRent.split("-");
			if (!monthRents[0].equals("*")) {
				startMonthRent=Integer.parseInt(monthRents[0]);
			}
			if (!monthRents[1].equals("*")) {
				endMonthRent=Integer.parseInt(monthRents[1]);
			}
		}
		Long regionId=null;
		if (!StringUtils.isEmpty(strRegionId)) {
			regionId=Long.parseLong(strRegionId);
		}
		
		Long typeId=null;
		if (!StringUtils.isEmpty(strTypeId)) {
			typeId=Long.parseLong(strTypeId);
		}
		
		StringBuilder sbSearchDisplay=new StringBuilder();
		sbSearchDisplay.append(new CityService().getById(cityId).getName()).append(",");//城市
		
		if (regionId!=null) {
			sbSearchDisplay.append(new RegionService().getById(regionId).getName()).append(",");//区
		}
		if (startMonthRent!=null) {
			sbSearchDisplay.append("房租高于").append(startMonthRent).append(",");
		}
		if (endMonthRent!=null) {
			sbSearchDisplay.append("房租低于").append(endMonthRent).append(",");
		}
		if (typeId!=null) {
			sbSearchDisplay.append(new IdNameService().getById(typeId).getName()).append(",");
		}
		if (!StringUtils.isEmpty(strKeywords)) {
			sbSearchDisplay.append(strKeywords).append(",");
		}
		req.setAttribute("searchDispaly", sbSearchDisplay.toString());
		
		Long pageIndex=1L;
		String strPageIndex=req.getParameter("pageIndex");
		if (!StringUtils.isEmpty(strPageIndex)) {
			pageIndex=Long.parseLong(strPageIndex);
		}
		Long pageSize=10L;
		String strPageSize=req.getParameter("pageSize");
		if (!StringUtils.isEmpty(strPageSize)) {
			pageSize=Long.parseLong(strPageSize);
		}
		
		
		HouseSearchOptions searchOptions=new HouseSearchOptions();
		searchOptions.setCityId(cityId);
		searchOptions.setEndMonthRent(endMonthRent);
		searchOptions.setKeywords(strKeywords);
		searchOptions.setOrderByType("monthRent".equals(strOrderBy)?OrderByType.MonthRent:OrderByType.Area);
		searchOptions.setPageIndex(pageIndex);
		searchOptions.setPageSize(pageSize);
		searchOptions.setRegionId(regionId);
		searchOptions.setStartMonthRent(startMonthRent);
		searchOptions.setTypeId(typeId);
		
		HouseService houseService=new HouseService();
		HouseSearchResult result=houseService.search(searchOptions);
		req.setAttribute("houses", result.getHouses());
		
		RegionService regionService=new RegionService();
		RegionDTO[] regions=regionService.getAll(cityId);
		req.setAttribute("regions", regions);
		
		req.setAttribute("queryString", req.getQueryString());
		req.setAttribute("totalCount", result.getTotalCount());
		
		
		String pagerUrlFormat=Functions.addQueryStringPart(req.getQueryString(),"pageIndex","{pageNum}");
		req.setAttribute("pagerUrlFormat", pagerUrlFormat);
		
		req.getRequestDispatcher("/WEB-INF/House/search.jsp").forward(req, resp);
		
		
	}
	
}
