import db_setup

class ReportGenerator:

    def __init__(self, db_manager: db_setup.DbManager):
        self.db_manager = db_manager

    def generate_report(self):
        self.db_manager.cursor.execute("SELECT site, COUNT(*) FROM logs GROUP BY site")
        erros = self.db_manager.cursor.fetchall()
        print("\n### Relatório de Scraping ###")
        print("- Erros por site:")
        for site, count in erros:
            print(f"  {site}: {count} erro(s)")

        self.db_manager.cursor.execute("SELECT site, COUNT(*) FROM metadados GROUP BY site")
        sucessos = self.db_manager.cursor.fetchall()
        print("- Sucessos por site:")
        for site, count in sucessos:
            print(f"  {site}: {count} sucesso(s)")

        print("Relatório de scraping gerado.")
