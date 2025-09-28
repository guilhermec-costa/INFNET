using System.ComponentModel.DataAnnotations;

namespace AgenciaTurismo.Models;

public class Reserva
{
    public int Id { get; set; }
    public DateTime DataReserva { get; set; }

    [Required]
    public int ClienteId { get; set; }
    public Cliente Cliente { get; set; }

    [Required]
    public int PacoteTuristicoId { get; set; }
    public PacoteTuristico PacoteTuristico { get; set; }
}