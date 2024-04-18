from selenium.common import NoSuchElementException
from selenium.webdriver.common.by import By
from selenium.webdriver.edge.webdriver import WebDriver
from selenium.webdriver.remote.webelement import WebElement

from time import sleep


class AdminPage:
    def __init__(self, driver: WebDriver):
        self.driver = driver
        self.main_page_url = 'http://localhost:8080/HSCMS/login.do'

    # 获取主页欢迎语
    def get_main_page_msg(self):
        if self.driver.current_url != self.main_page_url:
            return None
        return self.driver.find_element(By.CSS_SELECTOR, 'h1').text

    # 获取公告内容
    def get_anno(self):
        return self.driver.find_element(By.CSS_SELECTOR, '.layui-footer').text

    # 点击发布公告栏
    def click_publish_announcement(self):
        self.driver.find_element(By.XPATH, '//a[text()="发布公告"]').click()

    # 输入公告内容并提交
    def input_announcement_and_submit(self, context: str):
        self.click_publish_announcement()
        self.driver.find_element(By.CSS_SELECTOR, 'textarea').send_keys(context)
        self.driver.find_element(By.XPATH, '//button[text()="立即提交"]').click()

    # 点击注册审核栏
    def click_registration_audit(self):
        self.driver.find_element(By.XPATH, '//a[text()="注册审核"]').click()

    # 获取注册申请个数
    def get_len_of_application_of_registration(self):
        self.click_registration_audit()
        alist = self.driver.find_elements(By.XPATH, '//tbody/tr')
        return len(alist)

    # 同意第一条注册申请
    def accept_first_registration_application(self):
        self.click_registration_audit()
        email = self.driver.find_element(By.CSS_SELECTOR, 'tbody>tr>th:nth-child(2)').text
        self.driver.find_element(By.CSS_SELECTOR, 'tbody>tr a:nth-child(2)').click()
        sleep(1)  # 等待页面处理
        return email

    # 拒绝第一条注册申请
    def reject_first_registration_application(self):
        self.click_registration_audit()
        email = self.driver.find_element(By.XPATH, 'tbody>tr>th:nth-child(2)').text
        self.driver.find_element(By.CSS_SELECTOR, 'tbody>tr a:nth-child(1)').click()
        sleep(1)  # 等待页面处理
        return email

    # 通过邮箱地址同意注册申请
    def accept_registration_application_by_email(self, email: str):
        self.click_registration_audit()
        try:
            self.driver.find_element(By.XPATH, f'//th[text()="{email}"]/..//div/a[2]').click()
        except NoSuchElementException:
            return False
        except Exception as e:
            print(e)
        else:
            return True

    # 通过邮箱地址拒绝注册申请
    def reject_registration_application_by_email(self, email: str):
        self.click_registration_audit()
        try:
            self.driver.find_element(By.XPATH, f'//th[text()="{email}"]/..//div/a[1]').click()
        except NoSuchElementException:
            return False
        except Exception as e:
            print(e)
        else:
            return True

    # 点击班级审核栏
    def click_class_audit(self):
        self.driver.find_element(By.XPATH, '//a[text()="班级审核"]').click()

    # 获取创建班级申请个数
    def get_len_of_application_of_class(self):
        self.click_class_audit()
        alist = self.driver.find_elements(By.XPATH, '//tbody/tr')
        return len(alist)

    # 同意第一条班级创建申请
    def accept_first_class_application(self):
        self.click_class_audit()
        class_name = self.driver.find_element(By.CSS_SELECTOR, 'tbody>tr>th:nth-child(1)').text
        self.driver.find_element(By.CSS_SELECTOR, 'tbody>tr a:nth-child(2)').click()
        sleep(1)
        return class_name

    # 拒绝第一条班级创建申请
    def reject_first_class_application(self):
        self.click_class_audit()
        class_name = self.driver.find_element(By.CSS_SELECTOR, 'tbody>tr>th:nth-child(1)').text
        self.driver.find_element(By.CSS_SELECTOR, 'tbody>tr a:nth-child(1)').click()
        sleep(1)
        return class_name

    # 通过班级名称同意班级创建申请
    def accept_class_application_by_name(self, class_name: str):
        self.click_class_audit()
        try:
            self.driver.find_element(By.XPATH, f'//th[text()="{class_name}"]/../th[last()]//a[2]').click()
        except NoSuchElementException:
            return False
        except Exception as e:
            print(e)
        else:
            return True

    # 通过班级名称拒绝班级创建申请
    def reject_class_application_by_name(self, class_name: str):
        self.click_class_audit()
        try:
            self.driver.find_element(By.XPATH, f'//th[text()="{class_name}"]/../th[last()]//a[1]').click()
        except NoSuchElementException:
            return False
        except Exception as e:
            print(e)
        else:
            return True
