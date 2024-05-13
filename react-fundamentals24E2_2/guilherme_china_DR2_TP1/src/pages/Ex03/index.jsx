import React from "react";
import "./style.css"

const Ex03 = () => {
    let nome = "João"
    nome = "Maria"

    const sobrenome = "Silva"
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
