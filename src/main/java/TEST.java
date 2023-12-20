import java.sql.Connection;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fws.mvc.bean.ClassInfo;
import com.fws.mvc.bean.UserClassMap;
import com.fws.mvc.daoArc.ClassInfoDaoArc;
import com.fws.mvc.daoArc.UserClassMapDaoArc;
import com.fws.mvc.utils.JdbcTools;

class TEST {

	
	@Test
	void test() throws Exception {
		Connection connection = JdbcTools.getConnectionByPools();
		ClassInfoDaoArc classInfoDaoArc = new ClassInfoDaoArc();
		System.out.println(classInfoDaoArc.isExist(connection, "1705316"));
	}

}
