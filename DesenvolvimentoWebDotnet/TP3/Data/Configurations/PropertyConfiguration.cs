using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using TP3.Models;

namespace TP3.Data.Configurations
{
  public class PropertyConfiguration : IEntityTypeConfiguration<Property>
  {
    public void Configure(EntityTypeBuilder<Property> builder)
    {
      builder.Property(p => p.Name)
          .HasMaxLength(200)
          .HasColumnName("PropertyName");

      builder.Property(p => p.PricePerNight)
          .HasColumnName("Price");

      builder.HasData(
new Property { Id = 1, Name = "Hotel Copacabana", PricePerNight = 750.00m, CityId = 1 },
new Property { Id = 2, Name = "Pousada Ipanema", PricePerNight = 500.50m, CityId = 1 },
new Property { Id = 3, Name = "Apartamento na Paulista", PricePerNight = 350.00m, CityId = 2 },
new Property { Id = 4, Name = "Loft em Manhattan", PricePerNight = 1200.00m, CityId = 3 },
new Property { Id = 5, Name = "Hotel com vista para o Monte Fuji", PricePerNight = 1500.00m, CityId = 4 }
);
    }
  }
}