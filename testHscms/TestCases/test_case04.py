import allure
from random import randint
from PageObjects.index_page import IndexPage
from TestCases.utils import init_driver


@allure.feature('测试用例四')
class TestCase04:
    def setup_class(self):
        self.driver1 = init_driver()
        self.driver2 = init_driver()

    def teardown_class(self):
        self.driver1.quit()
        self.driver2.quit()

    @allure.description('打开两个页面，页面1负责老师用户的注册以及后续登陆验证操作，页面2负责登陆管理员账号对老师用户的注册申请进行审核')
    def test_demo(self):
        with allure.step('准备测试数据（页面1）'):
            a_email = 'admin@hscms.com'
            a_passwd = '123456'
            t_passwd = str(randint(1000, 9999))
            t_email = t_passwd + '@qq.com'
            t_name = 'teacher_test' + t_passwd
        with allure.step('进入系统主页（页面1）'):
            ip1 = IndexPage(self.driver1)
        with allure.step('点击注册账号（页面1）'):
            srp1 = ip1.click_register()
        with allure.step('选择注册类型为老师（页面1）'):
            trp1 = srp1.select_register_type('老师')
        with allure.step('填入注册信息（页面1）'):
            trp1.input_data(t_email, t_passwd, t_name)
        with allure.step('点击注册（页面1）'):
            trp1.click_sign_up()
        with allure.step('断言是否页面跳转，有醒目的‘注册申请已提交，清等待管理员审核，结果通过邮箱通知！’字样显示（页面1）'):
            assert '注册申请已提交' in trp1.get_msg()

        with allure.step('管理员登陆（页面2）'):
            ip2 = IndexPage(self.driver2)
            ap2 = ip2.login_process(a_email, a_passwd, '管理员')
        with allure.step('点击注册审核（页面2）'):
            ap2.click_registration_audit()
        with allure.step('同意刚才的注册申请（页面2）'):
            ap2.accept_registration_application_by_email(t_email)

        with allure.step('回到系统主页（页面1）'):
            ip1.open_url()
        with allure.step('输入刚才注册的老师用户的邮箱和密码（页面1）'):
            ip1.input_mail(t_email)
            ip1.input_passwd(t_passwd)
        with allure.step('选择登陆用户类型为老师（页面1）'):
            ip1.select_login_type('老师')
        with allure.step('点击登陆'):
            tp1 = ip1.click_login('老师')
        with allure.step('断言是否进入老师主页，有明显的"hello {老师名称}"字样（页面1）'):
            assert t_name in tp1.get_main_page_msg()
