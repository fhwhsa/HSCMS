import allure
from random import randint
from PageObjects.index_page import IndexPage
from TestCases.test_case06 import TestCase06
from TestCases.utils import init_driver


@allure.feature('测试用例七')
class TestCase07:
    def setup_class(self):
        self.driver1 = init_driver()
        self.driver2 = init_driver()

    def teardown_class(self):
        self.driver1.quit()
        self.driver2.quit()

    def test_demo(self):
        with allure.step('执行测试用例六'):
            class_name = TestCase06().out_func()

        with allure.step('准备数据'):
            t_email = 'teacher@teacher.com'
            t_passwd = '123456'
            g_email = 'guardian@guardian.com'
            g_passwd = '123456'
            anno_context = "测试公告" + str(randint(1000, 9999))

        with allure.step('老师登陆账号进入老师主页（页面1）'):
            ip1 = IndexPage(self.driver1)
            tp1 = ip1.login_process(t_email, t_passwd, '老师')

        with allure.step('家长登陆账号进入家长主页（页面2）'):
            ip2 = IndexPage(self.driver2)
            gp2 = ip2.login_process(g_email, g_passwd, '家长')

        with allure.step('点击班级管理，选择刚才创建的班级（页面1）'):
            tp1.click_class_management()
            tcm1 = tp1.select_create_class_to_manage_by_name(class_name)

        with allure.step('点击发布通知（页面1）'):
            tcm1.click_publish_anno()

        with allure.step('输入通知内容并提交（页面1）'):
            tcm1.publish_class_anno(anno_context)

        with allure.step('点击我的班级（页面2）'):
            gp2.click_my_class()

        with allure.step('选择相应班级（页面2）'):
            mco2 = gp2.select_join_class_to_operate_by_name(class_name)

        with allure.step('点击班级通知（页面2）'):
            mco2.click_class_anno()

        with allure.step('断言是否最近一条的班级通知内容与刚发布的相同（页面2）'):
            assert anno_context == mco2.get_last_class_anno()



