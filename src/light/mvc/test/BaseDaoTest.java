package light.mvc.test;

import light.mvc.service.sys.RoleServiceI;
import light.mvc.service.sys.UserServiceI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-ehcache.xml", "classpath:spring-hibernate.xml", "classpath:spring.xml"})
public class BaseDaoTest {
	@Autowired
	private RoleServiceI roleService;
	@Autowired
	private UserServiceI userService;
	
	
	@Test
	public void testName() throws Exception {
		//用户删除测试
		userService.delete(2L);
		
		
		//授权测试
		/*Role role = new Role();
		role.setResourceIds("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26");
		role.setId(1L);
		
		roleService.grant(role);*/
	}
	
	
}
