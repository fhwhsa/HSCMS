import java.sql.Connection;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fws.mvc.bean.ClassCommRecords;
import com.fws.mvc.bean.ClassInfo;
import com.fws.mvc.bean.UserClassMap;
import com.fws.mvc.daoArc.ClassCommRecordsDaoArc;
import com.fws.mvc.daoArc.ClassInfoDaoArc;
import com.fws.mvc.daoArc.UserClassMapDaoArc;
import com.fws.mvc.utils.JdbcTools;

class TEST {

	
	@Test
	void test() throws Exception {
		Connection connection = JdbcTools.getConnectionByPools();
		ClassCommRecordsDaoArc classCommRecordsDaoArc = new ClassCommRecordsDaoArc();
		List<ClassCommRecords> list = classCommRecordsDaoArc.getAllRecords(connection, "5172929");
		list = classCommRecordsDaoArc.getAllRecordsByFilter(connection, "5172929", "%测%", "%2023-12-11%");
		for (ClassCommRecords l : list)
			System.out.println(l);
		
	}

}
