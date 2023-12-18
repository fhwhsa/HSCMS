import java.sql.Connection;

import org.junit.jupiter.api.Test;

import com.fws.mvc.bean.ClassInfo;
import com.fws.mvc.daoArc.ClassInfoDaoArc;
import com.fws.mvc.utils.JdbcTools;

class TEST {

	
	@Test
	void test() throws Exception {
		Connection connection = JdbcTools.getConnectionByPools();
		ClassInfoDaoArc classInfoDaoArc = new ClassInfoDaoArc();
		for (ClassInfo c : classInfoDaoArc.getCreateClassRecordsList(connection, "1320079281@qq.com"))
			System.out.println(c.getClassName());
	}

}
