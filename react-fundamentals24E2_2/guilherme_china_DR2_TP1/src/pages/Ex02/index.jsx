import React from "react";
import "./style.css"

const Ex02 = () => {
    return (
        <div className="explanation">
            <h1>O que é CDN?</h1>
            <div className="separator"></div>
            <p>
                "CDN" significa "Content Delivery Network" (Rede de Distribuição de Conteúdo, em português). É uma rede de servidores distribuídos geograficamente que trabalham juntos para fornecer conteúdo da web aos usuários.
                Esses servidores armazenam em cache o conteúdo estático de um site, como imagens, arquivos CSS, JavaScript e outros tipos de arquivos de mídia.
                Quando um usuário solicita acesso a um site, a CDN redireciona essa solicitação para o servidor mais próximo geograficamente do usuário, em vez de enviar a solicitação
                diretamente ao servidor de origem do site. Isso reduz a latência e o tempo de carregamento da página, melhorando a experiência do usuário.
                Além disso, as CDNs também oferecem outras vantagens, como proteção contra ataques DDoS (negação de serviço distribuída), balanceamento de carga e otimização de recursos.   </p>
        </div>
    )
}

export default Ex02;
