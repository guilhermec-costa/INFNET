import React from "react";
import "./style.css"

/* Declare uma variável "nome" com o valor "João" usando a diretiva "let". Altere o valor da variável "nome" para "Maria". Declare uma variável "sobrenome" com o valor "Silva" usando a diretiva "const". 
Tente alterar o valor da variável "sobrenome". O que acontece? Por quê? Exiba a resposta para a pergunta numa tag parágrafo dentro de uma div. */

const Ex03 = () => {
    let nome = "João";
    nome = "Maria";

    const sobrenome = "Silva";
    /* sobrenome = "Costa" */
    return (
        <div>
            <p>
                Não é possível trocar o valor da constante "sobrenome", justamente por ela ser constante. Não é mutável.
            </p>
        </div>
    )
}

export default Ex03;
