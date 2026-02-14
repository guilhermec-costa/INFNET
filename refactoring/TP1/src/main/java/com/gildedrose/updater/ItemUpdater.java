package com.gildedrose.updater;

import com.gildedrose.Item;

/**
 * Interface para estratégias de atualização de qualidade de itens.
 * Segue o padrão Strategy para permitir fácil adição de novos tipos de itens.
 */
public interface ItemUpdater {
    
    /**
     * Atualiza a qualidade do item de acordo com suas regras específicas.
     * @param item O item a ser atualizado
     */
    void update(Item item);
    
    /**
     * Verifica se este updater pode tratar o item específico.
     * @param item O item a ser verificado
     * @return true se este updater pode processar o item
     */
    boolean canUpdate(Item item);
}
