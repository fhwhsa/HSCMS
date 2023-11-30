import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.Test;

import com.fws.mvc.bean.Administrator;
import com.fws.mvc.bean.Teacher;
import com.fws.mvc.bean.User;
import com.fws.mvc.daoArc.CommonDaoArc;
import com.fws.mvc.daoArc.GlobalVarDaoArc;
import com.fws.mvc.utils.JdbcTools;

class TEST {

	@Test
	void test() {
		GlobalVarDaoArc daoArc = new GlobalVarDaoArc();
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			daoArc.updateSysAnnoContext(connection, "hello");
			System.out.println(daoArc.getSysAnnoContext(connection));
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}

}
