using TP3.Models;

namespace TP3.Services
{
    public interface IPropertyService
    {
        Task<List<Property>> GetFilteredAsync(decimal? minPrice, decimal? maxPrice, string? cityName, string? propertyName);
        Task DeleteAsync(int id);
    }
}