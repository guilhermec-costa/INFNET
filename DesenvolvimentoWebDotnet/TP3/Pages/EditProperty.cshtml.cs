using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using TP3.Data;
using TP3.Models;

namespace TP3.Pages
{
    public class EditPropertyModel : PageModel
    {
        private readonly CityBreaksContext _context;

        public EditPropertyModel(CityBreaksContext context)
        {
            _context = context;
        }

        [BindProperty]
        public Property Property { get; set; } = default!;

        public async Task<IActionResult> OnGetAsync(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var property = await _context.Properties.FindAsync(id);
            if (property == null)
            {
                return NotFound();
            }
            Property = property;
            return Page();
        }

        public async Task<IActionResult> OnPostAsync(int id)
        {
            var propertyToUpdate = await _context.Properties.FindAsync(id);

            if (propertyToUpdate == null)
            {
                return NotFound();
            }

            if (await TryUpdateModelAsync<Property>(
                propertyToUpdate,
                "Property",
                p => p.Name, p => p.PricePerNight))
            {
                await _context.SaveChangesAsync();
                return RedirectToPage("/Index");
            }

            return Page();
        }
    }
}