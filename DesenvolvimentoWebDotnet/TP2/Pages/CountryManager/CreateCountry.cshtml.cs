using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using RazorPagesTurismo.Models;

namespace RazorPagesTurismo.Pages.CountryManager
{
    public class CreateCountryModel : PageModel
    {
        [BindProperty]
        public InputModel Input { get; set; }

        public Country CreatedCountry { get; private set; }

        public void OnGet() { }

        public IActionResult OnPost()
        {
            if (!ModelState.IsValid)
            {
                return Page();
            }

            if (!string.IsNullOrEmpty(Input.CountryName) && !string.IsNullOrEmpty(Input.CountryCode))
            {
                if (char.ToUpper(Input.CountryName[0]) != char.ToUpper(Input.CountryCode[0]))
                {
                    ModelState.AddModelError("Input.CountryCode", "O código do país deve começar com a mesma letra do nome do país.");
                    return Page();
                }
            }
            CreatedCountry = new Country
            {
                CountryName = Input.CountryName,
                CountryCode = Input.CountryCode
            };

            return Page();
        }

        public class InputModel
        {
            [Required]
            public string CountryName { get; set; }

            [Required]
            [StringLength(2, MinimumLength = 2, ErrorMessage = "O código do país deve ter exatamente 2 caracteres.")]
            public string CountryCode { get; set; }
        }
    }
}