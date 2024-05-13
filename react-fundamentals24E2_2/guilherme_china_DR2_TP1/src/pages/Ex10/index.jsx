import React from "react";
import "./style.css"
import { faker } from "@faker-js/faker"

/* Crie uma página simples que exiba uma lista com 10 itens gerados pelo Faker a sua escolha. */
/* Assim que terminar, salve o link do projeto com os exercícios realizados em PDF nomeando o arquivo conforme a regra "nome_sobrenome_DR2_TP1.PDF” e poste como resposta a este TP. */
const Ex10 = () => {
    const fakerItems = new Array(10).fill(0).map(item => faker.person.fullName())
    return (
        <div>
            {fakerItems.map(item => <p>{item}</p>)}
        </div>
    )
}

export default Ex10;
