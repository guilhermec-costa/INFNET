using AgenciaTurismo.Data;
using AgenciaTurismo.Models;
using Microsoft.EntityFrameworkCore;

namespace AgenciaTurismo.Services;

public class ReservaService
{
    private readonly AgenciaTurismoContext _context;
    
    public event EventHandler<string> CapacityReached;

    public ReservaService(AgenciaTurismoContext context)
    {
        _context = context;
    }

    public async Task<bool> CriarReservaAsync(Reserva reserva)
    {
        var pacote = await _context.PacotesTuristicos
            .Include(p => p.Reservas)
            .FirstOrDefaultAsync(p => p.Id == reserva.PacoteTuristicoId);

        if (pacote == null) return false;

        if (pacote.Reservas.Count >= pacote.CapacidadeMaxima)
        {
            OnCapacityReached($"Capacidade máxima de {pacote.CapacidadeMaxima} atingida para o pacote '{pacote.Titulo}'.");
            return false;
        }

        _context.Reservas.Add(reserva);
        await _context.SaveChangesAsync();
        return true;
    }

    protected virtual void OnCapacityReached(string message)
    {
        CapacityReached?.Invoke(this, message);
    }
}