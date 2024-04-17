from selenium import webdriver
from selenium.webdriver.edge.service import Service
from PageObjects.index_page import IndexPage

from random import randint


def test_tmp():
    driver_path = '../driver/msedgedriver'
    driver = webdriver.Edge(service=Service(driver_path), options=webdriver.EdgeOptions())
    ip = IndexPage(driver)

    email = '628305@qq.com'
    passwd = '628305'

    gp = ip.login_process(email, passwd, '家长')

    mco = gp.select_join_class_to_operate_by_name(gp.get_first_join_class_name())

    mco.publish_communication('测试数据')
    print(mco.filter_communication_records_by_content('测试'))
    print(mco.filter_class_anno_by_time('fasd'))
    print(mco.filter_communication_records_by_time('2024-04-17'))

    driver.quit()
