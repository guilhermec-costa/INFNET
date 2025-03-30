import constants as cons
import sqlite3

class DbManager:
    def __init__(self):
        self.conn = sqlite3.connect(cons.DB_PATH)
        self.cursor = self.conn.cursor()

    def setup(self):
        self.cursor.executescript('''
        CREATE TABLE IF NOT EXISTS dados_ibge (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            titulo TEXT,
            link TEXT
        );

        CREATE TABLE IF NOT EXISTS dados_bbc (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            titulo TEXT,
            url TEXT
        );

        CREATE TABLE IF NOT EXISTS metadados (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            site TEXT,
            data_hora TEXT,
            status TEXT
        );

        CREATE TABLE IF NOT EXISTS logs (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            site TEXT,
            erro TEXT,
            data_hora TEXT
        );
        ''')
        self.conn.commit()
        print("Banco de dados iniciado.")

    def save_log(self, site, erro):
        self.cursor.execute("INSERT INTO logs (site, erro, data_hora) VALUES (?,?, datetime('now'))", (site, erro))
        self.cursor.commit()

    def save_metadata(self, site, status):
        self.cursor.execute("INSERT INTO metadados (site, data_hora, status) VALUES (?, datetime('now'), ?)", (site, status))
        self.conn.commit()