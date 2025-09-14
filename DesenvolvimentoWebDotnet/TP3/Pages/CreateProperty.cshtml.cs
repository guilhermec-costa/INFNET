using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using TP3.Data;
using TP3.Models;

namespace TP3.Pages
{
    public class CreatePropertyModel : PageModel
    {
        private readonly CityBreaksContext _context;

        public CreatePropertyModel(CityBreaksContext context)
        {
            _context = context;
        }

        [BindProperty]
        public Property Property { get; set; } = new();

        public SelectList? CityOptions { get; set; }

        public async Task OnGetAsync()
        {
            CityOptions = new SelectList(await _context.Cities.OrderBy(c => c.Name).ToListAsync(), "Id", "Name");
        }

        public async Task<IActionResult> OnPostAsync()
        {
            if (!ModelState.IsValid)
            {
                await OnGetAsync();
                return Page();
            }

            _context.Properties.Add(Property);
            await _context.SaveChangesAsync();

            return RedirectToPage("/Index");
        }
    }
}