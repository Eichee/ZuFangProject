import static org.junit.Assert.*;

import org.junit.Test;

import com.zufang.dao.AttachmentDAO;
import com.zufang.dto.AttachmentDTO;


public class AttachmentDAOTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void getAllTest(){
		AttachmentDAO attachmentDAO=new AttachmentDAO();
		AttachmentDTO[] attachments=attachmentDAO.getAll();
		System.out.println(attachments.length);
		
	}
	
	@Test
	public void getAttachmentsTest(){
		AttachmentDAO attachmentDAO=new AttachmentDAO();
		AttachmentDTO[] attachments=attachmentDAO.getAttachments(3L);
		System.out.println(attachments.length);
	}

}
