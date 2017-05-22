package com.zufang.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.SliderUI;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.HouseDTO;
import com.zufang.dto.HousePicDTO;
import com.zufang.dto.HouseSearchOptions;
import com.zufang.dto.HouseSearchOptions.OrderByType;
import com.zufang.dto.HouseSearchResult;



public class HouseDAO {

	private static final String selectMainSQL;
	static {
		StringBuilder sbSQL = new StringBuilder();
		sbSQL.append(
				"select h.*, region.CityId CityId,  region.Name RegionName,community.Name  CommunityName,community.Location CommunityLocation,\n");
		sbSQL.append(
				"community.Traffic CommunityTraffic, community.BuiltYear CommunityBuiltYear, city.Name CityName,  roomType.Name RoomTypeName,\n");
		sbSQL.append("Status.Name StatusName,DecorateStatus.Name DecorateStatusName,Type.Name TypeName\n");
		sbSQL.append("from T_Houses h\n");
		sbSQL.append("left join T_Regions region on h.RegionId=region.Id\n");
		sbSQL.append("left join T_Communities community on h.CommunityId=community.Id\n");
		sbSQL.append("left join T_Cities city on region.CityId=city.Id\n");
		sbSQL.append("left join T_IdNames roomType   on h. RoomTypeId=roomType .Id\n");
		sbSQL.append("left join T_IdNames Status  on h.StatusId= Status.Id\n");
		sbSQL.append("left join T_IdNames DecorateStatus  on h.DecorateStatusId= DecorateStatus.Id\n");
		sbSQL.append("left join T_IdNames Type  on h.TypeId= Type.Id\n");

		selectMainSQL = sbSQL.toString();
	}

	public HouseDTO toDTO(ResultSet rs) throws SQLException {
		long id = rs.getLong("Id");
		HouseDTO house = new HouseDTO();
		house.setAddress(rs.getString("Address"));
		house.setArea(rs.getDouble("Area"));
		house.setCheckInDateTime(rs.getDate("CheckInDateTime"));
		house.setCommunityId(rs.getLong("CommunityId"));
		house.setCommunityName(rs.getString("CommunityName"));
		house.setCommunityLocation(rs.getString("CommunityLocation"));
		house.setCommunityTraffic(rs.getString("CommunityTraffic"));
		house.setCommunityBuiltYear((Integer) rs.getObject("CommunityBuiltYear"));
		house.setDecorateStatusId(rs.getLong("DecorateStatusId"));
		house.setDecorateStatusName(rs.getString("DecorateStatusName"));
		house.setDirection(rs.getString("Direction"));
		house.setFloorIndex(rs.getInt("FloorIndex"));
		house.setId(id);
		house.setLookableDateTime(rs.getDate("LookableDateTime"));
		house.setMonthRent(rs.getInt("MonthRent"));
		house.setCityId(rs.getLong("CityId"));
		house.setCityName(rs.getString("CityName"));
		house.setRegionId(rs.getLong("RegionId"));
		house.setRegionName(rs.getString("RegionName"));
		house.setRoomTypeId(rs.getLong("RoomTypeId"));
		house.setRoomTypeName(rs.getString("RoomTypeName"));
		house.setStatusId(rs.getLong("StatusId"));
		house.setStatusName(rs.getString("StatusName"));
		house.setTotalFloorCount(rs.getInt("TotalFloorCount"));
		house.setTypeId(rs.getLong("TypeId"));
		house.setTypeName(rs.getString("TypeName"));
		house.setOwnerName(rs.getString("OwnerName"));
		house.setOwnerPhoneNum(rs.getString("OwnerPhoneNum"));
		house.setDescription(rs.getString("Description"));
		house.setCreateDateTime(rs.getDate("CreateDateTime"));
		house.setDeleted(rs.getBoolean("IsDeleted"));

		List<Long> listAttachmentId = new ArrayList<>();// 泛型中不能用原始数据类型
		ResultSet rsAtt = null;
		try {
			rsAtt = JdbcUtils.executeQuery("select AttachmentId from t_houseattachments where HouseId=?", id);
			while (rsAtt.next()) {
				listAttachmentId.add(rsAtt.getLong("AttachmentId"));
			}
		} finally {
			JdbcUtils.closeAll(rsAtt);
		}
		Long[] attacheIds = listAttachmentId.toArray(new Long[listAttachmentId.size()]);

		// house.setAttachmentIds(toPrimitive(attacheIds));
		house.setAttachmentIds(ArrayUtils.toPrimitive(attacheIds));
		HousePicDTO[] pics = getPics(id);// 获得房子的图片
		if (pics.length > 0)// 获得第一张图片的缩略图
		{
			house.setFirstThumbUrl(pics[0].getThumbUrl());
		}

		// todo:house.setAttachmentIds的初始化
		// todo:house.setFirstThumbUrl(
		return house;
	}
	/*
	 * 
	 * private long[] toPrimitive(Long[] arrays) { long[] longs =new
	 * long[arrays.length]; for(int i=0;i<arrays.length;i++) { longs[i] =
	 * arrays[i]; } return longs; }
	 */

