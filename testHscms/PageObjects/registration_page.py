from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions
from selenium.webdriver.support.wait import WebDriverWait
from time import sleep

from warnings import warn


# 选择注册类型
class SelectRTypePage:

    def __init__(self, driver):
        self.driver = driver
        self.url = 'http://localhost:8080/HSCMS/turnToRegisterSelectTypeJSP.do'

    def select_register_type(self, rtype):
        if rtype not in ('老师', '家长'):
            raise ValueError('Invalid rtype value')
        self.driver.find_element(By.CSS_SELECTOR, ".layui-input").click()
        self.driver.find_element(By.CSS_SELECTOR, ".layui-this" if rtype == '家长' else ".layui-this + dd").click()
        self.driver.find_element(By.CSS_SELECTOR, 'button').click()
        sleep(1)  # 等待页面跳转
        if self.driver.current_url == self.url:
            return None
        return self.GuardianRPage(self.driver) if rtype == '家长' else self.TeacherRPage(self.driver)

    class RegistrationPage:

        def __init__(self, driver):
            self.driver = driver
            self.url = 'http://localhost:8080/HSCMS/turnToRegister.do'

        def get_vcode_and_input(self):
            self.driver.find_element(By.XPATH, '//button[text()="获取验证码"]').click()
            sleep(1)  # 等待可能的错误信息更新
            if self.driver.find_element(By.CSS_SELECTOR, 'p').text == '邮箱已被使用或审核还未通过':
                raise ValueError('邮箱已被使用或审核还未通过, 请重新传入一个测试邮箱')

            text = WebDriverWait(self.driver, 5).until(
                expected_conditions.visibility_of_element_located(
                    (By.CSS_SELECTOR, '#show_vcode_for_test')
                )
            ).text
            self.driver.find_element(By.XPATH, '//input[@placeholder="验证码"]').send_keys(text)

        def input_email_and_vcode(self, email):
            self.driver.find_element(By.XPATH, '//input[@placeholder="邮箱"]').send_keys(email)
            self.get_vcode_and_input()

        def input_passwd(self, passwd):
            self.driver.find_element(By.XPATH, '//input[@placeholder="密码"]').send_keys(passwd)
            self.driver.find_element(By.XPATH, '//input[@placeholder="确认密码"]').send_keys(passwd)

        def click_sign_up(self):
            self.driver.find_element(By.CSS_SELECTOR, ".layui-form-item > .layui-btn").click()

        def get_msg(self):
            return self.driver.find_element(By.CSS_SELECTOR, 'p').text if self.driver.current_url != self.url else False

        # 子类重载
        def input_data(self, email, passwd, name, stu_name=None):
            pass

    # 家长注册页
    class GuardianRPage(RegistrationPage):

        def __init__(self, driver):
            super().__init__(driver)

        def input_name(self, name, stu_name):
            self.driver.find_element(By.XPATH, '//input[@placeholder="昵称"]').send_keys(name)
            self.driver.find_element(By.XPATH, '//input[@placeholder="学生昵称"]').send_keys(stu_name)

        def input_data(self, email, passwd, name, stu_name=None):
            if stu_name is None:
                raise ValueError('Parameter should not be None')
            super().input_email_and_vcode(email)
            super().input_passwd(passwd)
            self.input_name(name, stu_name)

    # 老师注册页
    class TeacherRPage(RegistrationPage):

        def __init__(self, driver):
            super().__init__(driver)

        def input_name(self, name):
            self.driver.find_element(By.XPATH, '//input[@placeholder="昵称"]').send_keys(name)

        def input_data(self, email, passwd, name, stu_name=None):
            if stu_name is not None:
                warn('Parameter "stu_name" is useless')
            super().input_email_and_vcode(email)
            super().input_passwd(passwd)
            self.input_name(name)
