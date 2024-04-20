import allure
from random import randint
from PageObjects.index_page import IndexPage
from TestCases.utils import init_driver


@allure.feature('测试用例三')
class TestCase03:
    def setup_class(self):
        self.driver = init_driver()

    def teardown_class(self):
        self.driver.quit()

    def test_demo(self):
        with allure.step('准备测试数据'):
            passwd = str(randint(1000, 9999))
            email = passwd + '@qq.com'
            name = 'guardian_test' + passwd
            stu_name = 'student_test' + passwd
        with allure.step('进入系统主页'):
            ip = IndexPage(self.driver)
        with allure.step('点击注册账号'):
            stp = ip.click_register()
        with allure.step('选择注册用户类型为家长并点击确认'):
            grp = stp.select_register_type('家长')
        with allure.step('填入注册信息'):
            grp.input_data(email, passwd, name, stu_name)
        with allure.step('点击注册'):
            grp.click_sign_up()
        with allure.step('断言是否页面跳转并有"注册成功"字样'):
            assert '注册成功' in grp.get_msg()
        with allure.step('回到系统主页'):
            ip.open_url()
        with allure.step('输入邮箱和密码'):
            ip.input_mail(email)
            ip.input_passwd(passwd)
        with allure.step('选择登陆用户类型为家长'):
            ip.select_login_type('家长')
        with allure.step('点击登陆'):
            gp = ip.click_login('家长')
        with allure.step('断言是否进入家长主页，有明显的"hello {家长名称}"字样'):
            assert name in gp.get_main_page_msg()