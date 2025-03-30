from datetime import datetime

tarefas = []

def adicionar_tarefa(tarefas, descricao, prazo_final, urgencia=False):
    """
    Adiciona uma nova tarefa à lista de tarefas.

    Args:
        tarefas (list): Lista de tarefas pendentes.
        descricao (str): Descrição da tarefa.
        prazo_final (str): Prazo final da tarefa (formato: 'YYYY-MM-DD').
        urgencia (bool): Indica se a tarefa é urgente (default: False).

    Returns:
        None
    """
    tarefa = {
        "id": len(tarefas) + 1,
        "descricao": descricao,
        "data_criacao": datetime.now().strftime("%Y-%m-%d %H:%M"),
        "status": "pendente",
        "prazo_final": prazo_final,
        "urgencia": urgencia
    }
    tarefas.append(tarefa)
    print("Tarefa adicionada com sucesso!")

def listar_tarefas(tarefas):
    """
    Lista todas as tarefas pendentes e concluídas.

    Args:
        tarefas (list): Lista de tarefas pendentes.

    Returns:
        None
    """
    if not tarefas:
        print("Nenhuma tarefa encontrada.")
        return
    for tarefa in tarefas:
        print(f"ID: {tarefa['id']}, Descrição: {tarefa['descricao']}, Status: {tarefa['status']}, "
              f"Data de criação: {tarefa['data_criacao']}, Prazo final: {tarefa['prazo_final']}, "
              f"Urgente: {tarefa['urgencia']}")

def marcar_tarefa_concluida(tarefas, tarefa_id):
    """
    Marca uma tarefa como concluída.

    Args:
        tarefas (list): Lista de tarefas pendentes.
        tarefa_id (int): ID da tarefa a ser marcada como concluída.

    Returns:
        None
    """
    for tarefa in tarefas:
        if tarefa["id"] == tarefa_id:
            tarefa["status"] = "concluída"
            print("Tarefa marcada como concluída!")
            return
    print("Tarefa não encontrada.")

def remover_tarefa(tarefas, tarefa_id):
    """
    Remove uma tarefa da lista.

    Args:
        tarefas (list): Lista de tarefas pendentes.
        tarefa_id (int): ID da tarefa a ser removida.

    Returns:
        None
    """
    for i, tarefa in enumerate(tarefas):
        if tarefa["id"] == tarefa_id:
            tarefas.pop(i)
            print("Tarefa removida com sucesso!")
            return
    print("Tarefa não encontrada.")

def menu():
    """
    Exibe o menu de opções para o usuário e executa as operações correspondentes.

    Returns:
        None
    """
    while True:
        print("\nMenu de Gerenciamento de Tarefas:")
        print("1. Adicionar Tarefa")
        print("2. Listar Tarefas")
        print("3. Marcar Tarefa como Concluída")
        print("4. Remover Tarefa")
        print("5. Sair")
        opcao = input("Escolha uma opção: ")

        if opcao == "1":
            descricao = input("Digite a descrição da tarefa: ")
            prazo_final = input("Digite o prazo final (YYYY-MM-DD): ")
            urgencia = input("A tarefa é urgente? (s/n): ").lower() == 's'
            adicionar_tarefa(tarefas, descricao, prazo_final, urgencia)
        elif opcao == "2":
            listar_tarefas(tarefas)
        elif opcao == "3":
            try:
                tarefa_id = int(input("Digite o ID da tarefa a ser marcada como concluída: "))
                marcar_tarefa_concluida(tarefas, tarefa_id)
            except ValueError:
                print("ID inválido.")
        elif opcao == "4":
            try:
                tarefa_id = int(input("Digite o ID da tarefa a ser removida: "))
                remover_tarefa(tarefas, tarefa_id)
            except ValueError:
                print("ID inválido.")
        elif opcao == "5":
            print("Saindo do programa...")
            break
        else:
            print("Opção inválida, tente novamente.")

menu()
