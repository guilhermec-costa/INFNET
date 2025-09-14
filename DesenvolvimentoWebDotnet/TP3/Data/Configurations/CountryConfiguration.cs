using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using TP3.Models;

namespace TP3.Data.Configurations
{
  public class CountryConfiguration : IEntityTypeConfiguration<Country>
  {
    public void Configure(EntityTypeBuilder<Country> builder)
    {
      builder.Property(c => c.CountryName)
          .HasMaxLength(100)
          .HasColumnName("Country");

      builder.Property(c => c.CountryCode)
          .HasMaxLength(3);

      builder.HasData(
      new Country { Id = 1, CountryCode = "BR", CountryName = "Brasil" },
      new Country { Id = 2, CountryCode = "US", CountryName = "Estados Unidos" },
      new Country { Id = 3, CountryCode = "JP", CountryName = "Japão" }
  );
    }
  }
}