using System.ComponentModel.DataAnnotations;

namespace TP3.Models
{
    public class Country
    {
        public int Id { get; set; }

        [Required]
        public string CountryCode { get; set; } = string.Empty;

        [Required]
        public string CountryName { get; set; } = string.Empty;

        public List<City> Cities { get; set; } = new();
    }
}