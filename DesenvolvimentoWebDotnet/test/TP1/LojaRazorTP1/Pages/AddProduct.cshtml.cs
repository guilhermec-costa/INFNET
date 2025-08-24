using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace LojaRazor.Pages
{
    public class AddProductModel : PageModel
    {
        [BindProperty] // permite receber valores do formulário automaticamente
        public Produto NovoProduto { get; set; }

        public bool DadosEnviados { get; set; } = false;

        public void OnGet()
        {
            // Página inicial do formulário
        }

        public void OnPost()
        {
            if (ModelState.IsValid)
            {
                DadosEnviados = true;
            }
        }
    }
}
