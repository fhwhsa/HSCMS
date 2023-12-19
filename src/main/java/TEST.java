import java.sql.Connection;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fws.mvc.bean.ClassInfo;
import com.fws.mvc.bean.UserClassMap;
import com.fws.mvc.daoArc.ClassInfoDaoArc;
import com.fws.mvc.daoArc.UserClassMAPDaoArc;
import com.fws.mvc.utils.JdbcTools;

class TEST {

	
	@Test
	void test() throws Exception {
		Connection connection = JdbcTools.getConnectionByPools();
		UserClassMAPDaoArc userClassMAPDaoArc = new UserClassMAPDaoArc();
		List<UserClassMap> list = userClassMAPDaoArc.getClassMembers(connection, "4247053");
		UserClassMap creater = userClassMAPDaoArc.getClassCreter(connection, "4247053");
		System.out.println(creater);
		for (UserClassMap c : list)
			System.out.println(c);
	}

}
