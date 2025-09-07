using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace RazorPagesTurismo.Pages.CityManager
{
    public class CreateCityModel : PageModel
    {
        [BindProperty]
        public InputModel Input { get; set; }

        public string SuccessMessage { get; private set; }

        public void OnGet()
        {
        }

        public IActionResult OnPost()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            SuccessMessage = $"Cidade '{Input.CityName}' validada com sucesso!";
            return Page();
        }

        public class InputModel
        {
            [Required(ErrorMessage = "O nome da cidade é obrigatório.")]
            [MinLength(3, ErrorMessage = "O nome da cidade deve ter no mínimo 3 caracteres.")]
            public string CityName { get; set; }
        }
    }
}