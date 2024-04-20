from random import randint

import allure
from time import localtime
from time import time
from time import sleep

from PageObjects.index_page import IndexPage
from TestCases.test_case06 import TestCase06
from TestCases.utils import init_driver


@allure.feature('测试用例八')
class TestCase08:
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
            communication_context = "测试交流信息" + str(randint(1000, 9999))

        with allure.step('老师登陆账号进入老师主页（页面1）'):
            ip1 = IndexPage(self.driver1)
            tp1 = ip1.login_process(t_email, t_passwd, '老师')

        with allure.step('家长登陆账号进入家长主页（页面2）'):
            ip2 = IndexPage(self.driver2)
            gp2 = ip2.login_process(g_email, g_passwd, '家长')

        with allure.step('点击班级管理，选择刚才创建的班级'):
            tp1.click_class_management()
            tcm1 = tp1.select_create_class_to_manage_by_name(class_name)

        with allure.step('点击站内交流'):
            tcm1.click_class_communication()

        with allure.step('输入信息内容并提交'):
            tcm1.publish_communication_info(communication_context)

        with allure.step('点击我的班级'):
            sleep(1)  # 等待数据刷新
            gp2.click_my_class()

        with allure.step('选择相应班级'):
            mco2 = gp2.select_join_class_to_operate_by_name(class_name)

        with allure.step('点击站内交流'):
            mco2.click_class_communication()

        with allure.step('通过内容搜索刚才的信息，断言筛选返回的信息列表是否包含有刚才的信息'):
            assert communication_context in mco2.filter_communication_records_by_content(communication_context)

        with allure.step('通过时间搜索刚才的信息，断言筛选返回的信息列表是否包含有刚才的信息'):
            sleep(1)
            lc = localtime(time())
            filter_time = f'{lc.tm_year}-{lc.tm_mon}-{lc.tm_mday}'
            # print(filter_time)
            assert communication_context in mco2.filter_communication_records_by_time(filter_time)
