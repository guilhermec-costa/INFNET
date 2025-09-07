using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using RazorPagesTurismo.Models;

namespace RazorPagesTurismo.Pages.CountryManager
{
    public class CreateMultipleCountriesModel : PageModel
    {
        [BindProperty]
        public List<InputModel> Countries { get; set; }

        public List<Country> SubmittedCountries { get; private set; }

        public void OnGet()
        {
            Countries = new List<InputModel>();
            for (int i = 0; i < 5; i++)
            {
                Countries.Add(new InputModel());
            }
        }

        public void OnPost()
        {
            SubmittedCountries = new List<Country>();
            foreach (var input in Countries)
            {
                if (!string.IsNullOrEmpty(input.CountryName) && !string.IsNullOrEmpty(input.CountryCode))
                {
                    SubmittedCountries.Add(new Country
                    {
                        CountryName = input.CountryName,
                        CountryCode = input.CountryCode
                    });
                }
            }
        }

        public class InputModel
        {
            public string CountryName { get; set; }
            public string CountryCode { get; set; }
        }
    }
}