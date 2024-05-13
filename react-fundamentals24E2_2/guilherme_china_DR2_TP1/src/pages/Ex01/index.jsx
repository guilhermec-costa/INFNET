import React from "react";
import "./style.css"

/* Crie uma página básica em React com HTML, CSS e JavaScript que exiba a resposta para a pergunta "O que é React?" numa tag parágrafo dentro de uma div. */
const Ex01 = () => {
    return (
        <div className="explanation">
            <h1>O que é React?</h1>
            <div className="separator"></div>
            <p>
                React é uma biblioteca JavaScript de código aberto usada para criar
                interfaces de usuário, especialmente para aplicativos de página única (SPA).
                Desenvolvida pelo Facebook, ela permite que os desenvolvedores construam componentes reutilizáveis que lidam com a
                renderização eficiente dos dados na interface do usuário. O React utiliza uma abordagem baseada em componentes,
                onde cada parte da interface é encapsulada em um componente, permitindo um desenvolvimento mais modular e organizado.
                Além disso, o React utiliza um conceito chamado de Virtual DOM para otimizar a performance,
                atualizando apenas as partes da interface que foram alteradas, em vez de re-renderizar toda a página.
            </p>
        </div>
    )
}

export default Ex01;
