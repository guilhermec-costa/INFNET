package com.infnet.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.infnet.exception.InvalidEnderecoException;
import com.infnet.exception.InvalidPesoException;

class EntregaTest {

  @Test
  void deveCriarEntregaValida() {
    Entrega entrega = new Entrega("Rua A, 123", 5.0, "EXP", "Joao");
    assertEquals("Rua A, 123", entrega.getEndereco());
    assertEquals(5.0, entrega.getPeso());
    assertEquals("EXP", entrega.getTipoFrete());
    assertEquals("Joao", entrega.getDestinatario());
  }

  @Test
  void deveLancarExcecaoParaPesoInvalido() {
    assertThrows(InvalidPesoException.class, () -> new Entrega("Rua A, 123", -1.0, "EXP", "Joao"));
  }

  @Test
  void deveLancarExcecaoParaEnderecoVazio() {
    assertThrows(InvalidEnderecoException.class, () -> new Entrega("", 5.0, "EXP", "Joao"));
  }

  @Test
  void deveLancarExcecaoParaTipoFreteInvalido() {
    assertThrows(InvalidPesoException.class, () -> new Entrega("Rua A, 123", 5.0, "INVALIDO", "Joao"));
  }
}