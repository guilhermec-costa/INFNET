using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace AgenciaTurismo.Models;

public class PacoteTuristico
{
    public int Id { get; set; }

    [Required(ErrorMessage = "O título é obrigatório.")]
    [MinLength(3, ErrorMessage = "O título deve ter pelo menos 3 caracteres.")]
    public string Titulo { get; set; }

    public DateTime DataInicio { get; set; }

    public int DuracaoDias { get; set; }

    [Range(1, 100, ErrorMessage = "A capacidade deve ser entre 1 e 100.")]
    public int CapacidadeMaxima { get; set; }

    [Column(TypeName = "decimal(18, 2)")]
    public decimal Preco { get; set; }

    public List<CidadeDestino> Destinos { get; set; } = new List<CidadeDestino>();
    public List<Reserva> Reservas { get; set; } = new List<Reserva>();
    
    public bool IsDeleted { get; set; } = false;
}