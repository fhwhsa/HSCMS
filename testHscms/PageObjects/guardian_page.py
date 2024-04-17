from selenium.common import NoSuchElementException, TimeoutException
from selenium.webdriver.common.by import By
from selenium.webdriver.edge.webdriver import WebDriver
from time import sleep

from selenium.webdriver.support import expected_conditions
from selenium.webdriver.support.wait import WebDriverWait


class GuardianPage:

    def __init__(self, driver: WebDriver):
        self.driver = driver

    # 点击加入班级
    def click_join_class(self):
        self.driver.find_element(By.XPATH, '//a[text()="加入班级"]').click()

    # 加入班级
    def join_class(self, class_no: str):
        self.click_join_class()
        self.driver.find_element(By.CSS_SELECTOR, 'input').send_keys(class_no)
        self.driver.find_element(By.CSS_SELECTOR, 'button').click()
        sleep(1)
        info = self.driver.find_element(By.CSS_SELECTOR, 'p').text
        if "不存在" in info:
            return False
        return True

    # 点击我的班级
    def click_my_class(self):
        self.driver.find_element(By.XPATH, '//a[text()="我的班级"]').click()

    # 获取我的班级的第一个
    def get_first_join_class_name(self):
        self.click_my_class()
        try:
            text = self.driver.find_element(By.CSS_SELECTOR, '.layui-card .layui-card-body').text
        except NoSuchElementException:
            return None
        except Exception as e:
            print(e)
        else:
            return text[5:text.find("创建时间") - 1]

    # 通过班级名字查找是否包含在加入的班级中
    def find_join_class_by_name(self, class_name: str):
        self.click_my_class()
        clist = self.driver.find_elements(By.XPATH, '//div[@class="layui-card-body"]')
        for c in clist:
            if class_name in c.text:
                return c
        return None

    # 通过班级名称选择要进行操作的班级
    def select_join_class_to_operate_by_name(self, class_name=None):
        self.click_my_class()
        try:
            WebDriverWait(self.driver, 3).until(
                expected_conditions.element_to_be_clickable(
                    (By.XPATH, f'//div[contains(text(), "{class_name}")]/..')
                )
            ).click()
        except TimeoutException:
            return None
        except Exception as e:
            print(e)
        else:
            return self.MyClassOption(self.driver)

    # 我的班级（家长角色）操作类
    class MyClassOption:

        def __init__(self, driver: WebDriver):
            self.driver = driver

        # 点击班级信息
        def click_class_info(self):
            self.driver.find_element(By.XPATH, '//li[text() = "班级信息"]').click()

        # 获取班级编号
        def get_class_no(self):
            self.click_class_info()
            return self.driver.find_element(By.XPATH, '//div[contains(@class, "layui-show")]/p[1]').text[5:]

        # 获取班级名称
        def get_class_name(self):
            self.click_class_info()
            return self.driver.find_element(By.XPATH, '//div[contains(@class, "layui-show")]/p[2]').text[5:]

        # 获取班级老师邮箱
        def get_teacher_email(self):
            self.click_class_info()
            return self.driver.find_element(By.XPATH, '//div[contains(@class, "layui-show")]/p[4]').text[5:]

        # 点击班级成员
        def click_class_member(self):
            self.driver.find_element(By.XPATH, '//li[text()="班级成员"]').click()

        # 获取当前所有班级成员的邮箱
        def get_class_member_email_list(self):
            self.click_class_member()
            alist = self.driver.find_elements(By.XPATH, '//tbody/tr/th[2]')
            email_list = []
            for a in alist:
                email_list.append(a.text)
            return email_list

        # 点击班级通知
        def click_class_anno(self):
            self.driver.find_element(By.XPATH, '//li[text()="班级通知"]').click()

        # 获取最近一条公告
        def get_last_class_anno(self):
            self.click_class_anno()
            try:
                text = self.driver.find_element(By.XPATH, '//div[contains(@class, "layui-card")][last()]//div[@class="layui-card-body"]').text
            except NoSuchElementException:
                return None
            except Exception as e:
                print(e)
            else:
                return text

        # 根据内容筛选班级通知
        def filter_class_anno_by_content(self, filter_context: str):
            self.click_class_anno()
            self.driver.find_element(By.XPATH, '//input[@name="contxt"]').send_keys(filter_context)
            self.driver.find_element(By.CSS_SELECTOR, 'button').click()
            sleep(1)  # 等待页面刷新
            elist = self.driver.find_elements(By.XPATH, '//div[@class="layui-card-body"]')
            context_list = []
            for e in elist:
                context_list.append(e.text)
            return context_list

        # 根据时间筛选班级通知
        def filter_class_anno_by_time(self, filter_time: str):
            self.click_class_anno()
            self.driver.find_element(By.XPATH, '//input[@name="date"]').send_keys(filter_time)
            self.driver.find_element(By.CSS_SELECTOR, 'button').click()
            sleep(1)  # 等待页面刷新
            elist = self.driver.find_elements(By.XPATH, '//div[@class="layui-card-body"]')
            context_list = []
            for e in elist:
                context_list.append(e.text)
            return context_list

        # 点击站内交流
        def click_class_communication(self):
            self.driver.find_element(By.XPATH, '//li[text()="站内交流"]').click()

        # 站内交流发信息
        def publish_communication(self, context: str):
            self.click_class_communication()
            self.driver.find_element(By.CSS_SELECTOR, 'textarea').send_keys(context)
            self.driver.find_element(By.XPATH, '//button[text()="立即提交"]').click()

        # 站内交流通过内容筛选信息，返回筛选出来的信息列表
        def filter_communication_records_by_content(self, filter_context: str):
            self.click_class_communication()
            self.driver.find_element(By.XPATH, '//input[@name="contxt-filter"]').send_keys(filter_context)
            self.driver.find_element(By.XPATH, '//button[text()="筛选"]').click()
            sleep(1)  # 等待页面刷新
            elist = self.driver.find_elements(By.XPATH, '//div[@class="layui-card-body"]')
            context_list = []
            for e in elist:
                context_list.append(e.text)
            return context_list

        # 站内交流通过时间筛选信息yyyy-mm-dd，返回筛选出来的信息列表
        def filter_communication_records_by_time(self, filter_time: str):
            self.click_class_communication()
            self.driver.find_element(By.XPATH, '//input[@name="date"]').send_keys(filter_time)
            self.driver.find_element(By.XPATH, '//button[text()="筛选"]').click()
            sleep(1)  # 等待页面刷新
            elist = self.driver.find_elements(By.XPATH, '//div[@class="layui-card-body"]')
            context_list = []
            for e in elist:
                context_list.append(e.text)
            return context_list

