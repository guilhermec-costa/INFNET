using Microsoft.EntityFrameworkCore;
using TP3.Data;
using TP3.Models;

namespace TP3.Services
{
  public class CityService : ICityService
  {
    private readonly CityBreaksContext _context;

    public CityService(CityBreaksContext context)
    {
      _context = context;
    }

    public async Task<List<City>> GetAllAsync()
    {
      return await _context.Cities
          .Include(c => c.Country)
          .Include(c => c.Properties)
          .OrderBy(c => c.Name)
          .ToListAsync();
    }

    public async Task<City?> GetByNameAsync(string name)
    {
      return await _context.Cities
          .Include(c => c.Country)
          .Include(c => c.Properties.Where(p => p.DeletedAt == null))
          .FirstOrDefaultAsync(c => EF.Functions.Collate(c.Name, "NOCASE") == name);
    }
  }
}