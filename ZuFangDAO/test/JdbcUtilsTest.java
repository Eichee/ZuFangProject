import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.zufang.dao.utils.JdbcUtils;


public class JdbcUtilsTest {

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
	
	@Test
	public void executeInsertTest() throws SQLException{
		long id=JdbcUtils.executeInsert("insert into T_Roles (Name,IsDeleted) values(?,0)", "tt1");
		System.out.println(id);
	}

}
