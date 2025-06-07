def validar_inteiro(valor):
    try:
        v = int(valor)
        return v if v > 0 else None
    except:
        return None