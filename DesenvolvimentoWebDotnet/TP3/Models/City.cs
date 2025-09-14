using System.ComponentModel.DataAnnotations;

namespace TP3.Models
{
    public class City
    {
        public int Id { get; set; }

        [Required]
        public string Name { get; set; } = string.Empty;

        public int CountryId { get; set; }
        public Country? Country { get; set; }

        public List<Property> Properties { get; set; } = new();
    }
}