from selenium.webdriver.common.by import By
from selenium.webdriver.edge.webdriver import WebDriver
from time import sleep

from selenium.webdriver.support import expected_conditions
from selenium.webdriver.support.wait import WebDriverWait


class SelectFTypePage:

    def __init__(self, driver: WebDriver):
        self.driver = driver
        self.url = 'http://localhost:8080/HSCMS/turnToSelectUserTypeJSP.do'

    def select_user_type(self, selected_type):
        if selected_type not in ('家长', '老师'):
            raise ValueError('Invalid selected_type value')
        self.driver.find_element(By.CSS_SELECTOR, '.layui-input').click()
        self.driver.find_element(By.CSS_SELECTOR,
                                 '.layui-this' if selected_type == '家长' else '.layui-this + dd').click()
        self.driver.find_element(By.CSS_SELECTOR, 'button').click()
        if self.driver.current_url != self.url:
            return self.ForgetPasswdPage(self.driver)
        else:
            return None

    class ForgetPasswdPage:

        def __init__(self, driver: WebDriver):
            self.driver = driver
            self.url = 'http://localhost:8080/HSCMS/turnToForgetPasswdJSP.do'

        def input_email(self, email: str):
            self.driver.find_element(By.XPATH, '//input[@name="emailAddr"]').send_keys(email)

        def get_vcode_and_input(self):
            self.driver.find_element(By.XPATH, '//button[text()="获取验证码"]').click()
            sleep(1)  # 等待页面刷新
            vcode = self.driver.find_element(By.CSS_SELECTOR, '#show_vcode_for_test').text

            if vcode is None:
                raise ValueError('邮箱地址错误')

            text = WebDriverWait(self.driver, 5).until(
                expected_conditions.visibility_of_element_located(
                    (By.CSS_SELECTOR, '#show_vcode_for_test')
                )
            ).text
            self.driver.find_element(By.XPATH, '//input[@placeholder="验证码"]').send_keys(text)

        def input_passwd(self, passwd: str):
            self.driver.find_element(By.XPATH, '//input[@placeholder="新密码"]').send_keys(passwd)
            self.driver.find_element(By.XPATH, '//input[@placeholder="确认新密码"]').send_keys(passwd)

        def input_data(self, email: str, passwd: str):
            self.input_email(email)
            self.get_vcode_and_input()
            self.input_passwd(passwd)

        def submit(self):
            self.driver.find_element(By.XPATH, '//button[text()="提交"]').click()

        def get_msg(self):
            return self.driver.find_element(By.CSS_SELECTOR, 'p').text if self.driver.current_url != self.url else False