	public HouseDTO getById(long id) {
		StringBuilder sbSQL = new StringBuilder();
		sbSQL.append(selectMainSQL);
		sbSQL.append("where h.Id=? and h.IsDeleted=0");

		ResultSet rs = null;
		try {
			rs = JdbcUtils.executeQuery(sbSQL.toString(), id);
			if (rs.next()) {
				return toDTO(rs);
			} else {
				return null;
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.closeAll(rs);
		}
	}

	public long getTotalCount(long cityId, long typeId) {
		StringBuilder sbSQL = new StringBuilder();
		sbSQL.append("select count(*) from T_Houses h\n");
		sbSQL.append("left join T_Regions region on h.RegionId=region.Id\n");
		sbSQL.append("where region.CityId=? and h.TypeId=? and h.IsDeleted=0");
		Number num;
		try {
			num = (Number) JdbcUtils.querySingle(sbSQL.toString(), cityId, typeId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return num.longValue();
	}

	public HouseDTO[] getAll() {
		StringBuilder sbSQL = new StringBuilder();
		sbSQL.append(selectMainSQL);
		sbSQL.append("where h.IsDeleted=0\n");

		List<HouseDTO> houses = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = JdbcUtils.executeQuery(sbSQL.toString());
			while (rs.next()) {
				houses.add(toDTO(rs));
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.closeAll(rs);
		}
		return houses.toArray(new HouseDTO[houses.size()]);
	}

	public HouseDTO[] getPagedData(long cityId, long typeId, int pageSize, long currentIndex) {
		if (currentIndex <= 0) {
			throw new IllegalArgumentException("currentIndex必须>0");
		}
		if (pageSize <= 0) {
			throw new IllegalArgumentException("pageSize必须>0");
		}

		StringBuilder sbSQL = new StringBuilder();
		sbSQL.append(selectMainSQL);
		sbSQL.append("where region.CityId=? and h.TypeId=? and h.IsDeleted=0\n");
		sbSQL.append("limit ?,?\n");

		List<HouseDTO> houses = new ArrayList<>(pageSize);
		ResultSet rs = null;
		try {
			rs = JdbcUtils.executeQuery(sbSQL.toString(), cityId, typeId, (currentIndex - 1) * pageSize, pageSize);
			while (rs.next()) {
				houses.add(toDTO(rs));
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.closeAll(rs);
		}
		return houses.toArray(new HouseDTO[houses.size()]);
	}

	public long addnew(HouseDTO house) {

		StringBuilder sb = new StringBuilder();
		sb.append(
				"insert into T_Houses(RegionId,CommunityId,RoomTypeId,Address,MonthRent,StatusId,Area,DecorateStatusId,TotalFloorCount,\n");
		sb.append(
				"FloorIndex,TypeId,Direction,LookableDateTime,CheckInDateTime,OwnerName,OwnerPhoneNum,CreateDateTime,Description,IsDeleted)\n");
		sb.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,0)");

		Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			long houseId = JdbcUtils.executeInsert(conn, sb.toString(), house.getRegionId(), house.getCommunityId(),
					house.getRoomTypeId(), house.getAddress(), house.getMonthRent(), house.getStatusId(),
					house.getArea(), house.getDecorateStatusId(), house.getTotalFloorCount(), house.getFloorIndex(),
					house.getTypeId(), house.getDirection(), house.getLookableDateTime(), house.getCheckInDateTime(),
					house.getOwnerName(), house.getOwnerPhoneNum(), house.getDescription());

			for (long attId : house.getAttachmentIds()) {
				JdbcUtils.executeNonQuery(conn, "insert into t_houseattachments(HouseId,AttachmentId) values(?,?)",
						houseId, attId);
			}
			conn.commit();
			return houseId;
		} catch (SQLException e) {
			JdbcUtils.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtils.closeQuietly(conn);
		}
	}

	public void update(HouseDTO house) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"update T_Houses set RegionId=?,CommunityId=?,RoomTypeId=?,Address=?,MonthRent=?,StatusId=?,Area=?,DecorateStatusId=?,TotalFloorCount=?,\n");
		sb.append(
				"FloorIndex=?,TypeId=?,Direction=?,LookableDateTime=?,CheckInDateTime=?,OwnerName=?,OwnerPhoneNum=?,Description=?\n");
		sb.append("where Id=?");
		Connection conn = null;
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);

			JdbcUtils.executeNonQuery(conn, sb.toString(), house.getRegionId(), house.getCommunityId(),
					house.getRoomTypeId(), house.getAddress(), house.getMonthRent(), house.getStatusId(),
					house.getArea(), house.getDecorateStatusId(), house.getTotalFloorCount(), house.getFloorIndex(),
					house.getTypeId(), house.getDirection(), house.getLookableDateTime(), house.getCheckInDateTime(),
					house.getOwnerName(), house.getOwnerPhoneNum(), house.getDescription(), house.getId());

