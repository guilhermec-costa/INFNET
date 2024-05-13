import React from "react";
import "./style.css"

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
