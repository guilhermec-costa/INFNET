def exercicio1():
  print("=" * 20);
  num1 = float(input("Digite o primeiro número: "))
  num2 = float(input("Digite o segundo número: "))

  print("Soma:", num1 + num2)
  print("Subtração:", num1 - num2)
  print("Multiplicação:", num1 * num2)
  print("Divisão:", num1 / num2 if num2 != 0 else "Divisão por zero não é permitida")
  print("Divisão Inteira:", num1 // num2 if num2 != 0 else "Divisão por zero não é permitida")

def exercicio2():
  print("=" * 20);
  minutos = int(input("Digite o número de minutos: "))
  horas = minutos // 60
  minutos_restantes = minutos % 60
  print(f"{minutos} minutos equivalem a {horas} horas e {minutos_restantes} minutos.");

  horas = int(input("Digite as horas: "))
  minutos = int(input("Digite os minutos: "))
  minutos_totais = horas * 60 + minutos
  print(f"{horas} horas e {minutos} minutos equivalem a {minutos_totais} minutos.")

def exercicio3():
  print("=" * 20);
  nome1 = input("Digite o primeiro nome: ")
  nome2 = input("Digite o segundo nome: ")
  nome_combinado = nome1[:len(nome1)//2] + nome2[len(nome2)//2:]
  print("Nome combinado:", nome_combinado)

def exercicio4():
  print("=" * 20);
  operacao = input("Escolha uma operação (+, -, *, /): ")
  num1 = float(input("Digite o primeiro número: "))
  num2 = float(input("Digite o segundo número: "))

  if operacao == "+":
      print("Resultado:", num1 + num2)
  elif operacao == "-":
      print("Resultado:", num1 - num2)
  elif operacao == "*":
      print("Resultado:", num1 * num2)
  elif operacao == "/" and num2 != 0:
      print("Resultado:", num1 / num2)
  else:
      print("Operação inválida ou divisão por zero.")

def exercicio5():
  print("=" * 20);
  nome = input("Digite seu nome: ")
  sobrenome = input("Digite seu sobrenome: ")
  print(f"Olá, {nome} {sobrenome}! Seja bem-vindo(a)!")

def exercicio6():
  print("=" * 20);
  from random import randint
  numero_secreto = randint(1, 100)
  palpite = int(input("Adivinhe o número entre 1 e 100: "))

  if palpite == numero_secreto:
      print("Parabéns! Você acertou!")
  elif palpite < numero_secreto:
      print("Muito baixo!")
  else:
      print("Muito alto!")

def exercicio7():
  print("=" * 20);
  peso = float(input("Digite seu peso em kg: "))
  altura = float(input("Digite sua altura em metros: "))
  imc = peso / (altura ** 2)

  print("Seu IMC é:", imc)
  if imc < 18.5:
      print("Abaixo do peso")
  elif 18.5 <= imc < 24.9:
      print("Peso normal")
  elif 24.9 <= imc < 29.9:
      print("Sobrepeso")
  else:
      print("Obesidade")

def exercicio8():
  print("=" * 20);
  idade = int(input("Digite sua idade: "))
  if idade >= 18:
    print("Você é maior de idade.")
  else:
    print("Você é menor de idade.")

def exercicio9():
  print("=" * 20);
  valor_compra = float(input("Digite o valor da compra: R$"))

  if valor_compra > 500:
      desconto = 0.25
  elif valor_compra > 200:
      desconto = 0.15
  elif valor_compra > 100:
      desconto = 0.10
  else:
      desconto = 0.0

  valor_final = valor_compra * (1 - desconto)
  print(f"Desconto aplicado: {desconto * 100}%. Valor final: R${valor_final:.2f}")

def exercicio10():
  print("=" * 20);
  from random import choice

  personagens = ["um dragão", "um pirata", "um robô"]
  acoes = ["encontrou um tesouro", "salvou uma cidade", "conquistou um planeta"]
  locais = ["na montanha", "no mar", "no espaço"]
  historia = f"{choice(personagens)} {choice(acoes)} {choice(locais)}."
  print("História:", historia)

def exercicio11():
  print("=" * 20);
  from random import randint;
  num_dados = int(input("Quantos dados você deseja lançar? "))
  for i in range(num_dados):
    print(f"Dado {i+1}: {randint(1, 6)}")

def exercicio12():
  print("=" * 20);
  palavras = input("Digite palavras separadas por espaço: ").split()
  curtas = [p for p in palavras if len(p) < 5]
  longas = [p for p in palavras if len(p) >= 5]

  print("Palavras curtas:", curtas)
  print("Palavras longas:", longas)

def exercicio13():
  print("=" * 20);
  palavra = input("Digite uma palavra ou frase: ").replace(" ", "").lower()
  if palavra == palavra[::-1]:
      print("É um palíndromo!")
  else:
      print("Não é um palíndromo.")

def exercicio14():
  print("=" * 20);
  opcoes = {"Opção 1": 0, "Opção 2": 0, "Opção 3": 0}
  for _ in range(5):  
      voto = input("Vote em 'Opção 1', 'Opção 2' ou 'Opção 3': ")
      if voto in opcoes:
          opcoes[voto] += 1
      else:
          print("Voto inválido!")

  print("Resultado da votação:", opcoes)

def exercicio15():
  print("=" * 20);
  escolha1 = input("Você está em uma floresta. Deseja ir para 'esquerda' ou 'direita'? ")
  if escolha1 == "esquerda":
      escolha2 = input("Você encontra um lago. Deseja 'nadar' ou 'caminhar' ao redor? ")
      if escolha2 == "nadar":
          print("Você foi atacado por um peixe! Fim do jogo.")
      else:
          print("Você encontrou um tesouro escondido! Parabéns!")
  else:
      print("Você encontrou um caminho seguro para casa! Parabéns!")

def exercicio16():
  print("=" * 20);
  numero = int(input("Digite um número: "))
  if numero % 2 == 0:
      print("O número é par.")
  else:
      print("O número é ímpar.")