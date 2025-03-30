import React from "react";
import "./style.css"

/* 
Declare uma variável "nota" com o valor 7. Utilize uma estrutura condicional para verificar a nota e exibir a seguinte mensagem: 
Se a nota for menor que 5, exiba "Nota insuficiente". Se a nota for igual a 5, exiba "Nota suficiente". 
Se a nota for maior que 5 e menor que 8, exiba "Nota boa". Se a nota for maior ou igual a 8, exiba "Nota excelente".  
Use funcões e expressões JavaScript para mostrar os dados.
*/

const Ex07 = () => {
    const nota = 7
    function verifyGrade(grade) {
        if (grade < 5) {
            return "Nota insuficiente";
        } else if (grade === 5) {
            return "Nota suficiente";
        } else if (grade > 5 && grade < 8) {
            return "Nota boa";
        } else {
            return "Nota excelente";
        }
    }

    return <p>{verifyGrade(nota)}</p>
}

export default Ex07;
