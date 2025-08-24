using Microsoft.AspNetCore.Mvc.RazorPages;
using System.Collections.Generic;

namespace LojaRazor.Pages
{
    public class IndexModel : PageModel
    {
        public List<Produto> Produtos { get; set; } = new();

        public void OnGet()
        {
            Produtos = new List<Produto>
            {
                new Produto { Nome = "Notebook", Preco = 4500.00 },
                new Produto { Nome = "Smartphone", Preco = 2500.00 },
                new Produto { Nome = "Headset Gamer", Preco = 350.00 }
            };
        }
    }

    public class Produto
    {
        public string Nome { get; set; }
        public double Preco { get; set; }
    }
}
