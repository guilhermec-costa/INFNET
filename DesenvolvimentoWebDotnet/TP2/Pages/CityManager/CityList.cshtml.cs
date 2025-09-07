using Microsoft.AspNetCore.Mvc.RazorPages;

namespace RazorPagesTurismo.Pages.CityManager
{
    public class CityListModel : PageModel
    {
        public List<string> Cities { get; private set; }

        public void OnGet()
        {
            Cities = new List<string> { "Rio de Janeiro", "São Paulo", "Brasília" };
        }
    }
}