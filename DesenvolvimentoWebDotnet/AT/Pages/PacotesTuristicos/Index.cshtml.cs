using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.EntityFrameworkCore;
using AgenciaTurismo.Models;
using Microsoft.AspNetCore.Authorization;

namespace AT.Pages.PacotesTuristicos
{
    [Authorize]
    public class IndexModel : PageModel
    {
        private readonly AgenciaTurismo.Data.AgenciaTurismoContext _context;

        public IndexModel(AgenciaTurismo.Data.AgenciaTurismoContext context)
        {
            _context = context;
        }

        public IList<PacoteTuristico> PacoteTuristico { get;set; } = default!;

        public async Task OnGetAsync()
          {
            PacoteTuristico = await _context.PacotesTuristicos
                                      .Where(p => !p.IsDeleted)
                                      .ToListAsync();
          }

    }
}
