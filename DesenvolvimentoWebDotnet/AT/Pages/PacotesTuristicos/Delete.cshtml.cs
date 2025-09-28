using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using AgenciaTurismo.Models;

namespace AT.Pages.PacotesTuristicos
{
  public class DeleteModel : PageModel
  {
    private readonly AgenciaTurismo.Data.AgenciaTurismoContext _context;

    public DeleteModel(AgenciaTurismo.Data.AgenciaTurismoContext context)
    {
      _context = context;
    }

    [BindProperty]
    public PacoteTuristico PacoteTuristico { get; set; } = default!;

    public async Task<IActionResult> OnGetAsync(int? id)
    {
      if (id == null)
      {
        return NotFound();
      }

      var pacoteturistico = await _context.PacotesTuristicos.FirstOrDefaultAsync(m => m.Id == id);

      if (pacoteturistico is not null)
      {
        PacoteTuristico = pacoteturistico;

        return Page();
      }

      return NotFound();
    }

    public async Task<IActionResult> OnPostAsync(int? id)
    {
      if (id == null) return NotFound();

      PacoteTuristico = await _context.PacotesTuristicos.FindAsync(id);

      if (PacoteTuristico != null)
      {
        PacoteTuristico.IsDeleted = true;
        _context.Attach(PacoteTuristico).State = EntityState.Modified;

        await _context.SaveChangesAsync();
      }

      return RedirectToPage("./Index");
    }
  }
}
