import React from "react";
import "./style.css"
/* Declare uma variável "idade" com o valor "30" usando a diretiva "let". Crie uma variável "ehMaiorDeIdade" que verifica se a idade é maior ou igual a 18 anos. 
Declare uma variável "nomeCompleto" com o valor "João Silva" usando a diretiva "let". Altere o valor da variável "nomeCompleto" para "Maria Silva".
 Declare uma variável "casado" com o valor "false" usando a diretiva "let". Altere o valor da variável "casado" para "true. Use funcões e expressões JavaScript para mostrar os dados. */

const Ex04 = () => {
    let idade = 30;
    let ehMaiorDeIdade = idade >= 18;

    let nomeCompleto = "João Silva";
    nomeCompleto = "Maria Silva";

    let casado = false;
    casado = true;

    function mostrarValores() {
        return (
            <div>
                <p style={{ display: "block" }}>
                    Idade: {idade}
                </p>
                <p style={{ display: "block" }}>
                    É maior de idade: {ehMaiorDeIdade ? "Sim" : "Não"}
                </p>
                <p style={{ display: "block" }}>
                    Nome Completo: {nomeCompleto}
                </p>
                <p style={{ display: "block" }}>
                    É casado: {casado ? "Sim" : "Não"}
                </p>
            </div>
        )
    }

    return (
        <div>
            {mostrarValores()}
        </div>
    )
}

export default Ex04;
