from selenium.webdriver.common.by import By
from selenium.webdriver.edge.webdriver import WebDriver
from time import sleep

from selenium.webdriver.support import expected_conditions
from selenium.webdriver.support.wait import WebDriverWait

from PageObjects.admin_page import AdminPage
from PageObjects.forget_passwd_page import SelectFTypePage
from PageObjects.guardian_page import GuardianPage
from PageObjects.registration_page import SelectRTypePage
from PageObjects.teacher_page import TeacherPage


class IndexPage:
    def __init__(self, driver: WebDriver):
        self.driver = driver
        self.url = "http://localhost:8080/HSCMS/index.jsp"
        self.open_url()

    def open_url(self):
        self.driver.get(self.url)

    def input_mail(self, email_addr):
        self.driver.find_element(By.CSS_SELECTOR, 'input[name="emailAddr"]').send_keys(email_addr)

    def input_passwd(self, passwd):
        self.driver.find_element(By.CSS_SELECTOR, 'input[name="password"]').send_keys(passwd)

    def select_login_type(self, selected_type):
        self.driver.find_element(By.CSS_SELECTOR, 'select+div input').click()
        self.driver.find_element(By.XPATH, f'//dd[text()="{selected_type}"]').click()

    def click_login(self, selected_type):
        self.driver.find_element(By.CSS_SELECTOR, 'button').click()
        sleep(2)  # 等待页面刷新
        if self.driver.current_url == self.url:
            return None
        elif selected_type == "家长":
            return GuardianPage(self.driver)
        elif selected_type == "老师":
            return TeacherPage(self.driver)
        else:
            return AdminPage(self.driver)

    def click_register(self):
        WebDriverWait(self.driver, 5).until(
            expected_conditions.element_to_be_clickable(
                (By.CSS_SELECTOR, 'a:nth-child(2)')
            )
        ).click()
        sleep(2)  # 等待页面刷新
        return None if self.driver.current_url == self.url else SelectRTypePage(self.driver)

    def click_forget_passwd(self):
        self.driver.find_element(By.CSS_SELECTOR, 'a:nth-child(2)').click()
        sleep(2)  # 等待页面刷新
        return None if self.driver.current_url == self.url else SelectFTypePage(self.driver)

    def login_process(self, email_addr, passwd, selected_type):
        if selected_type not in ('家长', '老师', '管理员'):
            raise ValueError('Invalid selected_type value')
        self.input_mail(email_addr)
        self.input_passwd(passwd)
        self.select_login_type(selected_type)
        return self.click_login(selected_type)
