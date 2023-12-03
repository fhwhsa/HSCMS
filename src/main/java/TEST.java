import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.List;

import org.apache.catalina.valves.JDBCAccessLogValve;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.jupiter.api.Test;

import com.fws.mvc.bean.Administrator;
import com.fws.mvc.bean.Guardian;
import com.fws.mvc.bean.RegistrationRecord;
import com.fws.mvc.bean.Teacher;
import com.fws.mvc.bean.User;
import com.fws.mvc.daoArc.AdministratorDaoArc;
import com.fws.mvc.daoArc.CommonDaoArc;
import com.fws.mvc.daoArc.GlobalVarDaoArc;
import com.fws.mvc.daoArc.RegisterDaoArc;
import com.fws.mvc.daoArc.UserDaoArc;
import com.fws.mvc.utils.JdbcTools;

class TEST {

	
	@Test
	void test() throws Exception {
		AdministratorDaoArc administratorDaoArc = new AdministratorDaoArc();
		Connection connection = JdbcTools.getConnectionByPools();
		administratorDaoArc.approvedRegistrationRecord(connection, "ccc", "Guardian");
	}

}
