from selenium import webdriver
from selenium.webdriver.edge.service import Service


def init_driver():
    driver_path = '/home/fws/Code/eclipse-workspace/HSCMS/testHscms/driver/msedgedriver'
    driver = webdriver.Edge(service=Service(driver_path), options=webdriver.EdgeOptions())
    driver.implicitly_wait(5)
    driver.maximize_window()
    return driver