			// 先删，后加
			JdbcUtils.executeNonQuery(conn, "delete from t_houseattachments where HouseId=?", house.getId());
			for (long attId : house.getAttachmentIds()) {
				JdbcUtils.executeNonQuery(conn, "insert into t_houseattachments(HouseId,AttachmentId) values(?,?)",
						house.getId(), attId);
			}

			conn.commit();
		} catch (SQLException ex) {
			JdbcUtils.rollback(conn);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.closeQuietly(conn);
		}

	}

	public void markDeleted(long id) {
		try {
			JdbcUtils.executeNonQuery("update T_Houses set IsDeleted=1 where Id=?", id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public HousePicDTO[] getPics(long houseId) {
		ResultSet rs = null;
		List<HousePicDTO> list = new ArrayList<>();
		try {
			rs = JdbcUtils.executeQuery("select * from T_HousePics where HouseId=? and IsDeleted=0", houseId);
			while (rs.next()) {
				HousePicDTO housePic = new HousePicDTO();
				housePic.setCreateDateTime(rs.getDate("CreateDateTime"));
				housePic.setDeleted(rs.getBoolean("IsDeleted"));
				housePic.setHeight(rs.getInt("Height"));
				housePic.setHouseId(rs.getLong("HouseId"));
				housePic.setId(rs.getLong("Id"));
				housePic.setUrl(rs.getString("Url"));
				housePic.setWidth(rs.getInt("Width"));
				housePic.setThumbUrl(rs.getString("ThumbUrl"));

				list.add(housePic);
			}
			return list.toArray(new HousePicDTO[list.size()]);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.closeAll(rs);
		}
	}

	public long addnewHousePic(HousePicDTO housePic) {
		try {
			return JdbcUtils.executeInsert(
					"Insert into T_HousePics(HouseId,Url,ThumbUrl,Width,Height,CreateDateTime,IsDeleted) values(?,?,?,?,?,now(),0)",
					housePic.getHouseId(), housePic.getUrl(), housePic.getThumbUrl(), housePic.getWidth(),
					housePic.getHeight());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteHousePic(long housePicId) {
		try {
			JdbcUtils.executeNonQuery("Update T_HousePics set IsDeleted=1 where Id=?", housePicId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public HouseSearchResult search(HouseSearchOptions options){
		HouseSearchResult result=new HouseSearchResult();
		StringBuilder sbSelect=new StringBuilder();
		sbSelect.append(selectMainSQL);
		sbSelect.append("where h.IsDeleted=0 and city.Id=?\n");
		
		ArrayList<Object> listParams=new ArrayList<>();
		listParams.add(options.getCityId());
		
		if (options.getEndMonthRent()!=null) {
			sbSelect.append("and h.MonthRent<=?\n");
			listParams.add(options.getEndMonthRent());
		}
		
		if (options.getStartMonthRent()!=null) {
			sbSelect.append(" and h.MonthRent>=?\n");
			listParams.add(options.getStartMonthRent());
		}
		
		if (!StringUtils.isEmpty(options.getKeywords())) {
			sbSelect.append(" and community.Name like ?\n");
			listParams.add("'%"+options.getKeywords()+"%'");
		}
		
		if (options.getRegionId()!=null) {
			sbSelect.append("and h.RegionId=?\n");
			listParams.add(options.getRegionId());
		}
		
		if (options.getTypeId()!=null) {
			sbSelect.append(" and h.TypeId=?\n");
			listParams.add(options.getTypeId());
		}
		
		if (options.getOrderByType()==OrderByType.Area) {
			sbSelect.append("order by h.area asc\n");
		}else{
			sbSelect.append("order by h.monthrent asc\n");
		}
		
		Number totalCount;
		try{
			totalCount=(Number)JdbcUtils.querySingle(sbSelect.toString(), listParams.toArray());
		}
		catch(SQLException e){
			throw new RuntimeException(e);
		}
		
		if (totalCount!=null) {
			result.setTotalCount(totalCount.longValue());
		}
		
		
		sbSelect.append("limit ?,?\n");
		listParams.add((options.getPageIndex()-1)*options.getPageSize());
		listParams.add(options.getPageSize());
		
		ArrayList<HouseDTO> houses=new ArrayList<>();
		ResultSet rs=null;
		try{
			rs=JdbcUtils.executeQuery(sbSelect.toString(), listParams.toArray());
			while(rs.next()){
				houses.add(toDTO(rs));
			}
			result.setHouses(houses.toArray(new HouseDTO[houses.size()]));
		}
		catch(SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			JdbcUtils.closeAll(rs);
		}
		return result;
	}

}
