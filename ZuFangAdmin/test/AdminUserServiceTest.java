import static org.junit.Assert.*;

import org.junit.Test;

import com.zufang.service.AdminUserService;


public class AdminUserServiceTest {

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
	
	@Test
	public void adminUserServiceTest(){
		AdminUserService adminuserservice=new AdminUserService();
		boolean bool= adminuserservice.checkLogin("123", "123");
		System.out.println(bool);
	}

}
