import allure

from PageObjects.index_page import IndexPage
from TestCases.test_case05 import TestCase05
from TestCases.utils import init_driver


@allure.feature('测试用例六')
class TestCase06:
    def setup_class(self):
        self.driver1 = init_driver()
        self.driver2 = init_driver()

    def teardown_class(self):
        self.driver1.quit()
        self.driver2.quit()

    def out_func(self):
        self.driver1 = init_driver()
        self.driver2 = init_driver()
        class_name = self.test_demo()
        self.driver1.quit()
        self.driver2.quit()
        return class_name

    def test_demo(self):
        with allure.step('执行测试用例五，创建一个班级'):
            class_name = TestCase05().out_func()

        with allure.step('准备数据'):
            t_email = 'teacher@teacher.com'
            t_passwd = '123456'
            g_email = 'guardian@guardian.com'
            g_passwd = '123456'

        with allure.step('老师登陆进入老师主页（页面1）'):
            ip1 = IndexPage(self.driver1)
            tp1 = ip1.login_process(t_email, t_passwd, '老师')

        with allure.step('家长登陆进入家长主页（页面2）'):
            ip2 = IndexPage(self.driver2)
            gp2 = ip2.login_process(g_email, g_passwd, '家长')

        with allure.step('获取新创建班级的班级名称以及编号（页面1）'):
            tcm1 = tp1.select_create_class_to_manage_by_name(class_name)
            class_no = tcm1.get_class_no()

        with allure.step('点击加入班级（页面2）'):
            gp2.click_join_class()
        with allure.step('输入班级编号并提交申请，断言是否出现‘申请已提交’的提示（页面2）'):
            assert '申请已提交' in gp2.join_class(class_no)

        with allure.step('相应班级老师点击班级审核（页面1）'):
            tcm1.click_class_audit()
        with allure.step('同意刚才的班级加入申请（页面1）'):
            tcm1.accept_join_class_application_by_email(g_email)

        with allure.step('家长点击我的班级（页面2）'):
            gp2.click_my_class()
        with allure.step('断言是否我的班级列表出现有刚才加入的班级（页面2）'):
            assert gp2.find_join_class_by_name(class_name)

        return class_name

