using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using TP3.Models;
using TP3.Services;

namespace TP3.Pages
{
  public class CityDetailsModel : PageModel
  {
    private readonly ICityService _cityService;
    private readonly IPropertyService _propertyService;

    public City? City { get; private set; }

    public CityDetailsModel(ICityService cityService, IPropertyService propertyService)
    {
      _cityService = cityService;
      _propertyService = propertyService;
    }

    public async Task<IActionResult> OnGetAsync(string name)
    {
      if (string.IsNullOrEmpty(name))
      {
        return NotFound();
      }

      City = await _cityService.GetByNameAsync(name);

      if (City == null)
      {
        return NotFound();
      }

      return Page();
    }

    public async Task<IActionResult> OnPostDeleteAsync(int propertyId, string cityName)
    {
      await _propertyService.DeleteAsync(propertyId);
      return RedirectToPage(new { name = cityName });
    }
  }
}