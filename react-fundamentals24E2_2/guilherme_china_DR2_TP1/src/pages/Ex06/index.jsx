import React from "react";
import "./style.css"
/* Declare uma variável "idade" com o valor 30. Utilize uma estrutura condicional para verificar se a idade é maior ou igual a 18. 
Se for, exiba a mensagem "Você é maior de idade". Senão, exiba a mensagem "Você é menor de idade". Declare um array "compras" com os itens "Leite", "Pão" e "Ovos". 
Utilize um loop para exibir todos os itens do array "compras". Utilize uma estrutura condicional dentro do loop para exibir a mensagem "Este item é essencial" somente para o item "Leite".
Use funcões e expressões  */
const Ex06 = () => {
    const idade = 30;
    const compras = ["Leite", "Pão", "Ovos"]

    return (
        <div>
            {idade >= 18 ?
                "Você é maior de idade" :
                "Você é menor de idade"}
            <br />
            {compras.map(compra => {
                if (compra === "Leite") {
                    return <p>
                        Esse item é essencial: {compra}
                    </p>
                }
                return <p>compra</p>
            })}
        </div>
    )
}

export default Ex06;
