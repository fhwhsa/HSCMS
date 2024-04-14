import allure
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.edge.service import Service
from time import sleep


class TestAdminLogin:
    def setup_class(self):
        driver_path = '/home/fws/Code/eclipse-workspace/HSCMS/testHscms/driver/msedgedriver'
        self.driver = webdriver.Edge(service=Service(driver_path), options=webdriver.EdgeOptions())
        self.driver.implicitly_wait(5)
        self.driver.maximize_window()

    def teardown_class(self):
        self.driver.quit()

    @allure.story("管理员登陆用例")
    def test_login(self):
        email_path = 'admin@hscms.com'
        passwd = '123456'
        with allure.step("进入网页"):
            self.driver.get('http://localhost:8080/HSCMS/index.jsp')
        with allure.step("输入默认管理员邮箱"):
            self.driver.find_element(By.CSS_SELECTOR, 'input[name="emailAddr"]').send_keys(email_path)
        with allure.step("输入默认管理员密码"):
            self.driver.find_element(By.CSS_SELECTOR, 'input[name="password"]').send_keys(passwd)
        with allure.step("选择用户类型为管理员"):
            self.driver.find_element(By.CSS_SELECTOR, 'select+div input').click()
            self.driver.find_element(By.CSS_SELECTOR, 'dl>dd:nth-child(3)').click()
        with allure.step("点击登陆按钮"):
            self.driver.find_element(By.CSS_SELECTOR, 'button').click()
        with allure.step("断言是否与预期结果一致"):
            assert self.driver.find_element(By.CSS_SELECTOR, 'h1').text == 'hello fws'

        



