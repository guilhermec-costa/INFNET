using AgenciaTurismo.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace AgenciaTurismo.Pages;

public class CalculoDescontoModel : PageModel
{
    [BindProperty]
    public decimal PrecoComDesconto { get; set; }

    public void OnGet()
    {
    }

    public void OnPost(decimal preco)
    {
        var descontoService = new DescontoService();
        
        CalculateDelegate calculador = new CalculateDelegate(descontoService.AplicarDescontoCliente);

        PrecoComDesconto = calculador(preco);
    }
}