using TP3.Models;

namespace TP3.Services
{
    public interface ICityService
    {
        Task<List<City>> GetAllAsync();
        Task<City?> GetByNameAsync(string name);
    }
}