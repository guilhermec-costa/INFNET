using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace AgenciaTurismo.Pages;

public class CalculoTotalReservaModel : PageModel
{
    public decimal ValorTotal { get; set; }

    public void OnGet() { }

    public void OnPost(int duracaoDias, decimal precoDiaria)
    {
        Func<int, decimal, decimal> calcularTotal = (dias, preco) => dias * preco;

        ValorTotal = calcularTotal(duracaoDias, precoDiaria);
    }
}