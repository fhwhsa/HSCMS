import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.List;

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
import com.fws.mvc.utils.JdbcTools;

class TEST {

	void func(User user, String userType) {
		if (userType.equals("Teacher")) {
			Teacher obj = (Teacher)user;
			System.out.println(obj.getName() + " " + obj.getEmailAddr());
		}
		else {
			Guardian obj = (Guardian)user;
			System.out.println(obj.getName() + " " + obj.getEmailAddr());
		}
	}
	
	@Test
	void test() {
		Teacher teacher = new Teacher("fws", "123", "aaa", "");
		Guardian guardian = new Guardian("fws1", "123", "bbb", "", "");
		func(teacher, "Teacher");
		func(guardian, "Guardian");
	}
}
