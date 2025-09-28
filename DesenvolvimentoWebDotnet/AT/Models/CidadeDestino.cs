using System.ComponentModel.DataAnnotations;

namespace AgenciaTurismo.Models;

public class CidadeDestino
{
    public int Id { get; set; }

    [Required]
    public string Nome { get; set; }

    public int PaisDestinoId { get; set; }
    public PaisDestino PaisDestino { get; set; }
}