namespace TP1
{
    class Exercicio11
    {
        public static void Execute()
        {
            Console.WriteLine("=== Exercício 11 ===");
            Func<string, string, string> processador = (nome, sobrenome) =>
            {
                string resultado = $"{nome} {sobrenome}";
                Console.WriteLine($"Concatenação: {resultado}");
                return resultado;
            };

            processador += (nome, sobrenome) =>
            {
                string resultado = $"{nome} {sobrenome}".ToUpper();
                Console.WriteLine($"Maiúsculas: {resultado}");
                return resultado;
            };

            processador += (nome, sobrenome) =>
            {
                string resultado = $"{nome}{sobrenome}".ToUpper();
                Console.WriteLine($"Sem espaços: {resultado}");
                return resultado;
            };

            Console.WriteLine("=== Execução do delegate encadeado ===");
            string final = processador("João", "Silva");
            Console.WriteLine($"Valor retornado pelo delegate: {final}");
        }
    }
}
