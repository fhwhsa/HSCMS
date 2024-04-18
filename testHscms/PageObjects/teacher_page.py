from selenium.common import TimeoutException, NoSuchElementException
from selenium.webdriver.common.by import By
from selenium.webdriver.edge.webdriver import WebDriver
from selenium.webdriver.support import expected_conditions
from selenium.webdriver.support.wait import WebDriverWait
from time import sleep


class TeacherPage:

    def __init__(self, driver: WebDriver):
        self.driver = driver

    # 点击创建班级按钮
    def click_create_class(self):
        self.driver.find_element(By.XPATH, '//a[text()="创建班级"]').click()

    # 创建班级
    def create_class(self, class_name: str):
        self.click_create_class()
        self.driver.find_element(By.XPATH, '//input[@name="className"]').send_keys(class_name)
        self.driver.find_element(By.XPATH, '//button[text()="提交"]').click()

    # 点击班级管理
    def click_class_management(self):
        self.driver.find_element(By.XPATH, '//a[text()="班级管理"]').click()

    # 获取班级管理的第一个
    def get_first_create_class_name(self):
        self.click_class_management()
        try:
            text = self.driver.find_element(By.CSS_SELECTOR, '.layui-card .layui-card-body').text
        except NoSuchElementException:
            return None
        except Exception as e:
            print(e)
        else:
            return text[5:text.find("创建时间") - 1]

    # 通过班级名字查找是否包含在创建的班级中
    def find_create_class_by_name(self, class_name: str):
        self.click_class_management()
        clist = self.driver.find_elements(By.XPATH, '//div[@class="layui-card-body"]')
        for c in clist:
            if class_name in c.text:
                return c
        return None

    # 通过班级名称选择要管理的班级
    def select_create_class_to_manage_by_name(self, class_name=None):
        self.click_class_management()
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
            return self.TeacherClassManage(self.driver)

    # 班级管理类
    class TeacherClassManage:
        def __init__(self, driver: WebDriver):
            self.driver = driver

        # 点击班级信息
        def click_class_info(self):
            self.driver.find_element(By.XPATH, '//li[text()="班级信息"]').click()

        # 获取当前管理班级的编号
        def get_class_no(self):
            self.click_class_info()
            return self.driver.find_element(By.XPATH, '//div[contains(@class, "layui-show")]/p[1]').text[5:]

        # 获取当前管理班级的名称
        def get_class_name(self):
            self.click_class_info()
            return self.driver.find_element(By.XPATH, '//div[contains(@class, "layui-show")]/p[2]').text[5:]

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

        # 点击发布通知
        def click_publish_anno(self):
            self.driver.find_element(By.XPATH, '//li[text()="发布通知"]').click()

        # 发布班级公告
        def publish_class_anno(self, context: str):
            self.click_publish_anno()
            self.driver.find_element(By.CSS_SELECTOR, 'textarea').send_keys(context)
            self.driver.find_element(By.XPATH, '//button[@type="submit"]').click()

        # 获取最新一条公告
        def get_last_class_anno(self):
            self.click_publish_anno()
            try:
                text = self.driver.find_element(By.XPATH,
                                                '//div[contains(@style, "display")][last()]//div[@class="layui-card-body"]').text
            except NoSuchElementException:
                return None
            except Exception as e:
                print(e)
            else:
                return text

        # 点击站内交流
        def click_class_communication(self):
            self.driver.find_element(By.XPATH, '//li[text()="站内交流"]').click()

        # 站内交流发信息
        def publish_communication_info(self, context: str):
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

        # 点击班级审核
        def click_class_audit(self):
            self.driver.find_element(By.XPATH, '//li[text()="班级审核"]').click()

        # 获取加入当前班级的申请条数
        def get_len_of_application_of_join_class(self):
            self.click_class_audit()
            alist = self.driver.find_elements(By.XPATH, '//tbody/tr')
            return len(alist)

        # 同意第一条加入当前班级的申请
        def accept_first_join_class_application(self):
            self.click_class_audit()
            self.driver.find_element(By.CSS_SELECTOR, 'tbody>tr a:nth-child(2)').click()

        # 拒绝第一条加入当前班级的申请
        def reject_first_join_class_application(self):
            self.click_class_audit()
            self.driver.find_element(By.CSS_SELECTOR, 'tbody>tr a').click()

        # 通过申请人邮箱地址同意加入班级申请
        def accept_join_class_application_by_email(self, email: str):
            self.click_class_audit()
            try:
                self.driver.find_element(By.XPATH, f'//th[text()="{email}"]/../th[last()]//a[2]').click()
            except NoSuchElementException:
                return False
            except Exception as e:
                print(e)
            else:
                return True

        # 通过申请人邮箱地址拒绝加入班级申请
        def reject_join_class_application_by_email(self, email: str):
            self.click_class_audit()
            try:
                self.driver.find_element(By.XPATH, f'//th[text()="{email}"]/../th[last()]//a[1]').click()
            except NoSuchElementException:
                return False
            except Exception as e:
                print(e)
            else:
                return True
