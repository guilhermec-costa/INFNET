import os
from db import inicializar_banco, carregar_csv_para_db
from constants import DB_FILE
import app_flow

def main():
    inicializar_banco()
    if not os.path.exists(DB_FILE):
        print("Banco não encontrado. Execute o script SQL antes de iniciar o programa.")
        return
    carregar_csv_para_db()
    app_flow.atendimento()

if __name__ == "__main__":
    main()
