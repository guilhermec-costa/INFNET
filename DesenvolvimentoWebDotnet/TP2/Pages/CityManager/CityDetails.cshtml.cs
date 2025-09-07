using Microsoft.AspNetCore.Mvc.RazorPages;

namespace RazorPagesTurismo.Pages.CityManager
{
    public class CityDetailsModel : PageModel
    {
        public string Message { get; private set; }

        public void OnGet(string cityName)
        {
            Message = $"Você está vendo detalhes de: {cityName}";
        }
    }
}