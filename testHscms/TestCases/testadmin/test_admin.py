import allure
import pytest

from selenium import webdriver
from selenium.webdriver.edge.service import Service
from PageObjects.index_page import IndexPage
from random import randint
from time import sleep
from warnings import warn


@allure.feature('管理员功能测试')
class TestAdmin:

    def setup_class(self):
        driver_path = '/home/fws/Code/eclipse-workspace/HSCMS/testHscms/driver/msedgedriver'
        self.driver = webdriver.Edge(service=Service(driver_path), options=webdriver.EdgeOptions())
        email = 'admin@hscms.com'
        passwd = '123456'
        name = 'fws'
    def teardown_class(self):
        self.driver.quit()

    @pytest.mark.skip()
    @allure.story('登陆测试用例')
    def test_login(self):
        ip = IndexPage(self.driver)

        email = 'admin@hscms.com'
        passwd = '123456'
        name = 'fws'

        ap = ip.login_process(email, passwd, '管理员')

        assert name in ap.get_main_page_msg()

        return ap

    @pytest.mark.skip()
    @allure.story('发布公告测试用例')
    def test_publish_anno(self):
        ap = self.test_login()
        context = f'发布公告测试{randint(100, 999)}'
        ap.publish_announcement(context)
        sleep(1)  # 等待页面数据刷新
        assert context == ap.get_anno()

    def test_registration_audit(self):
        ap = self.test_login()
        ap.click_registration_audit()
        if ap.get_len_of_application_of_registration() < 2:
            warn('注册申请记录不超过两条，该用例跳过执行')
            return None

        ac, re = ap.accept_first_registration_application(), ap.reject_first_registration_application()

        return {'accept_teacher': ac, 'reject_teacher': re}
