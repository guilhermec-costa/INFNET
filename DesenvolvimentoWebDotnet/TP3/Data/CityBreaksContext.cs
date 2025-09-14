using Microsoft.EntityFrameworkCore;
using System.Reflection;
using TP3.Models;

namespace TP3.Data
{
  public class CityBreaksContext : DbContext
  {
    public CityBreaksContext(DbContextOptions<CityBreaksContext> options) : base(options)
    {
    }

    public DbSet<Country> Countries { get; set; }
    public DbSet<City> Cities { get; set; }
    public DbSet<Property> Properties { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);
        modelBuilder.ApplyConfigurationsFromAssembly(Assembly.GetExecutingAssembly());
    }
  }
}