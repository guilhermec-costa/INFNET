import React from "react";
import "./style.css"
/* Declare um objeto "pessoa" com as propriedades "nome", "idade" e "sobrenome". Altere o valor da propriedade "idade" do objeto "pessoa" para 35. 
Adicione uma nova propriedade "profissao" ao objeto "pessoa".Declare um array "familia" com os objetos "pessoa", mais outras pessoas da família. 
Adicione um novo membro à família (um novo objeto ao array "familia"). Altere o valor da propriedade "nome" de uma das pessoas no array "familia".  
Use funcões e expressões JavaScript para mostrar os dados. */
const Ex05 = () => {
    let pessoa = {
        nome: "Churros",
        idade: 9,
        sobrenome: "Augusto"
    }

    pessoa.idade = 35;
    pessoa.profissao = undefined;
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

    pessoa.familia[1].nome = "Churros20";
    console.log(pessoa);

    return <div><p>
        {JSON.stringify(pessoa, null, 4)}
    </p></div>
}
export default Ex05;
