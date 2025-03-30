import re
import requests
from bs4 import BeautifulSoup
import constants as cons
from db_setup import DbManager
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options

class Scraper:
    def __init__(self, db_manager: DbManager):
        self.db_manager = db_manager
        self.init_chrome_driver()

    @staticmethod
    def make_chromium_options() -> Options:
        chrome_options = Options()
        chrome_options.add_argument("--headless")
        chrome_options.add_argument("--no-sandbox")
        chrome_options.add_argument("--disable-dev-shm-usage")
        return chrome_options

    def init_chrome_driver(self):
        service = Service(cons.CHROMEDRIVER_PATH)
        self.driver = webdriver.Chrome(service=service, options=Scraper.make_chromium_options())

    def scrape_ibge(self):
        try:
            response = requests.get(cons.IBGE_URL)
            response.raise_for_status()
            linhas = response.text.split("\n")
            for linha in linhas:
                match = re.search(r'^(Tabela [\d\.]+(?: - .*)?)$', linha)
                if match:
                    titulo = match.group(1).strip()
                    self.db_manager.cursor.execute("INSERT INTO dados_ibge (titulo, link) VALUES (?, ?)", (titulo, ""))
            self.db_manager.conn.commit()
            self.db_manager.save_metadata("IBGE", "Sucesso")
            print("Scrape do IBGE finalizado.")
        except Exception as e:
            self.db_manager.save_log("IBGE", str(e))
            print(f"[ERRO] Scrape do IBGE falhou: {e}")

    def scrape_bbc(self):
        try:
            self.driver.get(cons.BBC_URL)
            soup = BeautifulSoup(self.driver.page_source, "html.parser")
            artigos = soup.find_all("a", {"href": True})  
            
            for artigo in artigos:
                titulo = artigo.get_text().strip()
                link = artigo["href"]
                
                if link.startswith("https://"):  
                    self.db_manager.cursor.execute("INSERT INTO dados_bbc (titulo, url) VALUES (?, ?)", (titulo, link))
                    
            self.db_manager.conn.commit()
            self.db_manager.save_metadata("BBC", "Sucesso")
            print("Scrape do BBC finalizado.")
            
        except Exception as e:
            self.db_manager.save_log("BBC", str(e))
            print(f"[ERRO] Scrape do BBC falhou: {e}")