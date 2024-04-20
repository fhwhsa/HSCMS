import allure
from random import randint
from PageObjects.index_page import IndexPage
from TestCases.utils import init_driver


@allure.feature('测试用例二')
class TestCase02:
    def setup_class(self):
        self.driver = init_driver()

    def teardown_class(self):
        self.driver.quit()

    def test_demo(self):
        with allure.step('准备测试数据'):
            context = '系统公告测试_' + str(randint(1000, 9999))
        with allure.step('管理员登陆账号进入管理员主页'):
            email = 'admin@hscms.com'
            passwd = '123456'
            admin_name = 'fws'
            ip = IndexPage(self.driver)
            ap = ip.login_process(email, passwd, '管理员')
            assert admin_name in ap.get_main_page_msg()
        with allure.step('点击发布公告'):
            ap.click_publish_announcement()
        with allure.step('输入公告内容并提交'):
            ap.input_announcement_and_submit(context)
        with allure.step('断言是否页面底部刷新，内容为刚才输入的公告内容'):
            assert context == ap.get_anno()


