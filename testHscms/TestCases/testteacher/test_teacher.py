import allure

from selenium import webdriver
from selenium.webdriver.edge.service import Service
from random import randint

from PageObjects.index_page import IndexPage


class TestTeacher:

    def setup_class(self):
        driver_path = '../../driver/msedgedriver'
        self.driver = webdriver.Edge(service=Service(driver_path), options=webdriver.EdgeOptions())

    def teardown_class(self):
        self.driver.quit()

    def test_registration(self):
        with allure.step('进入系统主页'):
            ip = IndexPage(self.driver)

        with allure.step('点击注册账号'):
            rp = ip.click_register()

        with allure.step('选择注册类型并点击确认'):
            trp = rp.select_register_type('老师')

        with allure.step('测试数据准备'):
            ri = (randint(100000, 999999))
            r_email = str(ri) + '@qq.com'
            r_passwd = str(ri)
            r_name = 'teacher_test' + str(randint(100, 999))

        with allure.step('填入数据（包含验证码）'):
            trp.input_data(r_email, r_passwd, r_name)

        with allure.step('点击注册按钮'):
            trp.click_sign_up()

        with allure.step('断言结果是否与预期一致'):
            assert '注册申请已提交' in trp.get_msg()
