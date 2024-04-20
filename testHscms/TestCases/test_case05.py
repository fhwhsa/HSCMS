import allure
from random import randint
from time import sleep
from PageObjects.index_page import IndexPage
from TestCases.utils import init_driver


@allure.feature('测试用例五')
class TestCase05:
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

    @allure.description('打开两个页面，页面1负责老师用户创建班级以及验证班级创建结果，页面2负责管理员进行班级审核')
    def test_demo(self):
        with allure.step('准备测试数据'):
            t_email = 'teacher@teacher.com'
            t_passwd = '123456'
            class_name = 'test_class' + str(randint(1000, 9999))
            a_email = 'admin@hscms.com'
            a_passwd = '123456'

        with allure.step('登陆默认的老师账号（页面1）'):
            ip1 = IndexPage(self.driver1)
            tp1 = ip1.login_process(t_email, t_passwd, '老师')
        with allure.step('管理员登陆（页面2）'):
            ip2 = IndexPage(self.driver2)
            ap2 = ip2.login_process(a_email, a_passwd, '管理员')

        with allure.step('点击创建班级（页面1）'):
            tp1.click_create_class()
        with allure.step('输入班级名称并提交（页面1）'):
            option_info = tp1.create_class(class_name)
        with allure.step('断言是否页面跳转，有‘请等待审核。 点此继续申请’字样显示（页面1）'):
            assert '请等待审核' in option_info

        with allure.step('点击班级审核（页面2）'):
            ap2.click_class_audit()
        with allure.step('同意刚才的班级创建申请（页面2）'):
            ap2.accept_class_application_by_name(class_name)

        with allure.step('回到老师用户主页，点击班级管理（页面1）'):
            sleep(2)  # 等待数据刷新
            tp1.click_class_management()
        with allure.step('断言是否班级管理列表出现有刚才创建的班级（页面1）'):
            assert tp1.find_create_class_by_name(class_name)

        return class_name
