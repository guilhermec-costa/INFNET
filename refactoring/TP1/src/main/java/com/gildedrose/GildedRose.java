package com.gildedrose;

import com.gildedrose.updater.ItemUpdater;
import com.gildedrose.updater.AgedBrieUpdater;
import com.gildedrose.updater.SulfurasUpdater;
import com.gildedrose.updater.BackstagePassUpdater;
import com.gildedrose.updater.DefaultUpdater;
import com.gildedrose.updater.ConjuredItemUpdater;

/**
 * Sistema de controle de estoque da loja Gilded Rose.
 * Refatorado usando o padrão Strategy para permitir fácil adição de novos tipos de itens.
 * 
 * A estrutura atual facilita a adição de novos tipos de item? Justifique com base no Princípio Aberto-Fechado:
 * Sim. O Princípio Aberto-Fechado (OCP) estabelece que entidades de software devem ser abertas para extensão mas fechadas para modificação.
 * A implementação atual usa o padrão Strategy com a interface ItemUpdater. Para adicionar um novo tipo de item,
 * basta criar uma nova classe que implemente ItemUpdater e adicioná-la à lista de updaters, sem alterar
 * a classe GildedRose ou as classes updaters existentes.
 * 
 * A implementação dos ItemUpdater respeita o Princípio da Responsabilidade Única? Explique:
 * Sim. Cada classe ItemUpdater tem uma única responsabilidade: atualizar um tipo específico de item.
 * AgedBrieUpdater só sabe como atualizar Aged Brie, BackstagePassUpdater só atualiza passes, etc.
 * Isso segue o SRP, pois cada classe tem apenas uma razão para mudar: uma mudança nas regras daquele tipo de item.
 * 
 * Alguma violação do Princípio de Substituição de Liskov pode ser identificada em sua hierarquia? Se sim, corrija-a:
 * O DefaultUpdater retorna true para canUpdate() para qualquer item, o que poderia ser um problema.
 * No entanto, como ele é usado como último recurso na lista de updaters (Strategy pattern), isso funciona corretamente.
 * Uma alternativa seria usar null ou Optional para representar "nenhum updater encontrado", mas a abordagem atual
 * é um padrão comum em implementações de Strategy onde há um caso default.
 */
class GildedRose {
    private final Item[] items;
    private final ItemUpdater[] updaters;

    public GildedRose(Item[] items) {
        this.items = items;
        this.updaters = new ItemUpdater[] {
            new AgedBrieUpdater(),
            new BackstagePassUpdater(),
            new SulfurasUpdater(),
            new ConjuredItemUpdater(),
            new DefaultUpdater()
        };
    }

    public void updateQuality() {
        for (Item item : items) {
            ItemUpdater updater = findUpdater(item);
            if (updater != null) {
                updater.update(item);
            }
        }
    }

    public Item[] getItems() {
        return items;
    }

    private ItemUpdater findUpdater(Item item) {
        for (ItemUpdater updater : updaters) {
            if (updater.canUpdate(item)) {
                return updater;
            }
        }
        return null;
    }
}
