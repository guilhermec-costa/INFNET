from db_setup import DbManager
from report_generator import ReportGenerator
from scraper import Scraper

def main():
    db_manager = DbManager()
    db_manager.setup()

    scraper = Scraper(db_manager)
    reporter = ReportGenerator(db_manager)

    scraper.scrape_ibge()
    scraper.scrape_bbc()
    reporter.generate_report()
    scraper.driver.quit()
    db_manager.cursor.close()

if __name__ == "__main__":
    main()