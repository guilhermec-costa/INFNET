import React from "react";
import "./style.css"

const Ex05 = () => {
    let pessoa = {
        nome: "Churros",
        idade: 9,
        sobrenome: "Augusto"
    }

    pessoa.idade = 35
    pessoa.profissao = undefined
    pessoa.familia = [
        { ...pessoa },
        {
            nome: "Churros1",
            idade: 10,
            sobrenome: "Augusto1"
        },
        {
            nome: "Churros2",
            idade: 13,
            sobrenome: "Augusto2"
        }
    ]

    pessoa.familia = [
        ...pessoa.familia,
        {
            nome: "Churros3",
            idade: 14,
            sobrenome: "Augusto3"
        }
    ]

    pessoa.familia[1].nome = "Churros20"
    console.log(pessoa)

    return <div><p>
        {JSON.stringify(pessoa, null, 4)}
    </p></div>
}
export default Ex05;
