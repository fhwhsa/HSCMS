from selenium import webdriver
from selenium.webdriver.edge.service import Service
from PageObjects.index_page import IndexPage

from random import randint


def test_tmp():
    driver_path = '../driver/msedgedriver'
    driver = webdriver.Edge(service=Service(driver_path), options=webdriver.EdgeOptions())
    ip = IndexPage(driver)

    email = '199629@qq.com'
    passwd = '199629'

    tp = ip.login_process(email, passwd, '老师')

    tcm = tp.select_create_class_to_manage_by_name('test111')

    tcm.accept_join_class_application_by_email('682165@qq.com')
    tcm.reject_join_class_application_by_email('832113@qq.com')

    driver.quit()
