using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace TP3.Models
{
    public class Property
    {
        public int Id { get; set; }

        [Required]
        public string Name { get; set; } = string.Empty;

        [Column(TypeName = "decimal(8, 2)")]
        public decimal PricePerNight { get; set; }

        public int CityId { get; set; }
        public City? City { get; set; }
        public DateTime? DeletedAt { get; set; }
    }
}