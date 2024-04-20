import allure
from PageObjects.index_page import IndexPage
from TestCases.utils import init_driver

@allure.feature('测试用例一')
class TestCase01:
    def setup_class(self):
        self.driver = init_driver()

    def teardown_class(self):
        self.driver.quit()

    def test_demo(self):
        with allure.step('准备测试数据'):
            email = 'admin@hscms.com'
            passwd = '123456'
            admin_name = 'fws'
        with allure.step('进入系统主页'):
            ip = IndexPage(self.driver)
        with allure.step('输入管理员邮箱及密码'):
            ip.input_mail(email)
            ip.input_passwd(passwd)
        with allure.step('选择登陆用户类型为管理员'):
            ip.select_login_type('管理员')
        with allure.step('点击登陆'):
            ap = ip.click_login('管理员')
        with allure.step('断言是否进入管理员主页，有"hello fws"字样'):
            assert admin_name in ap.get_main_page_msg()

